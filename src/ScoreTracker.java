import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ScoreTracker {
    private int wins;
    private int losses;

    public void loadScore(String path) {
        try {
            if (Files.exists(Path.of(path))) {
                String[] parts = Files.readString(Path.of(path)).split(",");
                wins = Integer.parseInt(parts[0]);
                losses = Integer.parseInt(parts[1]);
            }
        } catch (IOException | NumberFormatException e) {
            wins = 0;
            losses = 0;
        }
    }

    public void saveScore(String path) {
        String data = wins + "," + losses;
        try {
            Files.writeString(Path.of(path), data, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            // handle error
        }
    }

    public void recordWin() { wins++; }
    public void recordLoss() { losses++; }

    public int getWins() { return wins; }
    public int getLosses() { return losses; }
}
