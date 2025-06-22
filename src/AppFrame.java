import javax.swing.*;
import java.awt.*;

public class AppFrame extends JFrame {
    public AppFrame(GamePanel gamePanel, WordDisplayPanel wordDisplay, KeyboardPanel keyboardPanel, JLabel infoLabel, JLabel streakLabel, ScoreTracker scoreTracker) {
        setTitle("Visual Hangman");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 530);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Create stats button
        JButton statsButton = new JButton("Stats");
        statsButton.addActionListener(e -> new Leaderboard(scoreTracker));

        // Combine streak/info labels and stats button
        JPanel labelPanel = new JPanel(new GridLayout(2, 1));
        labelPanel.add(streakLabel);
        labelPanel.add(infoLabel);
        labelPanel.setOpaque(false);

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(labelPanel, BorderLayout.CENTER);
        northPanel.add(statsButton, BorderLayout.EAST);
        northPanel.setOpaque(false);

        // Center layout
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(gamePanel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(wordDisplay);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(keyboardPanel);

        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        setVisible(true);
    }
}
