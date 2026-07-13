package com.hangman.web;

import com.hangman.logic.GameLogic;
import com.hangman.persistence.WordBank;
import com.hangman.service.DictionaryProvider;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Proves the section 2 fix: concurrent guesses on one session are serialised by
 * the per-session lock, so mistake counting never loses an increment. Only wrong
 * guesses are made, so the round never ends and the dictionary and leaderboard
 * collaborators are never touched.
 */
class GamePlayServiceConcurrencyTest {

    private static final String WORD = "programming";
    private static final char[] WRONG_LETTERS = {'b', 'c', 'd', 'e', 'f'};

    @Test
    void concurrentGuessesDoNotLoseMistakeIncrements() throws Exception {
        GameSessionStore store = new GameSessionStore();
        WordBank wordBank = new WordBank(new StringReader(
                "[{\"word\":\"" + WORD + "\",\"difficulty\":\"easy\",\"wordLength\":" + WORD.length() + "}]"));
        GamePlayService service = new GamePlayService(
                wordBank, new DictionaryProvider(), new LeaderboardService(null, null), store);

        String gameId = service.startGame("racer", "easy").gameId();

        ExecutorService pool = Executors.newFixedThreadPool(16);
        List<Future<?>> futures = new ArrayList<>();
        for (int repeat = 0; repeat < 40; repeat++) {
            for (char wrong : WRONG_LETTERS) {
                String letter = String.valueOf(wrong);
                futures.add(pool.submit(() -> service.guess(gameId, letter)));
            }
        }
        for (Future<?> future : futures) {
            future.get();
        }
        pool.shutdown();

        GameLogic round = store.get(gameId).round();
        assertThat(round.getMistakesMade()).isEqualTo(WRONG_LETTERS.length);
        assertThat(round.isGameOver()).isFalse();
    }
}
