package asad.model.dataaccess.repository;

import asad.model.dataaccess.entity.AuthorTopicDistribution;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthorTopicDistributionRepository extends CrudRepository<AuthorTopicDistribution, Integer> {

    @Query("select atd from AuthorTopicDistribution atd " +
            "join fetch atd.topic where atd.author.id = :id")
    List<AuthorTopicDistribution> findAuthorTopic(@Param("id") Integer id);


}
