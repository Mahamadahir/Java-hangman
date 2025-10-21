package com.hangman.logic;

import com.hangman.model.Word;

import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

/**
 * Encapsulates the state of an active hangman round.
 */
public class GameLogic {
    private static final int MAX_MISTAKES = 6;

    private final Word targetWord;
    private final char[] guessedLetters;
    private final Set<Character> attemptedLetters = new LinkedHashSet<>();
    private final Set<Character> wrongLetters = new LinkedHashSet<>();
    private int mistakesMade;

    public GameLogic(Word targetWord) {
        this.targetWord = targetWord;
        this.guessedLetters = new char[targetWord.getLength()];
    }

    public GuessResult submitGuess(char rawLetter) {
        if (isGameOver()) {
            return GuessResult.GAME_OVER;
        }

        char letter = Character.toLowerCase(rawLetter);
        if (!Character.isLetter(letter)) {
            return GuessResult.INVALID;
        }

        if (!attemptedLetters.add(letter)) {
            return GuessResult.ALREADY_GUESSED;
        }

        boolean found = false;
        char[] target = targetWord.toCharArray();
        for (int index = 0; index < target.length; index++) {
            if (target[index] == letter) {
                guessedLetters[index] = letter;
                found = true;
            }
        }

        if (found) {
            return GuessResult.CORRECT;
        }

        wrongLetters.add(letter);
        mistakesMade++;
        return GuessResult.INCORRECT;
    }

    public boolean hasWon() {
        for (char letter : guessedLetters) {
            if (letter == 0) {
                return false;
            }
        }
        return true;
    }

    public boolean isGameOver() {
        return hasWon() || mistakesMade >= MAX_MISTAKES;
    }

    public int getMistakesMade() {
        return mistakesMade;
    }

    public int getRemainingLives() {
        return MAX_MISTAKES - mistakesMade;
    }

    public Word getTargetWord() {
        return targetWord;
    }

    public char[] getGuessedLetters() {
        return guessedLetters.clone();
    }

    public String getWrongLettersSummary() {
        return wrongLetters.stream()
                .map(String::valueOf)
                .map(letter -> letter.toUpperCase(Locale.ENGLISH))
                .reduce((a, b) -> a + " " + b)
                .orElse("-");
    }

    public enum GuessResult {
        CORRECT,
        INCORRECT,
        ALREADY_GUESSED,
        INVALID,
        GAME_OVER
    }
}
