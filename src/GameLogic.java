import java.util.List;
import java.util.ArrayList;

public class GameLogic {
    private Word currentWord;
    private char[] guessedLetters;
    private int livesRemaining;
    private final int totalLives = 6; // Adjustable
    private List<Character> wrongGuesses;

    public GameLogic(Word word) {
        this.currentWord = word;
        this.guessedLetters = new char[word.getWordLength()];
        this.livesRemaining = totalLives;
        this.wrongGuesses = new ArrayList<>();
    }

    public boolean guess(char letter) {
        letter = Character.toLowerCase(letter);  // 👈 Ensure consistent case
        boolean found = false;
        char[] actualChars = currentWord.toCharArray();

        for (int i = 0; i < actualChars.length; i++) {
            if (actualChars[i] == letter) {
                guessedLetters[i] = letter;
                found = true;
            }
        }

        if (!found && !wrongGuesses.contains(letter)) {
            livesRemaining--;
            wrongGuesses.add(letter);
        }

        return found;
    }

    public String getDisplayWord() {
        char[] display = new char[guessedLetters.length];
        for (int i = 0; i < guessedLetters.length; i++) {
            display[i] = (guessedLetters[i] == 0) ? '_' : guessedLetters[i];
        }
        return new String(display);
    }

    public boolean isGameOver() {
        return livesRemaining == 0 || hasWon();
    }

    public boolean hasWon() {
        return !getDisplayWord().contains("_");
    }

    public char[] getGuessedLetters() {
        return guessedLetters;
    }

    public Word getCurrentWord() {
        return currentWord;
    }

    public int getMistakesMade() {
        return totalLives - livesRemaining;
    }

    public int getLivesRemaining() {
        return livesRemaining;
    }

    public List<Character> getWrongGuesses() {
        return wrongGuesses;
    }
}
