package com.hangman.ui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.GridLayout;

/**
 * Main game window that anchors all interactive panels.
 */
public class AppFrame extends JFrame {

    public AppFrame(GamePanel gamePanel,
                    WordDisplayPanel wordDisplayPanel,
                    KeyboardPanel keyboardPanel,
                    JLabel infoLabel,
                    JLabel streakLabel,
                    Runnable leaderboardAction) {
        super("Visual Hangman");
        SwingUtilities.invokeLater(() -> initialise(gamePanel, wordDisplayPanel, keyboardPanel,
                infoLabel, streakLabel, leaderboardAction));
    }

    private void initialise(GamePanel gamePanel,
                            WordDisplayPanel wordDisplayPanel,
                            KeyboardPanel keyboardPanel,
                            JLabel infoLabel,
                            JLabel streakLabel,
                            Runnable leaderboardAction) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(720, 540);
        setLocationRelativeTo(null);

        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.add(streakLabel);
        infoPanel.add(infoLabel);

        JButton leaderboardButton = new JButton("Stats");
        leaderboardButton.addActionListener(e -> leaderboardAction.run());

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(infoPanel, BorderLayout.CENTER);
        northPanel.add(leaderboardButton, BorderLayout.EAST);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3, 1, 10, 10));
        centerPanel.add(gamePanel);
        centerPanel.add(wordDisplayPanel);
        centerPanel.add(keyboardPanel);

        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        setVisible(true);
    }
}
