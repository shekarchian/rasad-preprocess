package asad.model.dataaccess.repository;

import asad.model.dataaccess.entity.Topic;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TopicRepository extends CrudRepository<Topic, Integer> {

    /*@Query("select topic from Topic topic " +
            "inner join fetch topic.topicDistributions" +
            " where topic.topicCode = :id ")
    Topic findTopicByTopicId(@Param("id") Integer id, Topic.Type.);*/

    List<Topic> findByType(Topic.Type type);

}
