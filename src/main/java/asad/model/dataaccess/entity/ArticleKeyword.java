package asad.model.dataaccess.entity;

import org.hibernate.annotations.*;
import org.hibernate.sql.Select;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
public class ArticleKeyword {
    @Id
    private Integer id;
    private String keyword;

    @ManyToOne(fetch= FetchType.LAZY)
//    @JoinColumn(name="article_id")
    private Article article;

    public ArticleKeyword() {
    }

    public ArticleKeyword(Integer id, String keyword, Article article) {
        this.id = id;
        this.keyword = keyword;
//        this.article = article;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

//    public Article getArticle() {
//        return article;
//    }
//
//    public void setArticle(Article article) {
//        this.article = article;
//    }
}
