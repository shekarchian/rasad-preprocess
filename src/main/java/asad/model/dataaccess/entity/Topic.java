package asad.model.dataaccess.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "topic")
public class Topic implements Serializable {

    public enum Type {author, article}


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer topicCode;
    @Enumerated(EnumType.STRING)
    private Type type;
    private String wordList;

    @OneToMany(mappedBy = "topic", orphanRemoval=true)
    private Set<ArticleTopicDistribution> topicDistributions = new HashSet<>();

    public Topic() {
    }

    public Topic(Integer topicCode, Type type, String wordList) {
        this.topicCode = topicCode;
        this.type = type;
        this.wordList = wordList;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Integer getTopicCode() {
        return topicCode;
    }

    public void setTopicCode(Integer topicCode) {
        this.topicCode = topicCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWordList() {
        return wordList;
    }

    public void setWordList(String wordList) {
        this.wordList = wordList;
    }

    public Set<ArticleTopicDistribution> getTopicDistributions() {
        return topicDistributions;
    }

    public void setTopicDistributions(Set<ArticleTopicDistribution> topicDistributions) {
        this.topicDistributions = topicDistributions;
    }
}
