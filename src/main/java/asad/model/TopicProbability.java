package asad.model;

import java.util.List;

public class TopicProbability {
    private Integer id;
    private Double probability;
    private List<String> words;

    public TopicProbability(Integer id, Double probability, List<String> words) {
        this.id = id;
        this.probability = probability;
        this.words = words;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getProbability() {
        return probability;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }
}
