public class ScoreTracker {
    private int wins;
    private int losses;

    public void loadScore(String path) {
        // Load score from file
    }

    public void saveScore(String path) {
        // Save score to file
    }

    public void recordWin() { wins++; }
    public void recordLoss() { losses++; }

    public int getWins() { return wins; }
    public int getLosses() { return losses; }
}
