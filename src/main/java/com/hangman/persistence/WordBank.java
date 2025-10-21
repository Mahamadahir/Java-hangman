package com.hangman.persistence;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hangman.model.Word;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Provides word lookups filtered by difficulty.
 */
public class WordBank {
    private static final Type WORD_LIST_TYPE = new TypeToken<List<Word>>() { }.getType();

    private final List<Word> words;
    private final SecureRandom random = new SecureRandom();

    public WordBank(Path jsonPath) {
        Objects.requireNonNull(jsonPath);
        words = Collections.unmodifiableList(loadWords(jsonPath));
    }

    public Word pickRandomWord(String difficulty, List<String> exclusions) {
        List<Word> filtered = words.stream()
                .filter(word -> word.getDifficulty().equalsIgnoreCase(difficulty))
                .collect(Collectors.toList());

        if (filtered.isEmpty()) {
            throw new IllegalStateException("No words available for difficulty: " + difficulty);
        }

        List<Word> candidates = filtered.stream()
                .filter(word -> exclusions == null || !exclusions.contains(word.getValue()))
                .collect(Collectors.toList());

        List<Word> pool = candidates.isEmpty() ? filtered : candidates;
        return pool.get(random.nextInt(pool.size()));
    }

    private List<Word> loadWords(Path jsonPath) {
        try (BufferedReader reader = Files.newBufferedReader(jsonPath)) {
            return new Gson().fromJson(reader, WORD_LIST_TYPE);
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to load word list from " + jsonPath, ex);
        }
    }
}
