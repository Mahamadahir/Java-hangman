package com.hangman.web;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Service
class LeaderboardService {
    private final LeaderboardRepository repository;
    private final TransactionTemplate transactionTemplate;

    LeaderboardService(LeaderboardRepository repository, PlatformTransactionManager transactionManager) {
        this.repository = repository;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }

    /*
     * The (player, difficulty) row is uniquely constrained, so a naive
     * find-or-create races two ways: concurrent updates to an existing row lose
     * the higher streak, and two first-time writers both insert and one hits the
     * constraint. A native upsert would fix both but needs different SQL for H2
     * (dev) and Postgres (prod), so instead: take a pessimistic write lock on the
     * existing row to serialise updates, and if no row exists yet, insert and
     * catch the unique-constraint violation, retrying into the now-locked update
     * path. Both branches run as their own transaction so a failed insert rolls
     * back cleanly before the retry.
     */
    void recordStreak(String player, String difficulty, int streak) {
        if (streak <= 0) {
            return;
        }
        while (!raiseExistingBestStreak(player, difficulty, streak)) {
            if (insertNewEntry(player, difficulty, streak)) {
                return;
            }
        }
    }

    private boolean raiseExistingBestStreak(String player, String difficulty, int streak) {
        return Boolean.TRUE.equals(transactionTemplate.execute(status ->
                repository.findByPlayerAndDifficultyForUpdate(player, difficulty)
                        .map(entry -> {
                            entry.recordStreak(streak);
                            return true;
                        })
                        .orElse(false)));
    }

    private boolean insertNewEntry(String player, String difficulty, int streak) {
        try {
            transactionTemplate.executeWithoutResult(status ->
                    repository.saveAndFlush(new LeaderboardEntry(player, difficulty, streak)));
            return true;
        } catch (DataIntegrityViolationException concurrentInsert) {
            return false;
        }
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
