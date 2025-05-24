import java.io.File;
import java.util.List;
import java.util.Random;

public class WordBank {
    private List<Word> words;

    public WordBank(String filePath) {
        loadWordsFromJson(filePath);
    }

    private void loadWordsFromJson(String path) {
        // Parse JSON into List<Word>
        ObjectMapper mapper = new ObjectMapper();

        words = mapper.readValue(
                new File(path),
                new TypeReference<List<Word>>() {}
        );


    }

    public Word getRandomWord() {
        // Return a random word from the list
        Random random = new Random();
        return words.get(random.nextInt(words.size()));

    }

    public Word getRandomWordByDifficulty(String difficulty) {
        // Return a filtered random word by difficulty
        List<Word> filteredList = words;
        for(int i=0; i<words.size(); i++){
            if(((words.get(i)).getDifficulty()).equals(difficulty)){
                filteredList.add(words.get(i));
            }
        }

    }

    public List<Word> getAllWords() {
        return words;
    }
}
