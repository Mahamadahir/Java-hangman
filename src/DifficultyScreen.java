import javax.swing.*;
import java.awt.*;

public class DifficultyScreen extends JPanel {
    public interface DifficultySelectedListener {
        void onDifficultySelected(String difficulty);
    }

    public DifficultyScreen(User user, DifficultySelectedListener listener) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel label = new JLabel("Choose difficulty for " + user.getUsername(), SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 16));
        add(label, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        JButton easy = new JButton("Easy");
        JButton medium = new JButton("Medium");
        JButton hard = new JButton("Hard");

        buttonPanel.add(easy);
        buttonPanel.add(medium);
        buttonPanel.add(hard);
        add(buttonPanel, BorderLayout.CENTER);

        easy.addActionListener(e -> listener.onDifficultySelected("easy"));
        medium.addActionListener(e -> listener.onDifficultySelected("medium"));
        hard.addActionListener(e -> listener.onDifficultySelected("hard"));
    }
}
