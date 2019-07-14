package asad.model.dataaccess.repository;

import asad.model.dataaccess.entity.ArticleTopicDistribution;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleTopicDistributionRepository extends CrudRepository<ArticleTopicDistribution, Integer> {

    @Query("select atd from ArticleTopicDistribution atd " +
            "join fetch atd.topic where atd.article.id = :id")
    List<ArticleTopicDistribution> findArticleTopic(@Param("id") Integer id);



}
