package com.hangman.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hangman.model.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * Persists player statistics to a JSON file on disk.
 */
public class ScoreTracker {
    private static final Type SCORE_TYPE = new TypeToken<Map<String, ScoreData>>() { }.getType();

    private final Path storagePath;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private Map<String, ScoreData> userScores = new HashMap<>();

    public ScoreTracker(Path storagePath) {
        this.storagePath = Objects.requireNonNull(storagePath);
    }

    public void load() {
        if (!Files.exists(storagePath)) {
            userScores = new HashMap<>();
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(storagePath)) {
            Map<String, ScoreData> existing = gson.fromJson(reader, SCORE_TYPE);
            userScores = existing != null ? existing : new HashMap<>();
        } catch (IOException ex) {
            userScores = new HashMap<>();
        }
    }

    public void save() {
        try {
            Path parent = storagePath.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            try (BufferedWriter writer = Files.newBufferedWriter(storagePath)) {
                gson.toJson(userScores, writer);
            }
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to save score data", ex);
        }
    }

    public void initializeUser(String username) {
        userScores.computeIfAbsent(username, key -> new ScoreData());
    }

    public void recordWin(User user, String difficulty) {
        ScoreData data = userScores.computeIfAbsent(user.getUsername(), key -> new ScoreData());
        data.wins++;
        data.updateLongestStreak(difficulty, user.getCurrentStreak(difficulty));
    }

    public void recordLoss(User user, String difficulty, int streakBeforeReset) {
        ScoreData data = userScores.computeIfAbsent(user.getUsername(), key -> new ScoreData());
        data.losses++;
        data.updateLongestStreak(difficulty, streakBeforeReset);
    }

    public int getWins(String username) {
        return userScores.getOrDefault(username, new ScoreData()).wins;
    }

    public int getLosses(String username) {
        return userScores.getOrDefault(username, new ScoreData()).losses;
    }

    public int getLongestStreak(String username, String difficulty) {
        return userScores.getOrDefault(username, new ScoreData())
                .longestStreaks.getOrDefault(normalizeDifficulty(difficulty), 0);
    }

    public List<String> getAllUsernames() {
        return new ArrayList<>(userScores.keySet());
    }

    public Map<String, ScoreData> getAllScores() {
        return userScores;
    }

    public boolean deleteUser(String username) {
        if (username == null) {
            return false;
        }
        return userScores.remove(username) != null;
    }

    private static String normalizeDifficulty(String difficulty) {
        return Objects.requireNonNullElse(difficulty, "easy").toLowerCase(Locale.ENGLISH);
    }

    public static final class ScoreData {
        private int wins;
        private int losses;
        private final Map<String, Integer> longestStreaks = new HashMap<>();

        private ScoreData() {
            longestStreaks.put("easy", 0);
            longestStreaks.put("medium", 0);
            longestStreaks.put("hard", 0);
        }

        private void updateLongestStreak(String difficulty, int streak) {
            String normalized = normalizeDifficulty(difficulty);
            int current = longestStreaks.getOrDefault(normalized, 0);
            if (streak > current) {
                longestStreaks.put(normalized, streak);
            }
        }

        public int wins() {
            return wins;
        }

        public int losses() {
            return losses;
        }

        public Map<String, Integer> longestStreaks() {
            return new HashMap<>(longestStreaks);
        }
    }
}
