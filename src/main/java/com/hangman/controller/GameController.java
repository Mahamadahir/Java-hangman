package com.hangman.controller;

import com.hangman.logic.GameLogic;
import com.hangman.model.User;
import com.hangman.model.Word;
import com.hangman.persistence.ScoreTracker;
import com.hangman.persistence.WordBank;
import com.hangman.ui.AppFrame;
import com.hangman.ui.GamePanel;
import com.hangman.ui.KeyboardPanel;
import com.hangman.ui.WordDisplayPanel;
import com.hangman.ui.dialog.LeaderboardDialog;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Coordinates the game flow between UI components and domain logic.
 */
public class GameController {
    private final WordBank wordBank;
    private final ScoreTracker scoreTracker;
    private final User user;
    private final String difficulty;

    private final Set<String> usedWords = new HashSet<>();
    private final JLabel infoLabel = new JLabel("Wrong guesses: -", SwingConstants.CENTER);
    private final JLabel streakLabel = new JLabel("", SwingConstants.CENTER);
    private final GamePanel gamePanel = new GamePanel();
    private final WordDisplayPanel wordDisplayPanel = new WordDisplayPanel();
    private final KeyboardPanel keyboardPanel = new KeyboardPanel();

    private GameLogic logic;
    private AppFrame appFrame;

    public GameController(WordBank wordBank, ScoreTracker scoreTracker, User user, String difficulty) {
        this.wordBank = wordBank;
        this.scoreTracker = scoreTracker;
        this.user = user;
        this.difficulty = difficulty;
    }

    public void start() {
        streakLabel.setFont(streakLabel.getFont().deriveFont(14f));
        keyboardPanel.setKeyListener(this::handleGuess);
        appFrame = new AppFrame(gamePanel, wordDisplayPanel, keyboardPanel, infoLabel, streakLabel, this::openLeaderboard);
        startNewRound();
    }

    private void handleGuess(char letter) {
        GameLogic.GuessResult result = logic.submitGuess(letter);
        switch (result) {
            case CORRECT -> {
                keyboardPanel.highlightKey(letter, true);
                refreshBoard();
            }
            case INCORRECT -> {
                keyboardPanel.highlightKey(letter, false);
                refreshBoard();
                infoLabel.setText("Wrong guesses: " + logic.getWrongLettersSummary());
            }
            case ALREADY_GUESSED -> infoLabel.setText("Already tried that letter.");
            case INVALID -> infoLabel.setText("Please enter alphabetic characters only.");
            case GAME_OVER -> { /* ignored */ }
        }

        if (logic.isGameOver()) {
            concludeRound();
        }
    }

    private void concludeRound() {
        keyboardPanel.disableInput();
        boolean won = logic.hasWon();
        if (won) {
            user.incrementStreak(difficulty);
            scoreTracker.recordWin(user, difficulty);
        } else {
            int streakBeforeReset = user.getCurrentStreak(difficulty);
            scoreTracker.recordLoss(user, difficulty, streakBeforeReset);
            user.resetStreak(difficulty);
        }
        scoreTracker.save();
        updateStreakLabel();

        String message = won
                ? "Well played! You uncovered " + logic.getTargetWord().getValue().toUpperCase() + "."
                : "Out of lives! The word was " + logic.getTargetWord().getValue().toUpperCase() + ".";

        if (won) {
            JOptionPane.showMessageDialog(appFrame, message, "Round Complete", JOptionPane.INFORMATION_MESSAGE);
            startNewRound();
        } else {
            int choice = JOptionPane.showConfirmDialog(appFrame,
                    message + "\nPlay another round?", "Round Complete",
                    JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                usedWords.clear();
                startNewRound();
            } else {
                appFrame.dispose();
            }
        }
    }

    private void startNewRound() {
        keyboardPanel.resetKeys();
        logic = new GameLogic(nextWord());
        infoLabel.setText("Wrong guesses: -");
        refreshBoard();
        updateStreakLabel();
    }

    private void refreshBoard() {
        gamePanel.setMistakesMade(logic.getMistakesMade());
        wordDisplayPanel.updateWordState(logic.getGuessedLetters(), logic.getTargetWord().getLength());
    }

    private Word nextWord() {
        List<String> exclusions = new ArrayList<>(usedWords);
        Word word = wordBank.pickRandomWord(difficulty, exclusions);
        usedWords.add(word.getValue());
        return word;
    }

    private void updateStreakLabel() {
        String text = String.format(
                "Player: %s | Difficulty: %s | Streak: %d | Wins: %d | Losses: %d",
                user.getUsername(),
                difficulty,
                user.getCurrentStreak(difficulty),
                scoreTracker.getWins(user.getUsername()),
                scoreTracker.getLosses(user.getUsername())
        );
        streakLabel.setText(text);
    }

    private void openLeaderboard() {
        new LeaderboardDialog(appFrame, scoreTracker);
    }
}
