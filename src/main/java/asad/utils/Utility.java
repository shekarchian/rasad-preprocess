package asad.utils;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.*;
import java.util.Properties;

/**
 * @author Reza Shekarchian
 */
public class Utility {
    public static void runBashCommand(String script) {
        ProcessBuilder processBuilder = new ProcessBuilder();

        // Run a shell command
//        processBuilder.command("bash", "-c", script);

        // Run a shell script
        processBuilder.command(script);

        try {

            Process process = processBuilder.start();

            StringBuilder output = new StringBuilder();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println("Success!");
                System.out.println(output);
            } else {
                //abnormal...
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String executeCommand(String command) {

        StringBuffer output = new StringBuffer();

        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return output.toString();

    }

    public static Properties getTopicModelingPropertyFile() {
        /*Properties prop = null;
        try (InputStream input = new FileInputStream("src/main/resources/TopicModel.properties")) {
            prop = new Properties();
            prop.load(input);;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return prop;*/

        Properties prop = null;
        try  {
            ResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource banner = resourceLoader.getResource("classpath:"+ "TopicModel.properties");
            InputStream input = banner.getInputStream();
            prop = new Properties();
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return prop;
    }

    public static String getAddressProperty(String key) {
        Properties properties = getTopicModelingPropertyFile();
        String baseAddress = System.getProperty("user.name");
        return "/home" + "/" + baseAddress + "/" + properties.getProperty(key);
    }

    public static void removeAndCreateNeededDirectories() throws IOException {
        System.out.println("clearing base folder");
        Properties properties = getTopicModelingPropertyFile();
        FileUtils.deleteDirectory(new File(getAddressProperty("base.directory")));
        System.out.println("booos");
        System.out.println(getAddressProperty("base.directory"));

        new File(getAddressProperty("base.directory")).mkdir();
        new File(getAddressProperty("link_prediction.base.directory")).mkdir();
        new File(getAddressProperty("topic_modeling.base.directory")).mkdir();
        new File(getAddressProperty("article.topic_modeling.files.path")).mkdir();
        new File(getAddressProperty("author.topic_modeling.files.path")).mkdir();
        new File(getAddressProperty("article.link_prediction.files.path")).mkdir();
        new File(getAddressProperty("author.link_prediction.files.path")).mkdir();
        System.out.println("clearing base folder done");
    }
}
