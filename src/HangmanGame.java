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

        JLabel wordLabel = new JLabel(logic.getDisplayWord(), SwingConstants.CENTER);
        JLabel wrongLabel = new JLabel("", SwingConstants.CENTER);
        GamePanel panel = new GamePanel();
        panel.setLivesRemaining(logic.getLivesRemaining());

        frame.setLayout(new BorderLayout());
        frame.add(wordLabel, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.add(wrongLabel, BorderLayout.SOUTH);

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!logic.isGameOver()) {
                    char c = e.getKeyChar();
                    if (Character.isLetter(c)) {
                        logic.guess(c);
                        wordLabel.setText(logic.getDisplayWord());
                        wrongLabel.setText("Wrong guesses: " + logic.getWrongGuesses());
                        panel.setLivesRemaining(logic.getLivesRemaining());
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
