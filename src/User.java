public class User {
    private String username;
    private String difficulty = "easy"; // default
    private int streak = 0;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public int getStreak() {
        return streak;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public void incrementStreak() {
        streak++;
    }

    public void resetStreak() {
        streak = 0;
    }
}
