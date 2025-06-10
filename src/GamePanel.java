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
        // Draw hangman depending on livesRemaining
    }
}
