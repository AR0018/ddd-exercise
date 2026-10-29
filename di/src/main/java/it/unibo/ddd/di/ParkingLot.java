package it.unibo.ddd.di;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * The first 15 minutes are free, then each started hour costs 1.50 EUR.
 */
public class ParkingLot {
    static final Duration FREE_TIME = Duration.ofMinutes(15);
    static final BigDecimal HOURLY_FEE = new BigDecimal("1.50");

    private final PlateRegistry registry;
    private final Display display;

    public ParkingLot(PlateRegistry registry, Display display) {
        this.registry = registry;
        this.display = display;
    }

    public void enter(String plate) {
        registry.enter(plate);
        display.showMessage("Welcome, " + plate);
    }

    public BigDecimal exit(String plate) {
        var fee = fee(registry.exit(plate));
        display.showMessage("Goodbye, " + plate + ": please pay " + fee + " EUR");
        return fee;
    }

    private BigDecimal fee(final Duration stay) {
        if (stay.compareTo(FREE_TIME) <= 0) {
            return BigDecimal.ZERO.setScale(2);
        }
        var startedHours = (stay.toMinutes() + 59) / 60;
        return HOURLY_FEE.multiply(BigDecimal.valueOf(startedHours));
    }
}
