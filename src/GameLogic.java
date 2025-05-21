import java.util.List;
import java.util.ArrayList;

public class GameLogic {
    private Word currentWord;
    private boolean[] guessedLetters;
    private int livesRemaining;
    private List<Character> wrongGuesses;

    public GameLogic(Word word) {
        this.currentWord = word;
        this.guessedLetters = new boolean[word.getWordLength()];
        this.livesRemaining = 6;
        this.wrongGuesses = new ArrayList<>();
    }

    public boolean guess(char letter) {
        // Mark guessed letters and reduce lives if incorrect
    }

    public String getDisplayWord() {
        // Return word with underscores for unguessed letters
    }

    public boolean isGameOver() {
        return livesRemaining == 0 || hasWon();
    }

    public boolean hasWon() {
        // Return true if all letters guessed
    }

    public int getLivesRemaining() { return livesRemaining; }

    public List<Character> getWrongGuesses() { return wrongGuesses; }
}
