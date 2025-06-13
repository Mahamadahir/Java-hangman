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
        // Mark guessed letters and reduce lives if incorrect

        for(int i=0;i<currentWord.getWordLength();i++){
            if(letter==currentWord.toCharArray()[i]){
                guessedLetters[i]=letter;
                return true;
            }
        }
        livesRemaining--;
        wrongGuesses.add(letter);
        return false;
    }

    public String getDisplayWord() {
        // Return word with underscores for unguessed
        char [] letters = new char['_'* currentWord.getWordLength()];
        for(int i=0;i< currentWord.getWordLength();i++){
            for(int j =0; i< currentWord.getWordLength(); j++){
                if(currentWord.toCharArray()[i]==guessedLetters[j]){
                    letters[i]=currentWord.toCharArray()[i];
                }
            }
        }
        return new String(letters);
    }

    public boolean isGameOver() {
        return livesRemaining == 0 || hasWon();
    }

    public boolean hasWon() {
        // Return true if all letters guessed
        if(currentWord.toCharArray()==guessedLetters){
            return true;
        }
        return false;
    }

    public int getLivesRemaining() { return livesRemaining; }

    public List<Character> getWrongGuesses() { return wrongGuesses; }
}
