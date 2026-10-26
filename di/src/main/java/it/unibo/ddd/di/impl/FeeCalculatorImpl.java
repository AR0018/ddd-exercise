package it.unibo.ddd.di.impl;

import it.unibo.ddd.di.FeeCalculator;

import java.math.BigDecimal;
import java.time.Duration;

public class FeeCalculatorImpl implements FeeCalculator {
    private final Duration freeTime;
    private final BigDecimal hourlyFee;

    public FeeCalculatorImpl(final Duration freeTime, final BigDecimal hourlyFee) {
        this.freeTime = freeTime;
        this.hourlyFee = hourlyFee;
    }

    @Override
    public BigDecimal fee(final Duration stay) {
        if (stay.compareTo(freeTime) <= 0) {
            return BigDecimal.ZERO.setScale(2);
        }
        var startedHours = (stay.toMinutes() + 59) / 60;
        return hourlyFee.multiply(BigDecimal.valueOf(startedHours));
    }
}
