package it.unibo.ddd.di.impl;

import it.unibo.ddd.di.Display;

public class PrintingDisplay implements Display {
    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }
}
