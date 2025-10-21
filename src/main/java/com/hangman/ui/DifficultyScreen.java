package com.hangman.ui;

import com.hangman.model.User;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Font;
import java.util.function.Consumer;

/**
 * Allows the player to choose a difficulty before starting.
 */
public class DifficultyScreen extends JPanel {

    public DifficultyScreen(User user, Consumer<String> callback) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel label = new JLabel("Choose difficulty for " + user.getUsername(), SwingConstants.CENTER);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 16f));
        add(label, BorderLayout.NORTH);

        JPanel buttons = new JPanel(new java.awt.GridLayout(3, 1, 10, 10));
        add(buttons, BorderLayout.CENTER);

        addButton(buttons, "Easy", () -> callback.accept("easy"));
        addButton(buttons, "Medium", () -> callback.accept("medium"));
        addButton(buttons, "Hard", () -> callback.accept("hard"));
    }

    private void addButton(JPanel panel, String label, Runnable action) {
        JButton button = new JButton(label);
        button.addActionListener(e -> action.run());
        panel.add(button);
    }
}
