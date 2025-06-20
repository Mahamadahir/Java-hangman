import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ScoreTracker {
    private Map<String, int[]> userScores;
    private String path;

    public ScoreTracker(String path) {
        this.path = path;
        userScores = new HashMap<>();
    }

    public void load() {
        try (FileReader reader = new FileReader(path)) {
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, int[]>>() {}.getType();
            userScores = gson.fromJson(reader, type);
            if (userScores == null) userScores = new HashMap<>();
        } catch (IOException e) {
            userScores = new HashMap<>(); // start fresh if file not found
        }
    }

    public void save() {
        try (FileWriter writer = new FileWriter(path)) {
            Gson gson = new Gson();
            gson.toJson(userScores, writer);
        } catch (IOException e) {
            System.err.println("Failed to save scores: " + e.getMessage());
        }
    }

    public void recordWin(String username) {
        int[] stats = userScores.getOrDefault(username, new int[]{0, 0});
        stats[0]++;
        userScores.put(username, stats);
        save(); // Auto-save after win
    }

    public void recordLoss(String username) {
        int[] stats = userScores.getOrDefault(username, new int[]{0, 0});
        stats[1]++;
        userScores.put(username, stats);
        save(); // Auto-save after loss
    }

    public int getWins(String username) {
        return userScores.getOrDefault(username, new int[]{0, 0})[0];
    }

    public int getLosses(String username) {
        return userScores.getOrDefault(username, new int[]{0, 0})[1];
    }

    public Map<String, int[]> getAllScores() {
        return userScores;
    }
}
