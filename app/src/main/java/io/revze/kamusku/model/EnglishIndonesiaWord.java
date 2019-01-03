package io.revze.kamusku.model;

public class EnglishIndonesiaWord {
    private int id;
    private String word;
    private String description;

    public EnglishIndonesiaWord() {}

    public EnglishIndonesiaWord(String word, String description) {
        setWord(word);
        setDescription(description);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
