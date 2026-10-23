package it.unibo.ddd.cqrs.impl;

import it.unibo.ddd.cqrs.CounterReader;
import it.unibo.ddd.cqrs.CounterWriter;

import java.time.Instant;
import java.util.Map;
import java.util.TreeMap;

/** Read model (cf. steps 3-4 of cqrs/walkthrough.md). */
public class CounterReaderImpl implements CounterReader {
    private static final long INITIAL_VAL = 0;
    private final TreeMap<Instant, Long> projection = new TreeMap<>();

    public CounterReaderImpl(CounterWriter writer) {
        // TODO: Apparently, cannot factorize the lambda because forEach() and subscribe() require two different types
        //  of Consumer (java.unil.Consumer and io.reactivex.rxjava3.functions.Consumer).
        writer.getVariations().forEach(v -> projection.put(v.when(), v.after()));
        var disposable = writer.onVariation().subscribe(v -> projection.put(v.when(), v.after()));
    }

    private long getEntryOrDefault(final Map.Entry<Instant, Long> entry) {
        return entry != null ? entry.getValue() : INITIAL_VAL;
    }

    @Override
    public long getValue() {
        return getEntryOrDefault(projection.lastEntry());
    }

    @Override
    public long getValue(Instant at) {
        return getEntryOrDefault(projection.floorEntry(at));
    }
}
