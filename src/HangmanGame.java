import javax.swing.*;
import java.awt.*;

public class HangmanGame {
    private static WordBank bank;
    private static GameLogic logic;
    private static GamePanel gamePanel;
    private static WordDisplayPanel wordDisplay;
    private static KeyboardPanel keyboardPanel;
    private static JLabel infoLabel;
    private static JLabel streakLabel;

    private static String difficulty;
    private static User currentUser;
    private static ScoreTracker scoreTracker;

    public static void launchGame(User user, String chosenDifficulty) {
        currentUser = user;
        difficulty = chosenDifficulty;
        currentUser.setDifficulty(difficulty);

        bank = new WordBank("assets/words.json");
        scoreTracker = new ScoreTracker("assets/scores.json");
        scoreTracker.load(); // Load all scores into memory

        gamePanel = new GamePanel();
        wordDisplay = new WordDisplayPanel();
        keyboardPanel = new KeyboardPanel();
        infoLabel = new JLabel("", SwingConstants.CENTER);
        streakLabel = new JLabel("", SwingConstants.CENTER);
        streakLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        new AppFrame(gamePanel, wordDisplay, keyboardPanel, infoLabel, streakLabel);
        startNewGame();
    }

    private static void startNewGame() {
        logic = new GameLogic(bank.getRandomWordByDifficulty(difficulty));

        gamePanel.setMistakesMade(0);
        wordDisplay.setWordState(logic.getGuessedLetters(), logic.getCurrentWord().getWordLength());
        infoLabel.setText("Wrong guesses: ");
        updateStreakLabel();
        keyboardPanel.resetKeyboard();

        keyboardPanel.setKeyPressListener(letter -> {
            if (!logic.isGameOver()) {
                boolean correct = logic.guess(letter);

                gamePanel.setMistakesMade(logic.getMistakesMade());
                wordDisplay.setWordState(logic.getGuessedLetters(), logic.getCurrentWord().getWordLength());

                Color resultColor = correct ? new Color(144, 238, 144) : new Color(255, 102, 102);
                keyboardPanel.updateKeyColor(letter, resultColor);
                infoLabel.setText("Wrong guesses: " + logic.getWrongGuesses());

                if (logic.isGameOver()) {
                    boolean won = logic.hasWon();
                    if (won) {
                        currentUser.incrementStreak();
                        scoreTracker.recordWin(currentUser.getUsername());
                    } else {
                        currentUser.resetStreak();
                        scoreTracker.recordLoss(currentUser.getUsername());
                    }

                    updateStreakLabel();

                    String message = won
                            ? "🎉 You win!"
                            : "💀 You lose! The correct word was: " + logic.getCurrentWord().getWord();

                    int choice = JOptionPane.showConfirmDialog(null,
                            message + "\nPlay again?", "Game Over", JOptionPane.YES_NO_OPTION);

                    if (choice == JOptionPane.YES_OPTION) {
                        startNewGame();
                    } else {
                        System.exit(0);
                    }
                }
            }
        });
    }

    private static void updateStreakLabel() {
        int wins = scoreTracker.getWins(currentUser.getUsername());
        int losses = scoreTracker.getLosses(currentUser.getUsername());
        streakLabel.setText(String.format("User: %s | Difficulty: %s | Streak: %d | Wins: %d | Losses: %d",
                currentUser.getUsername(), difficulty, currentUser.getStreak(), wins, losses));
    }
}
