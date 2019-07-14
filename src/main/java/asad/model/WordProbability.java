package asad.model;

public class WordProbability {
    private String word;
    private Double probability;

    public WordProbability(String word, Double probability) {
        this.word = word;
        this.probability = probability;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Double getProbability() {
        return probability;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }
}
