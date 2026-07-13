package com.hangman.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GameApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    private String startGame(String difficulty) throws Exception {
        MvcResult result = mvc.perform(post("/api/game")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"playerName\":\"alice\",\"difficulty\":\"" + difficulty + "\"}"))
                .andExpect(status().isOk())
                .andReturn();
        return mapper.readTree(result.getResponse().getContentAsString()).get("gameId").asText();
    }

    @Test
    void startReturnsAFreshInProgressRound() throws Exception {
        mvc.perform(post("/api/game")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"playerName\":\"alice\",\"difficulty\":\"easy\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameId").isNotEmpty())
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"));
    }

    @Test
    void guessReturnsUpdatedState() throws Exception {
        String gameId = startGame("easy");

        mvc.perform(post("/api/game/" + gameId + "/guess")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"letter\":\"a\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastGuess").isNotEmpty());
    }

    @Test
    void nextStartsAnotherRound() throws Exception {
        String gameId = startGame("easy");

        mvc.perform(post("/api/game/" + gameId + "/next"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("IN_PROGRESS"));
    }

    @Test
    void unknownGameIdReturns404() throws Exception {
        mvc.perform(post("/api/game/does-not-exist/guess")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"letter\":\"a\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void invalidDifficultyReturns400() throws Exception {
        mvc.perform(post("/api/game")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"playerName\":\"alice\",\"difficulty\":\"insane\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void nonLetterGuessReturns400() throws Exception {
        String gameId = startGame("easy");

        mvc.perform(post("/api/game/" + gameId + "/guess")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"letter\":\"1\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void inProgressResponseNeverLeaksTheTargetWord() throws Exception {
        MvcResult result = mvc.perform(post("/api/game")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"playerName\":\"alice\",\"difficulty\":\"easy\"}"))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode body = mapper.readTree(result.getResponse().getContentAsString());
        assertThat(body.get("status").asText()).isEqualTo("IN_PROGRESS");
        assertThat(body.get("word").isNull()).isTrue();
        assertThat(body.get("definition").isNull()).isTrue();
        assertThat(body.get("maskedWord").asText()).matches("_+");
    }
}
