import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import spark.Spark;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class Initializer {

    public static void loadNatives(){
        try {
            System.load(Initializer.class.getResource("natives/opencv_java320_64.dll").toURI().getPath());
        } catch (Exception e) {
            System.out.println("Failed to load native file: " + e.getMessage());
        }
    }

    public static void loadSpark(){
        final Configuration configuration = new Configuration(new Version(2, 3, 0));
        configuration.setClassForTemplateLoading(Initializer.class, "/");

        Spark.get("/", (request, response) -> {
            StringWriter writer = new StringWriter();
            try {
                Template resultTemplate = configuration.getTemplate("templates/index.ftl");
                Map<String, Object> map = new HashMap<>();
                map.put("image", Camera.getEncodedFrame());
                resultTemplate.process(map, writer);
            } catch (Exception e) {
                Spark.halt(500);
            }
            return writer;
        });
    }
}
