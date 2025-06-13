import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class KeyboardPanel extends JPanel {
    private final Map<Character, JButton> keyButtons = new HashMap<>();

    public interface KeyPressListener {
        void onKeyPressed(char letter);
    }

    private KeyPressListener listener;

    public void setKeyPressListener(KeyPressListener listener) {
        this.listener = listener;
    }

    public KeyboardPanel() {
        setLayout(new GridLayout(3, 1, 5, 5));
        setOpaque(false);
        setPreferredSize(new Dimension(500, 150));

        String[] rows = {
                "QWERTYUIOP",
                "ASDFGHJKL",
                "ZXCVBNM"
        };

        for (String row : rows) {
            JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
            rowPanel.setOpaque(false);

            for (char c : row.toCharArray()) {
                JButton btn = new JButton(String.valueOf(c));
                btn.setFocusable(false);
                btn.setFont(new Font("Dialog", Font.BOLD, 20)); // more compact
                btn.setPreferredSize(new Dimension(55, 50));    // wider box
                btn.setHorizontalAlignment(SwingConstants.CENTER);
                btn.setVerticalAlignment(SwingConstants.CENTER);
                char upperC = c;
                btn.addActionListener(e -> {
                    if (listener != null) {
                        btn.setEnabled(false);
                        listener.onKeyPressed(upperC);
                    }
                });
                keyButtons.put(c, btn);
                rowPanel.add(btn);
            }
            add(rowPanel);
        }

        // Add keyboard listener to simulate button clicks
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = Character.toUpperCase(e.getKeyChar());
                if (keyButtons.containsKey(c)) {
                    keyButtons.get(c).doClick();
                }
            }
        });
    }

    public Map<Character, JButton> getKeyButtons() {
        return keyButtons;
    }

    public void updateKeyColor(char letter, Color color) {
        JButton btn = keyButtons.get(Character.toUpperCase(letter));
        if (btn != null) {
            btn.setBackground(color);
            btn.setOpaque(true);
            btn.setBorderPainted(false);
        }
    }
    public void resetKeyboard() {
        for (JButton btn : keyButtons.values()) {
            btn.setEnabled(true);
            btn.setBackground(null);
            btn.setOpaque(false);
            btn.setBorderPainted(true);
        }
        requestFocusInWindow(); // ensures keyboard input still works
    }

}