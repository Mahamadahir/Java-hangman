import java.util.List;

public class WordBank {
    private List<Word> words;

    public WordBank(String filePath) {
        loadWordsFromJson(filePath);
    }

    private void loadWordsFromJson(String path) {
        // Parse JSON into List<Word>

    }

    public Word getRandomWord() {
        // Return a random word from the list
    }

    public Word getRandomWordByDifficulty(String difficulty) {
        // Return a filtered random word by difficulty
    }

    public List<Word> getAllWords() {
        return words;
    }
}
