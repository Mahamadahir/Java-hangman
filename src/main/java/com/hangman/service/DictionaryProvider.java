package com.hangman.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Looks up word definitions from the free dictionaryapi.dev service and caches
 * each result so a given word is only fetched once per session.
 */
public class DictionaryProvider {
    private static final String API_BASE = "https://api.dictionaryapi.dev/api/v2/entries/en/";
    private static final String NOT_FOUND = "No definition found for this word.";
    private static final String UNAVAILABLE = "Definition unavailable right now (are you offline?).";
    private static final Duration TIMEOUT = Duration.ofSeconds(5);

    private final Map<String, String> cache = new ConcurrentHashMap<>();
    private final HttpExchange exchange;

    public DictionaryProvider() {
        this(defaultExchange());
    }

    DictionaryProvider(HttpExchange exchange) {
        this.exchange = exchange;
    }

    private static HttpExchange defaultExchange() {
        HttpClient client = HttpClient.newBuilder().connectTimeout(TIMEOUT).build();
        return word -> {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_BASE + URLEncoder.encode(word, StandardCharsets.UTF_8)))
                    .timeout(TIMEOUT)
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return new HttpReply(response.statusCode(), response.body());
        };
    }

    /**
     * Seam over the outbound dictionary call so tests can supply a fake reply
     * without a live HTTP request. Production wires the real {@link HttpClient}.
     */
    @FunctionalInterface
    interface HttpExchange {
        HttpReply fetch(String word) throws IOException, InterruptedException;
    }

    record HttpReply(int statusCode, String body) {
    }

    /**
     * Returns a human-readable definition for the word. Successful and
     * "not found" results are cached; transient network failures are not, so a
     * later round can retry.
     */
    public String define(String word) {
        String key = normalize(word);
        if (key.isEmpty()) {
            return NOT_FOUND;
        }
        String cached = cache.get(key);
        if (cached != null) {
            return cached;
        }
        String result = lookup(key);
        if (result != null) {
            cache.put(key, result);
            return result;
        }
        return UNAVAILABLE;
    }

    /** Exposes whether a word has already been fetched (used for tests/diagnostics). */
    public boolean isCached(String word) {
        return cache.containsKey(normalize(word));
    }

    private String lookup(String word) {
        try {
            HttpReply reply = exchange.fetch(word);
            int status = reply.statusCode();
            if (status == 200) {
                String parsed = parseDefinition(reply.body());
                return parsed != null ? parsed : NOT_FOUND;
            }
            if (status == 404) {
                return NOT_FOUND;
            }
            return null;
        } catch (IOException ex) {
            return null;
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            return null;
        }
    }

    private String parseDefinition(String body) {
        try {
            JsonArray entries = JsonParser.parseString(body).getAsJsonArray();
            for (int i = 0; i < entries.size(); i++) {
                JsonObject entry = entries.get(i).getAsJsonObject();
                JsonArray meanings = entry.getAsJsonArray("meanings");
                if (meanings == null) {
                    continue;
                }
                for (int m = 0; m < meanings.size(); m++) {
                    JsonObject meaning = meanings.get(m).getAsJsonObject();
                    JsonArray definitions = meaning.getAsJsonArray("definitions");
                    if (definitions == null || definitions.isEmpty()) {
                        continue;
                    }
                    String definition = definitions.get(0).getAsJsonObject()
                            .get("definition").getAsString();
                    String partOfSpeech = meaning.has("partOfSpeech")
                            ? meaning.get("partOfSpeech").getAsString()
                            : null;
                    return partOfSpeech != null
                            ? "(" + partOfSpeech + ") " + definition
                            : definition;
                }
            }
            return null;
        } catch (RuntimeException ex) {
            return null;
        }
    }

    private static String normalize(String word) {
        return word == null ? "" : word.trim().toLowerCase(Locale.ENGLISH);
    }
}
