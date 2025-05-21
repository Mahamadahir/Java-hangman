public class Word {
    private String word;
    private String difficulty;
    private int wordLength;

    public Word(String word, String difficulty, int wordLength) {
        this.word = word;
        this.difficulty = difficulty;
        this.wordLength = wordLength;
    }

    public String getWord() { return word; }
    public String getDifficulty() { return difficulty; }
    public int getWordLength() { return wordLength; }

    public void setWord(String word) { this.word = word; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
    public void setWordLength(int wordLength) { this.wordLength = wordLength; }

    public char[] toCharArray() {
        return word.toCharArray();
    }
}
