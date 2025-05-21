import javax.swing.*;

public class HangmanGame {
    public static void main(String[] args) {
        // Create a basic Swing window
        JFrame frame = new JFrame("Visual Hangman");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null); // center window

        JLabel label = new JLabel("Welcome to Visual Hangman!", SwingConstants.CENTER);
        frame.add(label);

        frame.setVisible(true);
    }
}
