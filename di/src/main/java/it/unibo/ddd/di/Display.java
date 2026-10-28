package it.unibo.ddd.di;

/**
 * The parking lot's display, used to show messages to drivers entering the parking lot.
 */
public interface Display {
    /**
     * Shows a message on the parking lot's display.
     * @param message the message to be shown.
     */
    void showMessage(String message);
}
