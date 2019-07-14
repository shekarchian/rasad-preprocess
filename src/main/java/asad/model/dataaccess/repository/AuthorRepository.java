package asad.model.dataaccess.repository;

import asad.model.dataaccess.entity.Author;
import asad.model.dataaccess.entity.Taxonomy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;


public interface AuthorRepository extends CrudRepository<Author, Integer> {
    Optional<Author> findById(Integer id);

    @Query("select author from Author author " +
            "inner join fetch author.articles article1 " +
            "inner join fetch article1.authors author2 " +
            "where author.id = :id ")
    Author findAuthorArticlesAuthors(@Param("id") Integer id);

    @Query("select taxonomy from Author author " +
            "inner join author.articles article " +
            "inner join article.taxonomies taxonomy " +
            "where author.id= :id")
    Set<Taxonomy> findAuthorTaxonomies(@Param("id") Integer id);

    @Query("select author from Author author " +
            "inner join fetch author.articles article ")
    Set<Author> findAllAuthorsArticles();

    @Query("select author from Author author " +
            "inner join fetch author.articles article1 " +
            "where author.id = :id ")
    Author findAuthorArticles(@Param("id") Integer id);

}