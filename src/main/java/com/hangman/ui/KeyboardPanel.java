package com.hangman.ui;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * On-screen keyboard that mirrors physical keyboard input.
 */
public class KeyboardPanel extends JPanel {
    private final Map<Character, JButton> keyButtons = new HashMap<>();
    private Consumer<Character> listener;

    public KeyboardPanel() {
        setLayout(new GridLayout(3, 1, 5, 5));
        setPreferredSize(new Dimension(500, 150));
        setOpaque(false);

        String[] rows = { "QWERTYUIOP", "ASDFGHJKL", "ZXCVBNM" };
        for (String row : rows) {
            JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
            rowPanel.setOpaque(false);
            for (char letter : row.toCharArray()) {
                JButton button = createLetterButton(letter);
                keyButtons.put(letter, button);
                rowPanel.add(button);
            }
            add(rowPanel);
        }

        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent event) {
                char letter = Character.toUpperCase(event.getKeyChar());
                JButton button = keyButtons.get(letter);
                if (button != null && button.isEnabled()) {
                    button.doClick();
                }
            }
        });
    }

    public void setKeyListener(Consumer<Character> listener) {
        this.listener = listener;
    }

    public void resetKeys() {
        keyButtons.values().forEach(button -> {
            button.setEnabled(true);
            button.setBackground(null);
            button.setOpaque(false);
            button.setBorderPainted(true);
        });
        requestFocusInWindow();
    }

    public void disableKey(char letter) {
        JButton button = keyButtons.get(Character.toUpperCase(letter));
        if (button != null) {
            button.setEnabled(false);
        }
    }

    public void highlightKey(char letter, boolean correct) {
        JButton button = keyButtons.get(Character.toUpperCase(letter));
        if (button != null) {
            button.setOpaque(true);
            button.setBackground(correct ? KeyboardSwatch.CORRECT.colour() : KeyboardSwatch.INCORRECT.colour());
            button.setBorderPainted(false);
        }
    }

    public void disableInput() {
        keyButtons.values().forEach(button -> button.setEnabled(false));
    }

    private JButton createLetterButton(char letter) {
        JButton button = new JButton(String.valueOf(letter));
        button.setFont(new Font("Dialog", Font.BOLD, 20));
        button.setPreferredSize(new Dimension(55, 50));
        button.setFocusable(false);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.addActionListener(e -> {
            button.setEnabled(false);
            if (listener != null) {
                listener.accept(letter);
            }
        });
        return button;
    }

    private enum KeyboardSwatch {
        CORRECT(new java.awt.Color(144, 238, 144)),
        INCORRECT(new java.awt.Color(255, 102, 102));

        private final java.awt.Color colour;

        KeyboardSwatch(java.awt.Color colour) {
            this.colour = colour;
        }

        java.awt.Color colour() {
            return colour;
        }
    }
}
