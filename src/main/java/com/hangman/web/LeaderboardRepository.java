package com.hangman.web;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

interface LeaderboardRepository extends JpaRepository<LeaderboardEntry, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select e from LeaderboardEntry e where e.player = :player and e.difficulty = :difficulty")
    Optional<LeaderboardEntry> findByPlayerAndDifficultyForUpdate(
            @Param("player") String player, @Param("difficulty") String difficulty);

    List<LeaderboardEntry> findTop10ByDifficultyOrderByBestStreakDescUpdatedAtAsc(String difficulty);

    List<LeaderboardEntry> findTop10ByOrderByBestStreakDescUpdatedAtAsc();
}
