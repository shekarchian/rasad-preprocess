package asad.model.dataaccess.entity;

import javax.persistence.*;

@Entity
public class ArticlesPredictedLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer article1;

    private Integer article2;
    private Double weight;
    @Lob
    private String keywords1;
    @Lob
    private String keywords2;
    @Lob
    private String taxonomy1;

    @Lob
    private String taxonomy2;

    public ArticlesPredictedLink() {
    }

    public ArticlesPredictedLink(Integer article1, Integer article2, Double weight, String keywords1, String keywords2, String taxonomy1, String taxonomy2) {
        this.article1 = article1;
        this.article2 = article2;
        this.weight = weight;
        this.keywords1 = keywords1;
        this.keywords2 = keywords2;
        this.taxonomy1 = taxonomy1;
        this.taxonomy2 = taxonomy2;
    }

    public Integer getArticle1() {
        return article1;
    }

    public void setArticle1(Integer article1) {
        this.article1 = article1;
    }

    public Integer getArticle2() {
        return article2;
    }

    public void setArticle2(Integer article2) {
        this.article2 = article2;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getKeywords1() {
        return keywords1;
    }

    public void setKeywords1(String keywords1) {
        this.keywords1 = keywords1;
    }

    public String getKeywords2() {
        return keywords2;
    }

    public void setKeywords2(String keywords2) {
        this.keywords2 = keywords2;
    }

    public String getTaxonomy1() {
        return taxonomy1;
    }

    public void setTaxonomy1(String taxonomy1) {
        this.taxonomy1 = taxonomy1;
    }

    public String getTaxonomy2() {
        return taxonomy2;
    }

    public void setTaxonomy2(String taxonomy2) {
        this.taxonomy2 = taxonomy2;
    }
}
