package asad.model.dataaccess.repository;

import asad.model.dataaccess.entity.ArticlesPredictedLink;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticlePredictedLinkRepository extends PagingAndSortingRepository<ArticlesPredictedLink, Integer> {

    @Query("select link from ArticlesPredictedLink link " +
            "where link.article1 = :id or link.article2 = :id ")
    List<ArticlesPredictedLink> findByArticleId(@Param("id") Integer id);

    @Query("select link from ArticlesPredictedLink link " +
            "where link.keywords1 != \'\' and link.keywords2 != \'\' ")
    Page<ArticlesPredictedLink> findAllKeywordLinks(Pageable pageable);

    @Query("select link from ArticlesPredictedLink link " +
            "where link.taxonomy1 != \'\' and link.taxonomy2 != \'\' ")
    Page<ArticlesPredictedLink> findAllTaxonomyLinks(Pageable pageable);

}
