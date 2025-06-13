import javax.swing.*;
import java.awt.*;

public class HangmanGame {
    private static WordBank bank;
    private static GameLogic logic;
    private static GamePanel gamePanel;
    private static WordDisplayPanel wordDisplay;
    private static KeyboardPanel keyboardPanel;
    private static JLabel infoLabel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            bank = new WordBank("assets/words.json");
            gamePanel = new GamePanel();
            wordDisplay = new WordDisplayPanel();
            keyboardPanel = new KeyboardPanel();
            infoLabel = new JLabel("Wrong guesses: ", SwingConstants.CENTER);

            new AppFrame(gamePanel, wordDisplay, keyboardPanel, infoLabel);
            startNewGame();
        });
    }

    public static void startNewGame() {
        logic = new GameLogic(bank.getRandomWord());

        gamePanel.setMistakesMade(0);
        wordDisplay.setWordState(logic.getGuessedLetters(), logic.getCurrentWord().getWordLength());
        infoLabel.setText("Wrong guesses: ");
        keyboardPanel.resetKeyboard();  // you must implement this method

        keyboardPanel.setKeyPressListener(letter -> {
            if (!logic.isGameOver()) {
                boolean correct = logic.guess(letter);

                gamePanel.setMistakesMade(logic.getMistakesMade());
                wordDisplay.setWordState(logic.getGuessedLetters(), logic.getCurrentWord().getWordLength());

                Color resultColor = correct ? new Color(144, 238, 144) : new Color(255, 102, 102);
                keyboardPanel.updateKeyColor(letter, resultColor);

                infoLabel.setText("Wrong guesses: " + logic.getWrongGuesses());

                if (logic.isGameOver()) {
                    String message = logic.hasWon()
                            ? "🎉 You win!"
                            : "💀 You lose! The correct word was: " + logic.getCurrentWord().getWord();
                    int choice = JOptionPane.showConfirmDialog(null, message + "\nPlay again?", "Game Over", JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        startNewGame();
                    } else {
                        System.exit(0);
                    }
                }
            }
        });
    }
}
