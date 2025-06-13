// GamePanel.java
import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private int mistakesMade;
    private char[] guessedLetters;
    private int wordLength;

    public GamePanel() {
        setPreferredSize(new Dimension(400, 250));
    }

    public void setMistakesMade(int mistakes) {
        this.mistakesMade = mistakes;
        repaint();
    }

    public void setGuessedLetters(char[] guessedLetters) {
        this.guessedLetters = guessedLetters;
    }

    public void setWordLength(int length) {
        this.wordLength = length;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw gallows
        g.drawLine(50, 20, 50, 140); // vertical post
        g.drawLine(50, 20, 110, 20); // top beam
        g.drawLine(110, 20, 110, 40); // rope
        g.drawLine(30, 140, 70, 140); // base

        // Draw hangman parts based on mistakesMade
        if (mistakesMade >= 1) g.drawOval(90, 40, 40, 40);                // head
        if (mistakesMade >= 2) g.drawLine(110, 80, 110, 130);             // body
        if (mistakesMade >= 3) g.drawLine(110, 90, 90, 110);              // left arm
        if (mistakesMade >= 4) g.drawLine(110, 90, 130, 110);             // right arm
        if (mistakesMade >= 5) g.drawLine(110, 130, 90, 160);             // left leg
        if (mistakesMade >= 6) g.drawLine(110, 130, 130, 160);            // right leg

        // Draw word letter boxes
        if (guessedLetters != null) {
            drawWordBoxes(g);
        }
    }

    private void drawWordBoxes(Graphics g) {
        int boxWidth = 30;
        int spacing = 10;
        int startX = 20;
        int y = 200;

        for (int i = 0; i < wordLength; i++) {
            int x = startX + i * (boxWidth + spacing);
            g.drawRect(x, y, boxWidth, boxWidth);

            char c = (i < guessedLetters.length) ? guessedLetters[i] : 0;
            if (c != 0) {
                g.drawString(String.valueOf(c), x + 10, y + 20);
            }
        }
    }
}