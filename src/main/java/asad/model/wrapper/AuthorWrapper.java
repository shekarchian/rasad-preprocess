package asad.model.wrapper;

import asad.model.dataaccess.entity.Author;

public class AuthorWrapper {

    private Integer id;
    private String name;

    public AuthorWrapper(Author author) {
        this.id = author.getId();
        this.name = author.getName();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
