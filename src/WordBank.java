import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordBank {
    private List<Word> words;
    private Random random = new Random();

    public WordBank(String filePath) {
        loadWordsFromJson(filePath);
    }

    private void loadWordsFromJson(String path) {
        try (FileReader reader = new FileReader(path)) {
            Gson gson = new Gson();
            Type wordListType = new TypeToken<List<Word>>() {}.getType();
            words = gson.fromJson(reader, wordListType);
        } catch (IOException e) {
            e.printStackTrace();
            words = new ArrayList<>(); // fallback to empty list
        }
    }

    public Word getRandomWord() {
        if (words.isEmpty()) {
            return new Word("empty", "easy", 5);
        }
        return words.get(random.nextInt(words.size()));
    }

    public Word getRandomWordByDifficulty(String difficulty) {
        List<Word> filteredList = new ArrayList<>();
        for (Word word : words) {
            if (word.getDifficulty().equalsIgnoreCase(difficulty)) {
                filteredList.add(word);
            }
        }

        if (filteredList.isEmpty()) {
            return new Word("empty", difficulty, 5);
        }

        return filteredList.get(random.nextInt(filteredList.size()));
    }

    public List<Word> getAllWords() {
        return words;
    }
}
