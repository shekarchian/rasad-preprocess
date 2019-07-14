package asad.model.dataaccess.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


@Entity
public class DenormalizedLemmatizedArticleText {
    @Id
    private Integer articleId;
    @Lob
    private String title;
    @Column(name = "abstract")
    @Lob
    private String abstractColumn;
    @Lob
    private String taxonomies;
    @Lob
    private String keyword;

    public DenormalizedLemmatizedArticleText() {
    }


    public DenormalizedLemmatizedArticleText(Integer articleId, String title, String abstractColumn, String taxonomies, String keyword) {
        this.articleId = articleId;
        this.title = title;
        this.abstractColumn = abstractColumn;
        this.taxonomies = taxonomies;
        this.keyword = keyword;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstractColumn() {
        return abstractColumn;
    }

    public void setAbstractColumn(String abstractColumn) {
        this.abstractColumn = abstractColumn;
    }

    public String getTaxonomies() {
        return taxonomies;
    }

    public void setTaxonomies(String taxonomies) {
        this.taxonomies = taxonomies;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
