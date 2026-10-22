package it.unibo.ddd.cqrs;

import it.unibo.ddd.Variation;
import it.unibo.ddd.cqrs.impl.CounterReaderImpl;
import it.unibo.ddd.cqrs.impl.CounterWriterImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// TODO: remove @Disabled once the implementations are ready (cf. cqrs/walkthrough.md)
// @Disabled("TODO: not implemented yet")
class CqrsTest {
    private static final Instant T0 = Instant.parse("2026-01-01T10:00:00Z");

    /** A clock that moves 1 minute ahead each time it is read. */
    private static Clock tickingClock() {
        return new Clock() {
            private Instant now = T0;

            @Override
            public Instant instant() {
                var result = now;
                now = now.plus(Duration.ofMinutes(1));
                return result;
            }

            @Override
            public ZoneId getZone() {
                return ZoneOffset.UTC;
            }

            @Override
            public Clock withZone(ZoneId zone) {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Test
    void writerMemorisesVariations() {
        var writer = new CounterWriterImpl(tickingClock());
        writer.setValue(5);
        writer.setValue(3);
        assertIterableEquals(
                List.of(
                    Variation.of(0, 5, T0),
                    Variation.of(5, 3, T0.plus(Duration.ofMinutes(1)))),
                writer.getVariations());
    }

    @Test
    void readerReconstructsPastValues() {
        final long defaultValue = 0;
        final long firstValue = 1;
        final long middleValue = 2;
        final long finalValue = 3;
        var writer = new CounterWriterImpl(tickingClock());
        var reader = new CounterReaderImpl(writer);
        writer.setValue(firstValue);
        writer.setValue(middleValue);
        writer.setValue(finalValue);
        assertEquals(finalValue, reader.getValue());
        assertEquals(defaultValue, reader.getValue(T0.minus(Duration.ofMinutes(1))));
        assertEquals(firstValue, reader.getValue(T0));
        assertEquals(middleValue, reader.getValue(T0.plus(Duration.ofMinutes(1))));
        assertEquals(finalValue, reader.getValue(T0.plus(Duration.ofMinutes(2))));
        assertEquals(firstValue, reader.getValue(T0.plus(Duration.ofSeconds(30))));
        assertEquals(finalValue, reader.getValue(T0.plus(Duration.ofHours(1))));
    }

    @Test
    void readerCreatedLaterReplaysHistory() {
        // TODO: set some values, then create the reader: it must know the past too
        fail("TODO");
    }
}
