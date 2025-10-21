package com.hangman.app;

import com.hangman.controller.GameController;
import com.hangman.model.User;
import com.hangman.persistence.ScoreTracker;
import com.hangman.persistence.WordBank;
import com.hangman.ui.DifficultyScreen;
import com.hangman.ui.UserScreen;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.nio.file.Path;

/**
 * Entry point for launching the Swing application.
 */
public class HangmanLauncher {
    private final ScoreTracker scoreTracker;
    private final WordBank wordBank;

    private JFrame frame;

    public HangmanLauncher() {
        Path assetsDir = Path.of("assets");
        this.scoreTracker = new ScoreTracker(assetsDir.resolve("scores.json"));
        this.wordBank = new WordBank(assetsDir.resolve("words.json"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HangmanLauncher launcher = new HangmanLauncher();
            launcher.start();
        });
    }

    private void start() {
        scoreTracker.load();
        frame = new JFrame("Hangman Launcher");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        showUserScreen();
        frame.setVisible(true);
    }

    private void showUserScreen() {
        frame.setContentPane(new UserScreen(scoreTracker, this::showDifficultyScreen));
        frame.revalidate();
        frame.repaint();
    }

    private void showDifficultyScreen(User user) {
        frame.setContentPane(new DifficultyScreen(user, difficulty -> {
            user.setPreferredDifficulty(difficulty);
            scoreTracker.initializeUser(user.getUsername());
            scoreTracker.save();
            startGame(user, difficulty);
        }));
        frame.revalidate();
        frame.repaint();
    }

    private void startGame(User user, String difficulty) {
        frame.dispose();
        GameController controller = new GameController(wordBank, scoreTracker, user, difficulty);
        controller.start();
    }
}
