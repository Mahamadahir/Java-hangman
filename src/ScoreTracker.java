import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class ScoreTracker {
    public static class ScoreData {
        int wins = 0;
        int losses = 0;
        Map<String, Integer> longestStreaks = new HashMap<>(); // difficulty -> streak

        public ScoreData() {
            longestStreaks.put("easy", 0);
            longestStreaks.put("medium", 0);
            longestStreaks.put("hard", 0);
        }
    }

    private Map<String, ScoreData> userScores;
    private final String path;

    public ScoreTracker(String path) {
        this.path = path;
        this.userScores = new HashMap<>();
    }

    public void load() {
        try (FileReader reader = new FileReader(path)) {
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, ScoreData>>() {}.getType();
            userScores = gson.fromJson(reader, type);
            if (userScores == null) userScores = new HashMap<>();
        } catch (IOException e) {
            userScores = new HashMap<>();
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

    public void initializeUser(String username) {
        if (!userScores.containsKey(username)) {
            userScores.put(username, new ScoreData());
        }
    }

    public void recordWin(String username) {
        initializeUser(username);
        userScores.get(username).wins++;
    }

    public void recordLoss(String username) {
        initializeUser(username);
        userScores.get(username).losses++;
    }

    public void updateLongestStreak(String username, String difficulty, int streak) {
        initializeUser(username);
        ScoreData data = userScores.get(username);
        int currentLongest = data.longestStreaks.getOrDefault(difficulty, 0);
        if (streak > currentLongest) {
            data.longestStreaks.put(difficulty, streak);
        }
    }

    public int getWins(String username) {
        return userScores.getOrDefault(username, new ScoreData()).wins;
    }

    public int getLosses(String username) {
        return userScores.getOrDefault(username, new ScoreData()).losses;
    }

    public int getLongestStreak(String username, String difficulty) {
        ScoreData data = userScores.getOrDefault(username, new ScoreData());
        return data.longestStreaks.getOrDefault(difficulty, 0);
    }

    public Set<String> getAllUsers() {
        return userScores.keySet();
    }

    public List<String> getAllUsernames() {
        return new ArrayList<>(userScores.keySet());
    }

    public Map<String, ScoreData> getAllScores() {
        return userScores;
    }
}
