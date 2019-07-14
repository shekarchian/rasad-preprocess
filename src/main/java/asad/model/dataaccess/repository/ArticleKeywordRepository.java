package asad.model.dataaccess.repository;

import asad.model.dataaccess.entity.Article;
import asad.model.dataaccess.entity.ArticleKeyword;
import asad.model.dataaccess.entity.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface ArticleKeywordRepository extends CrudRepository<ArticleKeyword, Integer> {
    Set<ArticleKeyword> findByArticle_Id(Integer id);

    @Query("select articleKeyword from ArticleKeyword articleKeyword " +
            "inner join fetch articleKeyword.article article " +
            "inner join fetch article.authors author " +
            "where author.id = :id ")
    Set<ArticleKeyword> findAuthorKeywords(@Param("id") Integer id);


}