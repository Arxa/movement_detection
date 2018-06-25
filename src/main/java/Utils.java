import org.apache.commons.lang3.SystemUtils;

import java.io.File;
import java.nio.file.Paths;

public class Utils {

    public static void loadNatives() throws Exception
    {
        String RESOURCES_NATIVES = Paths.get(new File(".").getCanonicalPath(),
                "src","main","resources","natives").toFile().getPath();

        if(SystemUtils.IS_OS_WINDOWS)
        {
            int bit = Integer.parseInt(System.getProperty("sun.arch.data.model"));
            if(bit == 32){
                System.load(Paths.get(RESOURCES_NATIVES,"opencv_320_32.dll").toString());
                System.out.println("Loaded OpenCV for Windows 32 bit");
            }
            else if (bit == 64){
                System.load(Paths.get(RESOURCES_NATIVES,"opencv_java320_64.dll").toString());
                System.out.println("Loaded OpenCV for Windows 64 bit");
            }
            else{
                System.out.println("Unknown Windows bit - trying with 32");
                System.load(Paths.get(RESOURCES_NATIVES,"opencv_java320_32.dll").toString());
                System.out.println("Loaded OpenCV for Windows 32 bit");
            }
        }
        else if(SystemUtils.IS_OS_MAC){
            System.out.println("MAC OS IS NOT SUPPORTED");
        }
        else if(SystemUtils.IS_OS_LINUX){
            System.load(Paths.get(RESOURCES_NATIVES,"libopencv_320_64.so").toString());
            System.out.println("Loaded OpenCV for Linux 64 bit\n");
        }
    }
}
