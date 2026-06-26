package com.hangman.web;

import com.hangman.logic.GameLogic;

import java.util.ArrayList;
import java.util.List;

/**
 * Browser-facing snapshot of a round. The full word and definition are only
 * populated once the round is over.
 */
record GameState(
        String gameId,
        String difficulty,
        String maskedWord,
        int wordLength,
        int remainingLives,
        int maxLives,
        List<Character> wrongLetters,
        int streak,
        String status,
        String word,
        String definition) {

    static GameState of(GameSession session, String word, String definition) {
        GameLogic round = session.round();
        return new GameState(
                session.id(),
                session.difficulty(),
                mask(round),
                round.getTargetWord().getLength(),
                round.getRemainingLives(),
                6,
                wrongLetters(round),
                session.streak(),
                status(round),
                word,
                definition);
    }

    private static String mask(GameLogic round) {
        char[] guessed = round.getGuessedLetters();
        StringBuilder masked = new StringBuilder(guessed.length);
        for (char letter : guessed) {
            masked.append(letter == 0 ? '_' : Character.toUpperCase(letter));
        }
        return masked.toString();
    }

    private static List<Character> wrongLetters(GameLogic round) {
        List<Character> letters = new ArrayList<>();
        for (String letter : round.getWrongLettersSummary().split(" ")) {
            if (!letter.isBlank() && !letter.equals("-")) {
                letters.add(letter.charAt(0));
            }
        }
        return letters;
    }

    private static String status(GameLogic round) {
        if (round.hasWon()) {
            return "WON";
        }
        if (round.isGameOver()) {
            return "LOST";
        }
        return "IN_PROGRESS";
    }
}
