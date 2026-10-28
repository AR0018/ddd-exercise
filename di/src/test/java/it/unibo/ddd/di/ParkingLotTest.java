package it.unibo.ddd.di;

import it.unibo.ddd.di.impl.PlateRegistryImpl;
import it.unibo.ddd.di.impl.PrintingDisplay;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

// TODO: once ParkingLot supports dependency injection, rewrite these tests with test doubles (cf. step 4 of di/walkthrough.md)
//       they should run in milliseconds, in any order, and assert exact fees

//TODO: Use Mockito to create test doubles.
//  Also check that the message is properly shown?
class ParkingLotTest {
    @Test
    void shortStaysAreFree() throws InterruptedException {
        var lot = new ParkingLot(new PlateRegistryImpl(), new PrintingDisplay());
        lot.enter("AB123CD");
        Thread.sleep(3000); // let some time pass...
        assertEquals(new BigDecimal("0.00"), lot.exit("AB123CD"));
    }

    @Test
    void longStaysArePaid() throws InterruptedException {
        var lot = new ParkingLot(new PlateRegistryImpl(), new PrintingDisplay());
        lot.enter("EF456GH");
        Thread.sleep(3000); // ... 16 minutes would be needed to see a fee: what can we assert?
        assertTrue(lot.exit("EF456GH").signum() >= 0);
    }

    @Test
    void sameCarCannotEnterTwice() {
        var parkingLot = new ParkingLot(new PlateRegistryImpl(), new PrintingDisplay());
        parkingLot.enter("IL789MN");
        assertThrows(IllegalStateException.class, () -> parkingLot.enter("IL789MN"));
    }
}
