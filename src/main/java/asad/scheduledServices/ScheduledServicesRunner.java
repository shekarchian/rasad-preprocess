/*
package asad.scheduledServices;

import asad.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Arrays;

*/
/**
 * @author Reza Shekarchian
 *//*


@SpringBootApplication
public class ScheduledServicesRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    @Autowired
    TopicModelingService topicModelingService;

    @Autowired
    GraphService graphService;

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        topicModelingService.p();
        return args -> {

            System.out.println("executing services");

        };

    }
        //        topicModelingService.createDenormalizedLemmatizedArticleText();//todo drop table before run
//////////////////////////////////////////////////////////////////////

//        topicModelingService.createAuthorBasedInputForTopicModeling();

/////////////////////////////////////////////////////////////////////////////////////

        // topicModelingService.createArticlesBasedInputForTopicModeling();

// Drop tables
///////////////////////////////////////////////////////create RPC
//        topicModelingService.deleteTopicRecords();
//        topicModelingService.createArticleTopicsTable();
//        topicModelingService.createArticlesTopicDistribution();

///////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

//        topicModelingService.createAuthorTopicsTable();
//        topicModelingService.createAuthorsTopicDistribution();

        /////////////////////////////////////////////////////////////////////////////////////// link prediction

//        topicModelingService.createCoAuthorsGraphFile();

//        //////////////////////////////////////
        ///////////////////////////////////////drop table
//        topicModelingService.createPredictedAuthorsTable();

        //////////////////////////////////
//        graphService.createRelatedArticlesGraphFile();

        /////////////////////////////////////////////////////////////drop table
//        graphService.createArticlesPredictedLinkTable();
//        graphService.createAuthorsPredictedLinkTable();


}
*/
