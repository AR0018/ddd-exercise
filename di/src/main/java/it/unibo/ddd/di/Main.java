package it.unibo.ddd.di;

import it.unibo.ddd.di.impl.PlateRegistryImpl;
import it.unibo.ddd.di.impl.PrintingDisplay;

public class Main {
    public static void main(String[] args) {
        // TODO: make this method the composition root, i.e. the only place choosing which implementations to use
        var lot = new ParkingLot(new PlateRegistryImpl(), new PrintingDisplay());
        lot.enter("AB123CD");
        lot.exit("AB123CD");
    }
}
