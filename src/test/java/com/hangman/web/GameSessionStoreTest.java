package com.hangman.web;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GameSessionStoreTest {

    @Test
    void evictionKeepsRecentlyActiveSessions() {
        GameSessionStore store = new GameSessionStore();
        GameSession session = store.create("alice", "easy");

        store.evictIdle();

        assertThat(store.get(session.id()).id()).isEqualTo(session.id());
    }

    @Test
    void getRejectsUnknownSessions() {
        GameSessionStore store = new GameSessionStore();

        assertThatThrownBy(() -> store.get("nope"))
                .isInstanceOf(GameSessionStore.SessionNotFoundException.class);
    }
}
