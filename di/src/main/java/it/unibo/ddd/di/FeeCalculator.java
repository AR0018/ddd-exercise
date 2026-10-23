package it.unibo.ddd.di;

import java.math.BigDecimal;
import java.time.Duration;

/**
 * Component responsible for calculating parking lot fees.
 */
public interface FeeCalculator {
    /**
     * Calculates the fee for the given stay duration.
     * @param stay the duration of the stay.
     * @return the calculated fee.
     */
    public BigDecimal fee(Duration stay);
}
