package asad.model.dataaccess.repository;

import asad.model.dataaccess.entity.Article;
import asad.model.dataaccess.entity.Author;
import asad.model.dataaccess.entity.Taxonomy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;


public interface ArticleRepository extends CrudRepository<Article, Integer> {

    Optional<Article> findById(Integer id);

    @Query("select a from Article a join fetch a.authors where a.id = :id")
    Article findArticleCompleteInfo(@Param("id") Integer id);

    @Query("select a.taxonomies from Article a inner join a.taxonomies where a.id = :id")
    Set<Taxonomy> findArticleTaxonomies(@Param("id") Integer id);

    @Query("select article from Article article " +
            "left join fetch article.keyword keywords ")
    Set<Article> findAllArticlesWithKeyword();//todo change


    @Query("select article from Article article " +
            "left join fetch article.taxonomies ")
    Set<Article> findAllArticlesWithTaxonomy();

    @Query("select article1 from Article article1 " +
            "inner join fetch article1.authors author " +
            "inner join fetch  author.articles article2 " +
            "where article1.id = :id ")
    Article findArticleAuthors(@Param("id") Integer id);

    @Query("select article from Article article " +
            "inner join fetch article.authors")
    Set<Article> findAllArticlesAuthors();
}