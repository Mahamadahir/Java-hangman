package com.hangman.controller;

/**
 * Lets a running game hand control back to the launcher's screen navigation
 * so the player can start a rematch, switch context, or quit without
 * relaunching the application.
 */
public interface GameNavigation {

    /** Returns the current player to the difficulty selection screen. */
    void changeDifficulty();

    /** Returns to the user selection screen to pick or create another profile. */
    void switchPlayer();

    /** Tears down the remaining window(s) and ends the application. */
    void exit();
}
