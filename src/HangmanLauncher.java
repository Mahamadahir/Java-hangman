import javax.swing.*;

public class HangmanLauncher {
    private static JFrame frame;
    private static final ScoreTracker scoreTracker = new ScoreTracker("assets/scores.json");

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            scoreTracker.load();  // Load scores (and users) first
            frame = new JFrame("Hangman Launcher");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);
            showUserScreen();
            frame.setVisible(true);
        });
    }

    private static void showUserScreen() {
        UserScreen screen = new UserScreen(scoreTracker, user -> showDifficultyScreen(user));
        frame.setContentPane(screen);
        frame.revalidate();
    }

    private static void showDifficultyScreen(User user) {
        DifficultyScreen screen = new DifficultyScreen(user, difficulty -> {
            HangmanGame.launchGame(user, difficulty);
            frame.dispose();
        });
        frame.setContentPane(screen);
        frame.revalidate();
    }
}
