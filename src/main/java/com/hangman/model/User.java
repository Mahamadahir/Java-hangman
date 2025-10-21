package com.hangman.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents an individual player and tracks their active streak per difficulty.
 */
public class User {
    private final String username;
    private final Map<String, Integer> streakByDifficulty = new HashMap<>();
    private String preferredDifficulty = "easy";

    public User(String username) {
        this.username = Objects.requireNonNull(username).trim();
    }

    public String getUsername() {
        return username;
    }

    public String getPreferredDifficulty() {
        return preferredDifficulty;
    }

    public void setPreferredDifficulty(String preferredDifficulty) {
        this.preferredDifficulty = preferredDifficulty;
    }

    public int getCurrentStreak(String difficulty) {
        return streakByDifficulty.getOrDefault(difficulty, 0);
    }

    public void incrementStreak(String difficulty) {
        streakByDifficulty.merge(difficulty, 1, Integer::sum);
    }

    public void resetStreak(String difficulty) {
        streakByDifficulty.put(difficulty, 0);
    }

    @Override
    public String toString() {
        return username;
    }
}
