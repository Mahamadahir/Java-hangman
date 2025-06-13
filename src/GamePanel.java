import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private int mistakesMade;

    public GamePanel() {
        setPreferredSize(new Dimension(400, 250));
    }

    public void setMistakesMade(int mistakes) {
        this.mistakesMade = mistakes;
        repaint();
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
    }
}