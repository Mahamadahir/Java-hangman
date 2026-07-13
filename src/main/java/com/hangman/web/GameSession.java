package com.hangman.web;

import com.hangman.logic.GameLogic;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

/**
 * Server-side state for one player's run. Holds the secret word (via
 * {@link GameLogic}) so it is never sent to the browser until a round ends,
 * which keeps the shared leaderboard honest.
 */
class GameSession {
    private final String id;
    private final String playerName;
    private final String difficulty;
    private final List<String> seenWords = new ArrayList<>();

    /*
     * One lock per session rather than synchronising the whole store. Two
     * requests for the same gameId are the only ones that can corrupt this
     * session's mutable state, and a single player's browser rarely fires those
     * concurrently. Locking the store instead would serialise every player's
     * moves through one monitor, which throttles unrelated games for no reason.
     * Because all mutation of this session and its GameLogic runs under this
     * lock, GameLogic needs no synchronisation of its own.
     */
    private final ReentrantLock lock = new ReentrantLock();

    private GameLogic round;
    private int streak;
    private Instant lastActive = Instant.now();

    GameSession(String id, String playerName, String difficulty) {
        this.id = id;
        this.playerName = playerName;
        this.difficulty = difficulty;
    }

    String id() {
        return id;
    }

    String playerName() {
        return playerName;
    }

    String difficulty() {
        return difficulty;
    }

    GameLogic round() {
        return round;
    }

    int streak() {
        return streak;
    }

    List<String> seenWords() {
        return seenWords;
    }

    void startRound(GameLogic logic) {
        this.round = logic;
        seenWords.add(logic.getTargetWord().getValue());
        touch();
    }

    void recordOutcome(boolean won) {
        streak = won ? streak + 1 : 0;
    }

    void touch() {
        lastActive = Instant.now();
    }

    Instant lastActive() {
        return lastActive;
    }

    <T> T runExclusive(Supplier<T> action) {
        lock.lock();
        try {
            return action.get();
        } finally {
            lock.unlock();
        }
    }
}
