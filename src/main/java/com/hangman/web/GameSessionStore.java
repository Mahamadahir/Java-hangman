package com.hangman.web;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory registry of active game sessions. Idle sessions are evicted lazily
 * so abandoned games do not accumulate; a pod restart simply drops in-flight
 * games, which is acceptable for a casual leaderboard.
 */
@Component
class GameSessionStore {
    private static final Duration MAX_IDLE = Duration.ofHours(2);

    private final Map<String, GameSession> sessions = new ConcurrentHashMap<>();

    GameSession create(String playerName, String difficulty) {
        evictIdle();
        String id = UUID.randomUUID().toString();
        GameSession session = new GameSession(id, playerName, difficulty);
        sessions.put(id, session);
        return session;
    }

    GameSession get(String id) {
        GameSession session = sessions.get(id);
        if (session == null) {
            throw new SessionNotFoundException(id);
        }
        session.touch();
        return session;
    }

    private void evictIdle() {
        Instant cutoff = Instant.now().minus(MAX_IDLE);
        sessions.values().removeIf(session -> session.lastActive().isBefore(cutoff));
    }

    static class SessionNotFoundException extends RuntimeException {
        SessionNotFoundException(String id) {
            super("No active game for session " + id);
        }
    }
}
