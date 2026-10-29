package it.unibo.ddd.di;

import it.unibo.ddd.di.impl.PlateRegistryImpl;
import it.unibo.ddd.di.impl.PrintingDisplay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// TODO: once ParkingLot supports dependency injection, rewrite these tests with test doubles (cf. step 4 of di/walkthrough.md)
//       they should run in milliseconds, in any order, and assert exact fees

//TODO: Use Mockito to create test doubles.
class ParkingLotTest {
    private Display mockDisplay;

    @BeforeEach
    void init() {
        mockDisplay = mock(Display.class);
    }

    @Test
    void shortStaysAreFree() throws InterruptedException {
        var lot = new ParkingLot(new PlateRegistryImpl(), mockDisplay);
        String plate = "AB123CD";
        BigDecimal fee = new BigDecimal("0.00");
        lot.enter(plate);
        Thread.sleep(3000); // let some time pass...
        assertEquals(fee, lot.exit(plate));
        verify(mockDisplay).showMessage("Welcome, " + plate);
        verify(mockDisplay).showMessage("Goodbye, " + plate + ": please pay " + fee + " EUR");
    }

    @Test
    void longStaysArePaid() throws InterruptedException {
        var lot = new ParkingLot(new PlateRegistryImpl(), mockDisplay);
        lot.enter("EF456GH");
        Thread.sleep(3000); // ... 16 minutes would be needed to see a fee: what can we assert?
        assertTrue(lot.exit("EF456GH").signum() >= 0);
        verify(mockDisplay, times(2)).showMessage(anyString());
    }

    @Test
    void sameCarCannotEnterTwice() {
        var parkingLot = new ParkingLot(new PlateRegistryImpl(), mockDisplay);
        parkingLot.enter("IL789MN");
        assertThrows(IllegalStateException.class, () -> parkingLot.enter("IL789MN"));
        verify(mockDisplay).showMessage(anyString());
    }
}
