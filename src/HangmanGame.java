import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class HangmanGame {
    public static void main(String[] args) {
        WordBank bank = new WordBank("assets/words.json");
        GameLogic logic = new GameLogic(bank.getRandomWord());

        // Create core components
        GamePanel gamePanel = new GamePanel();
        WordDisplayPanel wordDisplay = new WordDisplayPanel();
        KeyboardPanel keyboardPanel = new KeyboardPanel();
        JLabel infoLabel = new JLabel("Wrong guesses: ", SwingConstants.CENTER);

        // Initial UI state
        gamePanel.setMistakesMade(0);
        gamePanel.setGuessedLetters(logic.getGuessedLetters());
        gamePanel.setWordLength(logic.getCurrentWord().getWordLength());

        wordDisplay.setWordState(logic.getGuessedLetters(), logic.getCurrentWord().getWordLength());

        // Wire keyboard buttons
        Map<Character, JButton> buttons = keyboardPanel.getKeyButtons();
        for (Map.Entry<Character, JButton> entry : buttons.entrySet()) {
            char letter = entry.getKey();
            JButton button = entry.getValue();

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!logic.isGameOver()) {
                        boolean correct = logic.guess(letter);

                        // Update visuals
                        gamePanel.setMistakesMade(logic.getMistakesMade());
                        gamePanel.setGuessedLetters(logic.getGuessedLetters());
                        wordDisplay.setWordState(logic.getGuessedLetters(), logic.getCurrentWord().getWordLength());

                        Color resultColor = correct ? new Color(144, 238, 144) : new Color(255, 102, 102);
                        keyboardPanel.updateKeyColor(letter, resultColor);

                        infoLabel.setText("Wrong guesses: " + logic.getWrongGuesses());

                        // Disable button after guess
                        button.setEnabled(false);

                        if (logic.isGameOver()) {
                            JOptionPane.showMessageDialog(null,
                                    logic.hasWon() ? "🎉 You win!" : "💀 You lose!",
                                    "Game Over",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            });
        }

        // Launch app
        SwingUtilities.invokeLater(() -> new AppFrame(gamePanel, wordDisplay, keyboardPanel, infoLabel));
    }
}
