
public class Utils {

    public static void loadNatives(){
        try {
            System.load(Utils.class.getResource("natives/opencv_java320_64.dll").toURI().getPath());
        } catch (Exception e) {
            System.out.println("Failed to load native file: " + e.getMessage());
        }
    }
}
