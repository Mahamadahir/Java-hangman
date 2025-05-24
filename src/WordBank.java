import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class WordBank {
    private List<Word> words;
    Random random = new Random();

    public WordBank(String filePath) {
        loadWordsFromJson(filePath);
    }

    private void loadWordsFromJson(String path) {
        // Parse JSON into List<Word>
        ObjectMapper mapper = new ObjectMapper();

        try {
            words = mapper.readValue(
                    new File(path),
                    new TypeReference<List<Word>>() {}
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public Word getRandomWord() {
        // Return a random word from the list
        return words.get(random.nextInt(words.size()));

    }

    public Word getRandomWordByDifficulty(String difficulty) {
        // Return a filtered random word by difficulty
        List<Word> filteredList = words;
        for (Word word : words) {
            if ((word.getDifficulty()).equals(difficulty)) {
                filteredList.add(word);
            }
        }
        if(filteredList.isEmpty()){
            return new Word("Emtpy","Easy", 5);
        }
        return filteredList.get(random.nextInt(filteredList.size()));
    }

    public List<Word> getAllWords() {
        return words;
    }
}
