package com.hangman.web;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

interface LeaderboardRepository extends JpaRepository<LeaderboardEntry, Long> {

    Optional<LeaderboardEntry> findByPlayerAndDifficulty(String player, String difficulty);

    List<LeaderboardEntry> findTop10ByDifficultyOrderByBestStreakDescUpdatedAtAsc(String difficulty);

    List<LeaderboardEntry> findTop10ByOrderByBestStreakDescUpdatedAtAsc();
}
