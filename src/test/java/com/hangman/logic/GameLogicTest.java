package com.hangman.logic;

import com.hangman.logic.GameLogic.GuessResult;
import com.hangman.model.Word;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GameLogicTest {

    private static GameLogic roundFor(String word) {
        return new GameLogic(new Word(word, "easy", word.length()));
    }

    @Test
    void guessingEveryLetterWinsTheRound() {
        GameLogic round = roundFor("cat");

        assertThat(round.submitGuess('c')).isEqualTo(GuessResult.CORRECT);
        assertThat(round.submitGuess('a')).isEqualTo(GuessResult.CORRECT);
        assertThat(round.submitGuess('t')).isEqualTo(GuessResult.CORRECT);

        assertThat(round.hasWon()).isTrue();
        assertThat(round.isGameOver()).isTrue();
    }

    @Test
    void sixMistakesEndsTheRoundAsALoss() {
        GameLogic round = roundFor("cat");

        for (char letter : "bdefgh".toCharArray()) {
            assertThat(round.submitGuess(letter)).isEqualTo(GuessResult.INCORRECT);
        }

        assertThat(round.getMistakesMade()).isEqualTo(6);
        assertThat(round.getRemainingLives()).isZero();
        assertThat(round.isGameOver()).isTrue();
        assertThat(round.hasWon()).isFalse();
    }

    @Test
    void fiveMistakesIsNotYetGameOver() {
        GameLogic round = roundFor("cat");

        for (char letter : "bdefg".toCharArray()) {
            round.submitGuess(letter);
        }

        assertThat(round.getMistakesMade()).isEqualTo(5);
        assertThat(round.isGameOver()).isFalse();
    }

    @Test
    void duplicateGuessDoesNotIncrementMistakes() {
        GameLogic round = roundFor("cat");

        assertThat(round.submitGuess('z')).isEqualTo(GuessResult.INCORRECT);
        assertThat(round.submitGuess('z')).isEqualTo(GuessResult.ALREADY_GUESSED);

        assertThat(round.getMistakesMade()).isEqualTo(1);
    }

    @Test
    void nonLetterGuessIsRejectedWithoutCost() {
        GameLogic round = roundFor("cat");

        assertThat(round.submitGuess('1')).isEqualTo(GuessResult.INVALID);
        assertThat(round.submitGuess('!')).isEqualTo(GuessResult.INVALID);

        assertThat(round.getMistakesMade()).isZero();
    }

    @Test
    void guessingAfterGameOverReturnsGameOver() {
        GameLogic round = roundFor("cat");
        for (char letter : "bdefgh".toCharArray()) {
            round.submitGuess(letter);
        }

        assertThat(round.isGameOver()).isTrue();
        assertThat(round.submitGuess('c')).isEqualTo(GuessResult.GAME_OVER);
    }

    @Test
    void repeatedLetterRevealsEveryPosition() {
        GameLogic round = roundFor("book");

        assertThat(round.submitGuess('o')).isEqualTo(GuessResult.CORRECT);

        char[] revealed = round.getGuessedLetters();
        assertThat(revealed[1]).isEqualTo('o');
        assertThat(revealed[2]).isEqualTo('o');
        assertThat(revealed[0]).isEqualTo((char) 0);
        assertThat(revealed[3]).isEqualTo((char) 0);
    }
}
