package com.hangman.model;

import com.google.gson.annotations.SerializedName;

import java.util.Locale;
import java.util.Objects;

/**
 * Represents a selectable word in the game.
 */
public class Word {
    @SerializedName("word")
    private String value;
    @SerializedName("difficulty")
    private String difficulty;
    @SerializedName("wordLength")
    private int wordLength;

    public Word() {
        // Required for Gson.
    }

    public Word(String word, String difficulty, int length) {
        this.value = normalizeWord(word);
        this.difficulty = normalizeDifficulty(difficulty);
        this.wordLength = length;
    }

    public String getValue() {
        value = normalizeWord(value);
        return value;
    }

    public char[] toCharArray() {
        return getValue().toLowerCase(Locale.ENGLISH).toCharArray();
    }

    public int getLength() {
        String word = getValue();
        return wordLength > 0 ? wordLength : word.length();
    }

    public String getDifficulty() {
        return normalizeDifficulty(difficulty);
    }

    private static String normalizeWord(String word) {
        String trimmed = Objects.requireNonNull(word, "Word text cannot be null.").trim();
        if (trimmed.isEmpty()) {
            throw new IllegalStateException("Word text cannot be empty.");
        }
        return trimmed;
    }

    private static String normalizeDifficulty(String difficulty) {
        return Objects.requireNonNullElse(difficulty, "easy").toLowerCase(Locale.ENGLISH);
    }
}
