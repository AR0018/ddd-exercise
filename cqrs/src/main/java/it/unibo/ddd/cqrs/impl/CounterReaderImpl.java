package it.unibo.ddd.cqrs.impl;

import it.unibo.ddd.cqrs.CounterReader;
import it.unibo.ddd.cqrs.CounterWriter;

import java.time.Instant;
import java.util.TreeMap;

/** Read model (cf. steps 3-4 of cqrs/walkthrough.md). */
public class CounterReaderImpl implements CounterReader {
    // TODO: add a projection, i.e. a data structure optimised for queries (hint: TreeMap<Instant, Long>)
    private TreeMap<Instant, Long> projection = new TreeMap<>();

    public CounterReaderImpl(CounterWriter writer) {
        // TODO: replay the past variations of the writer, then subscribe to the new ones
        writer.getVariations().forEach(v -> projection.put(v.when(), v.after()));
        writer.onVariation().subscribe();
        throw new UnsupportedOperationException("TODO: not implemented yet");
    }

    @Override
    public long getValue() {
        throw new UnsupportedOperationException("TODO: not implemented yet");
    }

    @Override
    public long getValue(Instant at) {
        // TODO: the value of the latest variation before or at the given moment (or 0)
        throw new UnsupportedOperationException("TODO: not implemented yet");
    }
}
