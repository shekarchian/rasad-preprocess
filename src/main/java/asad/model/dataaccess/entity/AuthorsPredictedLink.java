package asad.model.dataaccess.entity;

import javax.persistence.*;

@Entity
public class AuthorsPredictedLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer author1;

    private Integer author2;
    private Double weight;

    @Lob
    private String keywords1;
    @Lob
    private String keywords2;
    @Lob
    private String taxonomy1;

    @Lob
    private String taxonomy2;

    public AuthorsPredictedLink() {
    }

    public AuthorsPredictedLink(Integer author1, Integer author2, Double weight, String keywords1, String keywords2, String taxonomy1, String taxonomy2) {
        this.author1 = author1;
        this.author2 = author2;
        this.weight = weight;
        this.keywords1 = keywords1;
        this.keywords2 = keywords2;
        this.taxonomy1 = taxonomy1;
        this.taxonomy2 = taxonomy2;
    }

    public Integer getAuthor1() {
        return author1;
    }

    public void setAuthor1(Integer author1) {
        this.author1 = author1;
    }

    public Integer getAuthor2() {
        return author2;
    }

    public void setAuthor2(Integer author2) {
        this.author2 = author2;
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
