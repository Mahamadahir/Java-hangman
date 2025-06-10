import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ScoreTracker {
    private int wins;
    private int losses;
    private String username; // add this to identify the user

    public ScoreTracker(String username) {
        this.username = username;
    }

    public void loadScore(String path, String username) {
        this.username = username;

        try (FileReader reader = new FileReader(path)) {
            Gson gson = new Gson();
            Type mapType = new TypeToken<Map<String, ScoreTracker>>() {}.getType();
            Map<String, ScoreTracker> userMap = gson.fromJson(reader, mapType);

            if (userMap != null && userMap.containsKey(username)) {
                ScoreTracker stored = userMap.get(username);
                this.wins = stored.getWins();
                this.losses = stored.getLosses();
            } else {
                this.wins = 0;
                this.losses = 0;
            }

        } catch (IOException e) {
            System.out.println("Error loading score file: " + e.getMessage());
            this.wins = 0;
            this.losses = 0;
        }
    }

    public void saveScore(String path) {
        Gson gson = new Gson();
        Type mapType = new TypeToken<Map<String, ScoreTracker>>() {}.getType();
        Map<String, ScoreTracker> userMap;

        // Try to read existing scores
        try (FileReader reader = new FileReader(path)) {
            userMap = gson.fromJson(reader, mapType);
            if (userMap == null) userMap = new HashMap<>();
        } catch (IOException e) {
            userMap = new HashMap<>();
        }

        // Update current user score
        userMap.put(this.username, this);

        // Write back to file
        try (FileWriter writer = new FileWriter(path)) {
            gson.toJson(userMap, writer);
        } catch (IOException e) {
            System.out.println("Error saving score file: " + e.getMessage());
        }
    }

    public void recordWin() {
        wins++;
    }

    public void recordLoss() {
        losses++;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }
}