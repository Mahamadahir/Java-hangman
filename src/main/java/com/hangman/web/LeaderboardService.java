package com.hangman.web;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
class LeaderboardService {
    private final LeaderboardRepository repository;

    LeaderboardService(LeaderboardRepository repository) {
        this.repository = repository;
    }

    @Transactional
    void recordStreak(String player, String difficulty, int streak) {
        if (streak <= 0) {
            return;
        }
        LeaderboardEntry entry = repository.findByPlayerAndDifficulty(player, difficulty)
                .orElseGet(() -> new LeaderboardEntry(player, difficulty, 0));
        entry.recordStreak(streak);
        repository.save(entry);
    }

    @Transactional(readOnly = true)
    List<LeaderboardRow> topEntries(String difficulty) {
        List<LeaderboardEntry> entries = (difficulty == null || difficulty.isBlank())
                ? repository.findTop10ByOrderByBestStreakDescUpdatedAtAsc()
                : repository.findTop10ByDifficultyOrderByBestStreakDescUpdatedAtAsc(difficulty);
        return entries.stream()
                .map(e -> new LeaderboardRow(e.getPlayer(), e.getDifficulty(), e.getBestStreak()))
                .toList();
    }

    record LeaderboardRow(String player, String difficulty, int bestStreak) {
    }
}
