package com.hangman.service;

import com.hangman.service.DictionaryProvider.HttpReply;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

class DictionaryProviderTest {

    private static final String NOT_FOUND = "No definition found for this word.";
    private static final String UNAVAILABLE = "Definition unavailable right now (are you offline?).";

    private static final String CAT_JSON = """
            [{"word":"cat","meanings":[
              {"partOfSpeech":"noun","definitions":[
                {"definition":"A small domesticated feline."}]}]}]
            """;

    @Test
    void parsesDefinitionFromASuccessfulResponse() {
        DictionaryProvider dictionary = new DictionaryProvider(word -> new HttpReply(200, CAT_JSON));

        assertThat(dictionary.define("cat")).isEqualTo("(noun) A small domesticated feline.");
    }

    @Test
    void returnsNotFoundOn404() {
        DictionaryProvider dictionary = new DictionaryProvider(word -> new HttpReply(404, ""));

        assertThat(dictionary.define("qwerty")).isEqualTo(NOT_FOUND);
    }

    @Test
    void returnsUnavailableWhenTheRequestFails() {
        DictionaryProvider dictionary = new DictionaryProvider(word -> {
            throw new IOException("network down");
        });

        assertThat(dictionary.define("cat")).isEqualTo(UNAVAILABLE);
    }

    @Test
    void cachesSuccessfulLookupsAndAvoidsASecondHttpCall() {
        AtomicInteger calls = new AtomicInteger();
        DictionaryProvider dictionary = new DictionaryProvider(word -> {
            calls.incrementAndGet();
            return new HttpReply(200, CAT_JSON);
        });

        String first = dictionary.define("cat");
        String second = dictionary.define("cat");

        assertThat(second).isEqualTo(first);
        assertThat(dictionary.isCached("cat")).isTrue();
        assertThat(calls).hasValue(1);
    }
}
