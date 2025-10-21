package com.hangman.model;

import java.util.Locale;
import java.util.Objects;

/**
 * Represents a selectable word in the game.
 */
public class Word {
    private String value;
    private String difficulty;
    private int wordLength;

    public Word() {
        // Required for Gson.
    }

    public Word(String word, String difficulty, int length) {
        this.value = Objects.requireNonNull(word).trim();
        this.difficulty = Objects.requireNonNullElse(difficulty, "easy").toLowerCase(Locale.ENGLISH);
        this.wordLength = length;
    }

    public String getValue() {
        return value;
    }

    public char[] toCharArray() {
        return value.toLowerCase(Locale.ENGLISH).toCharArray();
    }

    public int getLength() {
        return wordLength > 0 ? wordLength : value.length();
    }

    public String getDifficulty() {
        return difficulty;
    }
}
