package com.hangman.web;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/game")
class GameApiController {
    private final GamePlayService gamePlay;

    GameApiController(GamePlayService gamePlay) {
        this.gamePlay = gamePlay;
    }

    @PostMapping
    GameState start(@RequestBody StartRequest request) {
        return gamePlay.startGame(request.playerName(), request.difficulty());
    }

    @PostMapping("/{gameId}/guess")
    GameState guess(@PathVariable String gameId, @RequestBody GuessRequest request) {
        return gamePlay.guess(gameId, request.letter());
    }

    @PostMapping("/{gameId}/next")
    GameState next(@PathVariable String gameId) {
        return gamePlay.nextRound(gameId);
    }

    record StartRequest(String playerName, String difficulty) {
    }

    record GuessRequest(String letter) {
    }
}
