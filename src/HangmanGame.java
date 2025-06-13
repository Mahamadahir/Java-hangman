import javax.swing.*;
import java.awt.*;

public class HangmanGame {
    public static void main(String[] args) {
        WordBank bank = new WordBank("assets/words.json");
        GameLogic logic = new GameLogic(bank.getRandomWord());

        GamePanel gamePanel = new GamePanel();
        WordDisplayPanel wordDisplay = new WordDisplayPanel();
        KeyboardPanel keyboardPanel = new KeyboardPanel();
        JLabel infoLabel = new JLabel("Wrong guesses: ", SwingConstants.CENTER);

        // Initial state setup
        gamePanel.setMistakesMade(0);
        wordDisplay.setWordState(logic.getGuessedLetters(), logic.getCurrentWord().getWordLength());

        // Let the keyboard panel manage button and key input
        keyboardPanel.setKeyPressListener(letter -> {
            if (!logic.isGameOver()) {
                boolean correct = logic.guess(letter);

                // Update display components
                gamePanel.setMistakesMade(logic.getMistakesMade());
                wordDisplay.setWordState(logic.getGuessedLetters(), logic.getCurrentWord().getWordLength());

                Color resultColor = correct ? new Color(144, 238, 144) : new Color(255, 102, 102);
                keyboardPanel.updateKeyColor(letter, resultColor);

                infoLabel.setText("Wrong guesses: " + logic.getWrongGuesses());

                if (logic.isGameOver()) {
                    JOptionPane.showMessageDialog(null,
                            logic.hasWon() ? "🎉 You win!" : "💀 You lose!",
                            "Game Over",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // Launch the application
        SwingUtilities.invokeLater(() ->
                new AppFrame(gamePanel, wordDisplay, keyboardPanel, infoLabel)
        );
    }
}
