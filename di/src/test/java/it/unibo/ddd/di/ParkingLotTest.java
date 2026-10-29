package it.unibo.ddd.di;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    void longStaysArePaid() {
        BigDecimal oneHourFee = new BigDecimal("1.50");
        BigDecimal twoHoursFee = new BigDecimal("3.00");
        String firstPlate = "EF456GH";
        String secondPlate = "IJ789KL";
        when(mockRegistry.exit(firstPlate)).thenReturn(Duration.ofMinutes(30));
        when(mockRegistry.exit(secondPlate)).thenReturn(Duration.ofMinutes(90));
        lot.enter(firstPlate);
        lot.enter(secondPlate);
        assertEquals(oneHourFee, lot.exit(firstPlate));
        assertEquals(twoHoursFee, lot.exit(secondPlate));
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
        String plate = "IL789MN";
        doNothing()
                .doThrow(new IllegalStateException())
                .when(mockRegistry).enter(plate);
        lot.enter(plate);
        assertThrows(IllegalStateException.class, () -> lot.enter(plate));
        verify(mockDisplay).showMessage(anyString());
    }
}
