package it.unibo.ddd.di;

import it.unibo.ddd.di.impl.PlateRegistryImpl;
import it.unibo.ddd.di.impl.PrintingDisplay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// TODO: once ParkingLot supports dependency injection, rewrite these tests with test doubles (cf. step 4 of di/walkthrough.md)
//       they should run in milliseconds, in any order, and assert exact fees

//TODO: Use Mockito to create test doubles.
class ParkingLotTest {
    private Display mockDisplay;
    private PlateRegistry mockRegistry;
    private ParkingLot lot;

    @BeforeEach
    void init() {
        mockDisplay = mock(Display.class);
        mockRegistry = mock(PlateRegistry.class);
        lot = new ParkingLot(mockRegistry, mockDisplay);
    }

    @Test
    void shortStaysAreFree() {
        when(mockRegistry.exit(anyString())).thenReturn(Duration.ofMinutes(5));
        String plate = "AB123CD";
        BigDecimal fee = new BigDecimal("0.00");
        lot.enter(plate);
        assertEquals(fee, lot.exit(plate));
    }

    @Test
    void longStaysArePaid() throws InterruptedException {
        // TODO: mock the registry so that it returns a Duration of at least 16 minutes
        //  and verify the fee is correct.
        lot.enter("EF456GH");
        Thread.sleep(3000); // ... 16 minutes would be needed to see a fee: what can we assert?
        assertTrue(lot.exit("EF456GH").signum() >= 0);
        verify(mockDisplay, times(2)).showMessage(anyString());
    }

    @Test
    void messagesAreCorrectlyDisplayed() {
        String plate = "AB123CD";
        BigDecimal fee = new BigDecimal("0.00");
        lot.enter(plate);
        lot.exit(plate);
        verify(mockDisplay).showMessage("Welcome, " + plate);
        verify(mockDisplay).showMessage("Goodbye, " + plate + ": please pay " + fee + " EUR");
    }

    @Test
    void sameCarCannotEnterTwice() {
        var parkingLot = new ParkingLot(new PlateRegistryImpl(), mockDisplay);
        parkingLot.enter("IL789MN");
        assertThrows(IllegalStateException.class, () -> parkingLot.enter("IL789MN"));
        verify(mockDisplay).showMessage(anyString());
    }
}
