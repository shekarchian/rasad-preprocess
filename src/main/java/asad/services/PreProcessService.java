package asad.services;

import asad.model.dataaccess.dao.GeneralDao;
import asad.scheduledServices.GraphService;
import asad.scheduledServices.TopicModelingService;
import asad.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Reza Shekarchian
 */

@Component
public class PreProcessService {
    @Autowired
    TopicModelingService topicModelingService;
    @Autowired
    GraphService graphService;

    @Autowired
    GeneralDao generalDao;
    /*@Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {

        return null;
    }*/

    //    @PostConstruct
    public void preProcess(int part) throws IOException {
        System.out.println("Starting to pre processing part " + part);
        /*Properties properties = getPropertyFile("scheduled.services.properties");
        if (Boolean.parseBoolean(properties.getProperty("runner_permission"))) {
//            changeProperty("scheduled.services.properties", "runner_permission", "false");
        }*/
        if (part == 0) {
            Utility.removeAndCreateNeededDirectories();
            generalDao.truncateTables();
            topicModelingService.createDenormalizedLemmatizedArticleText();
        }
        if (part == 1) {
            topicModelingService.createAuthorBasedInputForTopicModeling();
            topicModelingService.createArticlesBasedInputForTopicModeling();
            topicModelingService.runArticleTopicModeling();
        }
        if (part == 2) {
            topicModelingService.runAuthorTopicModeling();
        }
        if (part == 3) {
            topicModelingService.createArticleTopicsTable();
            topicModelingService.createArticlesTopicDistribution();
        }
        if (part == 4) {
            topicModelingService.createAuthorTopicsTable();
            topicModelingService.createAuthorsTopicDistribution();

        }
        if (part == 5) {
            graphService.createCoAuthorsGraphFile();
            graphService.createRelatedArticlesGraphFile();

        }
        if (part == 6) {
            graphService.runArticleLinkPrediction();
        }
        if (part == 7) {
            graphService.runAuthorLinkPrediction();

        }
        if (part == 8) {
            graphService.createArticlesPredictedLinkTable();
            graphService.createAuthorsPredictedLinkTable();
        }



        System.out.println("Pre processing is done");
    }


    private Properties getPropertyFile(String fileName) {
        Properties prop = null;
        try  {
            ResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource banner = resourceLoader.getResource("classpath:"+ fileName);
            InputStream input = banner.getInputStream();
            prop = new Properties();
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return prop;
    }

    public static void changeProperty(String filename, String key, String value) {
        try {
            Properties prop = new Properties();
            prop.load(new FileInputStream(filename));
            prop.setProperty(key, value);
            prop.store(new FileOutputStream(filename), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
