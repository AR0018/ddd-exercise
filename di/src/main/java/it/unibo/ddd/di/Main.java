package it.unibo.ddd.di;

import it.unibo.ddd.di.impl.PlateRegistryImpl;
import it.unibo.ddd.di.impl.PrintingDisplay;

public class Main {
    public static void main(String[] args) {
        var lot = new ParkingLot(new PlateRegistryImpl(), new PrintingDisplay());
        lot.enter("AB123CD");
        lot.exit("AB123CD");
    }
}
