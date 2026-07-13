package com.hangman.persistence;

import com.hangman.model.Word;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WordBankTest {

    private static final String WORDS = """
            [
              {"word": "cat", "difficulty": "easy", "wordLength": 3},
              {"word": "dog", "difficulty": "easy", "wordLength": 3},
              {"word": "planet", "difficulty": "medium", "wordLength": 6},
              {"word": "xylophone", "difficulty": "hard", "wordLength": 9}
            ]
            """;

    private static WordBank bank() {
        return new WordBank(new StringReader(WORDS));
    }

    @Test
    void picksOnlyWordsMatchingDifficulty() {
        WordBank bank = bank();

        for (int i = 0; i < 50; i++) {
            Word word = bank.pickRandomWord("easy", List.of());
            assertThat(word.getDifficulty()).isEqualTo("easy");
            assertThat(word.getValue()).isIn("cat", "dog");
        }
    }

    @Test
    void excludesSeenWords() {
        WordBank bank = bank();

        for (int i = 0; i < 50; i++) {
            Word word = bank.pickRandomWord("easy", List.of("cat"));
            assertThat(word.getValue()).isEqualTo("dog");
        }
    }

    @Test
    void fallsBackToTheFullPoolWhenEveryWordIsExcluded() {
        WordBank bank = bank();

        Word word = bank.pickRandomWord("easy", List.of("cat", "dog"));

        assertThat(word.getValue()).isIn("cat", "dog");
    }

    @Test
    void throwsWhenDifficultyHasNoWords() {
        WordBank bank = bank();

        assertThatThrownBy(() -> bank.pickRandomWord("impossible", List.of()))
                .isInstanceOf(IllegalStateException.class);
    }
}
