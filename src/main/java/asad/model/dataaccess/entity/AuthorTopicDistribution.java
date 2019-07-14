package asad.model.dataaccess.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Reza Shekarchian
 */
@Entity
@Table(name = "author_topic_distribution")
public class AuthorTopicDistribution implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn
    private Author author;

    @ManyToOne
    @JoinColumn
    private Topic topic;

    private Double probability;


    public AuthorTopicDistribution() {
    }

    public AuthorTopicDistribution(Author author, Topic topic, Double probability) {
        this.author = author;
        this.topic = topic;
        this.probability = probability;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Double getProbability() {
        return probability;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }
}
