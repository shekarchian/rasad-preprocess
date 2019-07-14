package asad.model.dataaccess.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Reza Shekarchian
 */
@Entity
@Table(name = "article_topic_distribution")
public class ArticleTopicDistribution implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn
    private Article article;

    @ManyToOne
    @JoinColumn
    private Topic topic;

    private Double probability;


    public ArticleTopicDistribution() {
    }

    public ArticleTopicDistribution(Article article, Topic topic, Double probability) {
        this.article = article;
        this.topic = topic;
        this.probability = probability;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
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
