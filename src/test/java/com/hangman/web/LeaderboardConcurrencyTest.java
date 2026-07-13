package com.hangman.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Proves the section 3 fix: concurrent recordStreak calls for the same
 * (player, difficulty) neither throw on the unique constraint nor lose the
 * highest streak, and create exactly one row.
 */
@SpringBootTest
class LeaderboardConcurrencyTest {

    @Autowired
    private LeaderboardService leaderboard;

    @Autowired
    private LeaderboardRepository repository;

    @BeforeEach
    void clean() {
        repository.deleteAll();
    }

    @Test
    void concurrentWritesForANewPlayerCreateOneRowWithTheHighestStreak() throws Exception {
        int threads = 32;
        ExecutorService pool = Executors.newFixedThreadPool(threads);
        CountDownLatch start = new CountDownLatch(1);

        List<Future<?>> futures = new ArrayList<>();
        for (int i = 1; i <= threads; i++) {
            int streak = i;
            futures.add(pool.submit(() -> {
                start.await();
                leaderboard.recordStreak("racer", "easy", streak);
                return null;
            }));
        }

        start.countDown();
        for (Future<?> future : futures) {
            future.get();
        }
        pool.shutdown();

        assertThat(repository.count()).isEqualTo(1);
        assertThat(leaderboard.topEntries("easy")).singleElement()
                .satisfies(row -> assertThat(row.bestStreak()).isEqualTo(threads));
    }
}
