package it.unibo.ddd.di;

import java.time.Duration;

/**
 * Component responsible for managing entry and exit of cars from the Parking Lot,
 * based on their plates.
 */
public interface PlateRegistry {
    /**
     * Registers the entry of a new car with the given plate.
     * @param plate the car's plate.
     * @throws IllegalStateException if the car with the given plate is already parked.
     */
    void enter(String plate);

    /**
     * Removes the car with the given plate from the registry of parked cars,
     * and calculates the duration of its stay.
     * @param plate the car's plate.
     * @return the duration of the car's stay inside the parking lot.
     * @throws IllegalStateException if the car with the given plate is not parked.
     */
    Duration exit(String plate);
}
