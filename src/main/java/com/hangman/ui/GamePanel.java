package com.hangman.ui;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;

/**
 * Draws the gallows and hangman body parts based on mistakes made.
 */
public class GamePanel extends JPanel {
    private int mistakesMade;

    public GamePanel() {
        setPreferredSize(new Dimension(400, 250));
        setOpaque(false);
    }

    public void setMistakesMade(int mistakesMade) {
        this.mistakesMade = mistakesMade;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawLine(50, 20, 50, 140);
        g.drawLine(50, 20, 110, 20);
        g.drawLine(110, 20, 110, 40);
        g.drawLine(30, 140, 70, 140);

        if (mistakesMade >= 1) g.drawOval(90, 40, 40, 40);
        if (mistakesMade >= 2) g.drawLine(110, 80, 110, 130);
        if (mistakesMade >= 3) g.drawLine(110, 90, 90, 110);
        if (mistakesMade >= 4) g.drawLine(110, 90, 130, 110);
        if (mistakesMade >= 5) g.drawLine(110, 130, 90, 160);
        if (mistakesMade >= 6) g.drawLine(110, 130, 130, 160);
    }
}
