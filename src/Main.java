import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        ScoreTracker scoreTracker = new ScoreTracker("assets/scores.json");
        new Leaderboard(scoreTracker);
    }
}
