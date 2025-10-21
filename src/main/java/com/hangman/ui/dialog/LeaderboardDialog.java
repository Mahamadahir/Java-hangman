package com.hangman.ui.dialog;

import com.hangman.persistence.ScoreTracker;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Comparator;
import java.util.List;

/**
 * Displays basic leaderboard information.
 */
public class LeaderboardDialog extends JDialog {

    public LeaderboardDialog(java.awt.Window owner, ScoreTracker scoreTracker) {
        super(owner, "Leaderboard", ModalityType.MODELESS);
        setLayout(new GridLayout(2, 2, 10, 10));

        add(createPanel("Longest Streaks - Easy", scoreTracker, "easy"));
        add(createPanel("Longest Streaks - Medium", scoreTracker, "medium"));
        add(createPanel("Longest Streaks - Hard", scoreTracker, "hard"));
        add(createPanel("Most Wins", scoreTracker, null));

        pack();
        setLocationRelativeTo(owner);
        setVisible(true);
    }

    private JPanel createPanel(String title, ScoreTracker tracker, String difficulty) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(title, SwingConstants.CENTER);
        panel.add(label, BorderLayout.NORTH);

        DefaultTableModel model = new DefaultTableModel(
                new Object[]{ "Username", difficulty == null ? "Wins" : "Longest Streak" }, 0);
        JTable table = new JTable(model);

        List<String> users = tracker.getAllUsernames();
        Comparator<String> comparator = Comparator.comparingInt(user ->
                difficulty == null
                        ? tracker.getWins(user)
                        : tracker.getLongestStreak(user, difficulty));
        users.sort(comparator.reversed());

        for (String user : users) {
            int value = difficulty == null
                    ? tracker.getWins(user)
                    : tracker.getLongestStreak(user, difficulty);
            if (value > 0) {
                model.addRow(new Object[]{ user, value });
            }
        }

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }
}
