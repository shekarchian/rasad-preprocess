package asad.model.dataaccess.dao;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;


@Service
@Transactional
public class GeneralDao {

    @PersistenceContext
    private EntityManager em;

    public void truncateTables(){
        System.out.println("started truncating tables");
        em.createNativeQuery("truncate table denormalized_lemmatized_article_text").executeUpdate();
        em.createNativeQuery("truncate table article_topic_distribution").executeUpdate();
        em.createNativeQuery("truncate table author_topic_distribution").executeUpdate();
        em.createNativeQuery("truncate table topic").executeUpdate();
        em.createNativeQuery("truncate table articles_predicted_link").executeUpdate();
        em.createNativeQuery("truncate table authors_predicted_link").executeUpdate();
        System.out.println("ended");

    }
}
