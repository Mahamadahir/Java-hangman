package com.hangman.web;

import com.hangman.persistence.WordBank;
import com.hangman.service.DictionaryProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

@Configuration
class GameBeans {

    @Bean
    WordBank wordBank() {
        try {
            return new WordBank(new InputStreamReader(
                    new ClassPathResource("words.json").getInputStream(), StandardCharsets.UTF_8));
        } catch (IOException ex) {
            throw new UncheckedIOException("Bundled words.json missing from classpath", ex);
        }
    }

    @Bean
    DictionaryProvider dictionaryProvider() {
        return new DictionaryProvider();
    }
}
