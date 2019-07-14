package asad.model.dataaccess.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Author {
    @Id
    private Integer id;

    private String name;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "authors")

    private Set<Article> articles = new HashSet<>();

    @OneToMany(mappedBy = "author")
    private Set<AuthorTopicDistribution> topicDistributions = new HashSet<>();

    public Author() {
    }

    public Author(Integer id, String name, Set<Article> articles) {
        this.id = id;
        this.name = name;
        this.articles = articles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }
}
