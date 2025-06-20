import javax.swing.*;
import java.awt.*;

public class AppFrame extends JFrame {
    public AppFrame(GamePanel gamePanel, WordDisplayPanel wordDisplay, KeyboardPanel keyboardPanel, JLabel infoLabel, JLabel streakLabel) {
        setTitle("Visual Hangman");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 530);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        topPanel.add(streakLabel);
        topPanel.add(infoLabel);
        topPanel.setOpaque(false);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(gamePanel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(wordDisplay);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(keyboardPanel);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        setVisible(true);
    }
}
