package com.hangman.ui;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

/**
 * Displays guessed letters inside boxes for each character slot.
 */
public class WordDisplayPanel extends JPanel {
    private char[] currentLetters = new char[0];
    private int wordLength;

    public WordDisplayPanel() {
        setPreferredSize(new Dimension(500, 60));
        setOpaque(false);
    }

    public void updateWordState(char[] guessedLetters, int length) {
        this.currentLetters = guessedLetters.clone();
        this.wordLength = length;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (wordLength == 0) {
            return;
        }

        int boxSize = 40;
        int spacing = 10;
        int totalWidth = wordLength * boxSize + (wordLength - 1) * spacing;
        int startX = Math.max((getWidth() - totalWidth) / 2, 0);
        int y = 10;

        g.setFont(new Font("SansSerif", Font.BOLD, 20));
        for (int index = 0; index < wordLength; index++) {
            int x = startX + index * (boxSize + spacing);
            g.setColor(Color.LIGHT_GRAY);
            g.fillRoundRect(x, y, boxSize, boxSize, 10, 10);
            g.setColor(Color.BLACK);
            g.drawRoundRect(x, y, boxSize, boxSize, 10, 10);

            char letter = index < currentLetters.length ? currentLetters[index] : 0;
            if (letter != 0) {
                g.drawString(String.valueOf(Character.toUpperCase(letter)), x + 12, y + 27);
            }
        }
    }
}
