package asad.model.wrapper;

import asad.model.dataaccess.entity.Article;
import asad.model.dataaccess.entity.Author;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ArticleWrapper {

    private Integer id;
    private String title;
    private String journal;
    private String publisher;
    private Date date;
    private String volume;
    private String abstractColumn;
    private Set<AuthorWrapper> authors = new HashSet<>();

    public ArticleWrapper() {
    }

    public ArticleWrapper(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.journal = article.getJournal();
        this.publisher = article.getPublisher();
        this.date = article.getDate();
        this.volume = article.getVolume();
        this.abstractColumn = article.getAbstractColumn();
        for (Author author: article.getAuthors()){
            this.authors.add(new AuthorWrapper(author));
        }
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getJournal() {
        return journal;
    }

    public String getPublisher() {
        return publisher;
    }

    public Date getDate() {
        return date;
    }

    public String getVolume() {
        return volume;
    }

    public String getAbstractColumn() {
        return abstractColumn;
    }

    public Set<AuthorWrapper> getAuthors() {
        return authors;
    }
}
