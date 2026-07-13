package com.hangman.web;

import com.hangman.web.LeaderboardService.LeaderboardRow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LeaderboardServiceTest {

    @Autowired
    private LeaderboardService leaderboard;

    @Autowired
    private LeaderboardRepository repository;

    @BeforeEach
    void clean() {
        repository.deleteAll();
    }

    @Test
    void persistsAndReturnsTheBestStreak() {
        leaderboard.recordStreak("alice", "easy", 5);

        List<LeaderboardRow> rows = leaderboard.topEntries("easy");
        assertThat(rows).singleElement()
                .satisfies(row -> {
                    assertThat(row.player()).isEqualTo("alice");
                    assertThat(row.bestStreak()).isEqualTo(5);
                });
    }

    @Test
    void keepsTheHighestStreakAndIgnoresLowerOnes() {
        leaderboard.recordStreak("alice", "easy", 5);
        leaderboard.recordStreak("alice", "easy", 8);
        leaderboard.recordStreak("alice", "easy", 3);

        assertThat(leaderboard.topEntries("easy")).singleElement()
                .satisfies(row -> assertThat(row.bestStreak()).isEqualTo(8));
    }

    @Test
    void enforcesOneRowPerPlayerAndDifficulty() {
        leaderboard.recordStreak("alice", "easy", 5);
        leaderboard.recordStreak("alice", "easy", 9);
        leaderboard.recordStreak("alice", "hard", 4);

        assertThat(repository.count()).isEqualTo(2);
    }

    @Test
    void ordersTopEntriesByBestStreakDescending() {
        leaderboard.recordStreak("alice", "easy", 5);
        leaderboard.recordStreak("bob", "easy", 9);
        leaderboard.recordStreak("carol", "easy", 7);

        List<LeaderboardRow> rows = leaderboard.topEntries("easy");
        assertThat(rows).extracting(LeaderboardRow::player)
                .containsExactly("bob", "carol", "alice");
    }
}
