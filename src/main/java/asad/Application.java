package asad;

import asad.services.PreProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.annotation.PostConstruct;
import java.io.IOException;

@SpringBootApplication()
public class Application {
    private static int part;

    private static ConfigurableApplicationContext sp;
    public static void main(String[] args) throws IOException {
        part = Integer.parseInt(args[0]);
         sp =SpringApplication.run(Application.class, args);

    }

    @Autowired
    PreProcessService preProcessService;

    @PostConstruct
    public void preProcess() throws IOException {
        preProcessService.preProcess(part);
        System.out.println("part " + part + " is done.");
        System.out.println("closing app");
        sp.close();
    }



}
