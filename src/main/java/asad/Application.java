package asad;

import asad.model.dataaccess.dao.GeneralDao;
import asad.scheduledServices.GraphService;
import asad.scheduledServices.TopicModelingService;
import asad.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.Properties;

@SpringBootApplication()
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

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

    @PostConstruct
    public void preProcess() throws IOException {
        Properties properties = getPropertyFile("scheduled.services.properties");
        if (Boolean.parseBoolean(properties.getProperty("runner_permission"))) {
            dropAndCreatePreProcessedTables();
            changeProperty("scheduled.services.properties", "runner_permission", "false");
        }
        System.out.println("System is ready to use");
    }


    void dropAndCreatePreProcessedTables() throws IOException {
        Utility.removeAndCreateNeededDirectories();
        generalDao.truncateTables();
        topicModelingService.createDenormalizedLemmatizedArticleText();

        topicModelingService.createAuthorBasedInputForTopicModeling();
        topicModelingService.createArticlesBasedInputForTopicModeling();

        topicModelingService.runArticleTopicModeling();
        topicModelingService.runAuthorTopicModeling();

        topicModelingService.createArticleTopicsTable();
        topicModelingService.createArticlesTopicDistribution();

        topicModelingService.createAuthorTopicsTable();
        topicModelingService.createAuthorsTopicDistribution();

        graphService.createCoAuthorsGraphFile();
        graphService.createRelatedArticlesGraphFile();

        graphService.runArticleLinkPrediction();
        graphService.runAuthorLinkPrediction();

        graphService.createArticlesPredictedLinkTable();
        graphService.createAuthorsPredictedLinkTable();
        System.out.println("END OF PREPROCCESSING.");
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
