import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class HangmanGame {
    public static void main(String[] args) {
        WordBank bank = new WordBank("assets/words.json");
        GameLogic logic = new GameLogic(bank.getRandomWord());

        JFrame frame = new JFrame("Visual Hangman");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);

        JLabel wrongLabel = new JLabel("Wrong guesses: ", SwingConstants.CENTER);

        GamePanel panel = new GamePanel();
        panel.setMistakesMade(0);
        panel.setGuessedLetters(logic.getGuessedLetters());
        panel.setWordLength(logic.getCurrentWord().getWordLength());

        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        frame.add(wrongLabel, BorderLayout.SOUTH);

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!logic.isGameOver()) {
                    char c = Character.toLowerCase(e.getKeyChar());
                    if (Character.isLetter(c)) {
                        logic.guess(c);

                        // Update visual panel
                        panel.setMistakesMade(logic.getMistakesMade());
                        panel.setGuessedLetters(logic.getGuessedLetters());
                        panel.repaint();

                        wrongLabel.setText("Wrong guesses: " + logic.getWrongGuesses());

                        if (logic.isGameOver()) {
                            JOptionPane.showMessageDialog(frame,
                                    logic.hasWon() ? "You win!" : "You lose!",
                                    "Game Over",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            }
        });

        frame.setVisible(true);
    }
}
