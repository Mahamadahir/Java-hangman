import javax.swing.*;
import java.awt.*;

public class WordDisplayPanel extends JPanel {
    private char[] guessedLetters;
    private int wordLength;

    public WordDisplayPanel() {
        setPreferredSize(new Dimension(500, 60));
        setOpaque(false);
    }

    public void setWordState(char[] guessedLetters, int wordLength) {
        this.guessedLetters = guessedLetters;
        this.wordLength = wordLength;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (guessedLetters == null || wordLength == 0) return;

        int boxSize = 40;
        int spacing = 10;
        int startX = (getWidth() - (wordLength * (boxSize + spacing))) / 2;
        int y = 10;

        for (int i = 0; i < wordLength; i++) {
            int x = startX + i * (boxSize + spacing);
            g.setColor(Color.LIGHT_GRAY);
            g.fillRoundRect(x, y, boxSize, boxSize, 10, 10);
            g.setColor(Color.BLACK);
            g.drawRoundRect(x, y, boxSize, boxSize, 10, 10);

            if (guessedLetters[i] != 0) {
                g.setFont(new Font("SansSerif", Font.BOLD, 20));
                g.drawString(String.valueOf(guessedLetters[i]).toUpperCase(), x + 13, y + 28);
            }
        }
    }
}
