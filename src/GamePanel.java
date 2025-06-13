import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private int livesRemaining;

    public GamePanel() {}

    public void setLivesRemaining(int lives) {
        this.livesRemaining = lives;
        repaint(); // triggers repaint
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (livesRemaining <= 5) {
            g.drawOval(50, 20, 40, 40); // head
        }
        if (livesRemaining <= 4) {
            g.drawLine(70, 60, 70, 110); // body
        }
        if (livesRemaining <= 3) {
            g.drawLine(70, 70, 50, 90); // left arm
        }
        if (livesRemaining <= 2) {
            g.drawLine(70, 70, 90, 90); // right arm
        }
        if (livesRemaining <= 1) {
            g.drawLine(70, 110, 50, 140); // left leg
        }
        if (livesRemaining == 0) {
            g.drawLine(70, 110, 90, 140); // right leg
        }
    }
}
