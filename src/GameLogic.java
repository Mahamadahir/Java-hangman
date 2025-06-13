import java.util.List;
import java.util.ArrayList;

public class GameLogic {
    private Word currentWord;
    private char[] guessedLetters;
    private int livesRemaining;
    private List<Character> wrongGuesses;

    public GameLogic(Word word) {
        this.currentWord = word;
        this.guessedLetters = new char[word.getWordLength()];
        this.livesRemaining = 10;
        this.wrongGuesses = new ArrayList<>();
    }

    public boolean guess(char letter) {
        boolean found = false;
        char[] actualChars = currentWord.toCharArray();

        for (int i = 0; i < actualChars.length; i++) {
            if (actualChars[i] == letter) {
                guessedLetters[i] = letter;
                found = true;
            }
        }

        if (!found) {
            livesRemaining--;
            wrongGuesses.add(letter);
        }

        return found;
    }


    public String getDisplayWord() {
        char[] display = new char[currentWord.getWordLength()];
        char[] actualChars = currentWord.toCharArray();

        for (int i = 0; i < actualChars.length; i++) {
            char current = actualChars[i];
            boolean guessed = false;

            for (char g : guessedLetters) {
                if (current == g) {
                    guessed = true;
                    break;
                }
            }

            display[i] = guessed ? current : '_';
        }

        return new String(display);
    }

    public boolean isGameOver() {
        return livesRemaining == 0 || hasWon();
    }

    public boolean hasWon() {
        return !getDisplayWord().contains("_");
    }


    public int getLivesRemaining() { return livesRemaining; }

    public List<Character> getWrongGuesses() { return wrongGuesses; }
}
