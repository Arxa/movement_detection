public class Initializer {

    public static void loadNatives(){
        try {
            System.load(Initializer.class.getResource("opencv_java320_64.dll").toURI().getPath());
        } catch (Exception e) {
            System.out.println("Failed to load native file: " + e.getMessage());
        }

    }
}
