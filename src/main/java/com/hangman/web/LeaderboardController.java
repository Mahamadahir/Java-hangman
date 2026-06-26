package com.hangman.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
class LeaderboardController {
    private final LeaderboardService leaderboard;

    LeaderboardController(LeaderboardService leaderboard) {
        this.leaderboard = leaderboard;
    }

    @GetMapping("/api/leaderboard")
    List<LeaderboardService.LeaderboardRow> top(@RequestParam(required = false) String difficulty) {
        return leaderboard.topEntries(difficulty);
    }
}
