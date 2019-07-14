package asad.model.dataaccess.repository;

import asad.model.dataaccess.entity.AuthorsPredictedLink;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthorPredictedLinkRepository extends PagingAndSortingRepository<AuthorsPredictedLink, Integer> {

    @Query("select link from AuthorsPredictedLink link " +
            "where link.author1 = :id or link.author2 = :id ")
    List<AuthorsPredictedLink> findByAuthorId(@Param("id") Integer id);


    @Query("select link from AuthorsPredictedLink link " +
            "where link.keywords1 != \'\' and link.keywords2 != \'\' ")
    Page<AuthorsPredictedLink> findAllKeywordLinks(Pageable pageable);

    @Query("select link from AuthorsPredictedLink link " +
            "where link.taxonomy1 != \'\' and link.taxonomy2 != \'\' ")
    Page<AuthorsPredictedLink> findAllTaxonomyLinks(Pageable pageable);
}
