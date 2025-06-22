import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.awt.*;

public class Leaderboard extends JFrame {
    public Leaderboard(ScoreTracker tracker) {
        setTitle("Leaderboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(2, 2));

        add(createTablePanel("Longest Streaks - Easy", tracker, "easy"));
        add(createTablePanel("Longest Streaks - Medium", tracker, "medium"));
        add(createTablePanel("Longest Streaks - Hard", tracker, "hard"));
        add(createTablePanel("Most Wins", tracker, null));

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createTablePanel(String title, ScoreTracker tracker, String difficulty) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(title, SwingConstants.CENTER);
        panel.add(label, BorderLayout.NORTH);

        DefaultTableModel model = new DefaultTableModel(new String[]{"Username", difficulty != null ? "Longest Streak" : "Wins"}, 0);
        JTable table = new JTable(model);

        List<String> users = tracker.getAllUsernames();
        users.sort((a, b) -> {
            int aVal = difficulty != null ? tracker.getLongestStreak(a, difficulty) : tracker.getWins(a);
            int bVal = difficulty != null ? tracker.getLongestStreak(b, difficulty) : tracker.getWins(b);
            return Integer.compare(bVal, aVal); // Descending
        });

        for (String user : users) {
            int value = difficulty != null ? tracker.getLongestStreak(user, difficulty) : tracker.getWins(user);
            if (value > 0) model.addRow(new Object[]{user, value});
        }

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }
}
