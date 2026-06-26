package com.hangman.web;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.time.Instant;

@Entity
@Table(
        name = "leaderboard",
        uniqueConstraints = @UniqueConstraint(columnNames = {"player", "difficulty"}))
class LeaderboardEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String player;

    @Column(nullable = false)
    private String difficulty;

    @Column(name = "best_streak", nullable = false)
    private int bestStreak;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();

    protected LeaderboardEntry() {
    }

    LeaderboardEntry(String player, String difficulty, int bestStreak) {
        this.player = player;
        this.difficulty = difficulty;
        this.bestStreak = bestStreak;
    }

    String getPlayer() {
        return player;
    }

    String getDifficulty() {
        return difficulty;
    }

    int getBestStreak() {
        return bestStreak;
    }

    Instant getUpdatedAt() {
        return updatedAt;
    }

    void recordStreak(int streak) {
        if (streak > bestStreak) {
            bestStreak = streak;
            updatedAt = Instant.now();
        }
    }
}
