package asad.model.dataaccess.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "taxonomy_class")
public class Taxonomy {
    @Id
    private Integer id;
    private Integer code;
    private Integer parent_taxonomy_class_id;
    private String title;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "taxonomies")
    private Set<Article> articles;

    public Taxonomy() {
    }

    public Taxonomy(Integer id, Integer code, Integer parent_taxonomy_class_id, String title) {
        this.id = id;
        this.code = code;
        this.parent_taxonomy_class_id = parent_taxonomy_class_id;
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public Integer getCode() {
        return code;
    }

    public Integer getParent_taxonomy_class_id() {
        return parent_taxonomy_class_id;
    }

    public String getTitle() {
        return title;
    }
}
