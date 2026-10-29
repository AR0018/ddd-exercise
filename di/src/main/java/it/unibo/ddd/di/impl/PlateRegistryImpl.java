package it.unibo.ddd.di.impl;

import it.unibo.ddd.di.PlateRegistry;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class PlateRegistryImpl implements PlateRegistry {
    private final Map<String, Instant> entries = new HashMap<>();

    @Override
    public void enter(String plate) {
        if (entries.putIfAbsent(plate, Instant.now()) != null) {
            throw new IllegalStateException(plate   + " is already parked");
        }
    }

    @Override
    public Duration exit(String plate) {
        var entry = entries.remove(plate);
        if (entry == null) {
            throw new IllegalStateException(plate + " is not parked");
        }
        return Duration.between(entry, Instant.now());
    }
}
