package com.hangman.web;

import com.hangman.logic.GameLogic;
import com.hangman.model.Word;
import com.hangman.persistence.WordBank;
import com.hangman.service.DictionaryProvider;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Set;

@Service
class GamePlayService {
    private static final Set<String> DIFFICULTIES = Set.of("easy", "medium", "hard");

    private final WordBank wordBank;
    private final DictionaryProvider dictionary;
    private final LeaderboardService leaderboard;
    private final GameSessionStore sessions;

    GamePlayService(WordBank wordBank, DictionaryProvider dictionary,
                    LeaderboardService leaderboard, GameSessionStore sessions) {
        this.wordBank = wordBank;
        this.dictionary = dictionary;
        this.leaderboard = leaderboard;
        this.sessions = sessions;
    }

    GameState startGame(String playerName, String difficulty) {
        String name = sanitiseName(playerName);
        String level = validateDifficulty(difficulty);
        GameSession session = sessions.create(name, level);
        return session.runExclusive(() -> startRound(session));
    }

    GameState nextRound(String gameId) {
        GameSession session = sessions.get(gameId);
        return session.runExclusive(() -> startRound(session));
    }

    GameState guess(String gameId, String rawLetter) {
        GameSession session = sessions.get(gameId);
        char letter = validateLetter(rawLetter);
        return session.runExclusive(() -> {
            GameLogic round = session.round();
            GameLogic.GuessResult result = round.submitGuess(letter);

            if (!round.isGameOver()) {
                return GameState.of(session, result, null, null);
            }

            boolean won = round.hasWon();
            session.recordOutcome(won);
            if (won) {
                leaderboard.recordStreak(session.playerName(), session.difficulty(), session.streak());
            }
            String word = round.getTargetWord().getValue();
            return GameState.of(session, result, word, dictionary.define(word));
        });
    }

    private GameState startRound(GameSession session) {
        Word word = wordBank.pickRandomWord(session.difficulty(), session.seenWords());
        session.startRound(new GameLogic(word));
        return GameState.of(session, null, null);
    }

    private String sanitiseName(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidInputException("Player name is required");
        }
        String trimmed = name.trim();
        return trimmed.length() > 20 ? trimmed.substring(0, 20) : trimmed;
    }

    private String validateDifficulty(String difficulty) {
        String level = difficulty == null ? "" : difficulty.trim().toLowerCase(Locale.ENGLISH);
        if (!DIFFICULTIES.contains(level)) {
            throw new InvalidInputException("Difficulty must be one of " + DIFFICULTIES);
        }
        return level;
    }

    private char validateLetter(String rawLetter) {
        if (rawLetter == null || rawLetter.length() != 1 || !Character.isLetter(rawLetter.charAt(0))) {
            throw new InvalidInputException("A single letter is required");
        }
        return Character.toLowerCase(rawLetter.charAt(0));
    }

    static class InvalidInputException extends RuntimeException {
        InvalidInputException(String message) {
            super(message);
        }
    }
}
