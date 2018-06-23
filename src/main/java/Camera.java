import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import java.util.Base64;

public class Camera {

    private static VideoCapture videoCapture = new VideoCapture();
    private static Mat frame = new Mat();
    private static MatOfByte matOfByte = new MatOfByte();

    public static void openCamera(){
        videoCapture.open(0);
    }

    public static String encodeWebcamFrame() {
        videoCapture.read(frame);
        Imgcodecs.imencode(".jpeg", frame, matOfByte);
        return "data:image/jpeg;charset=utf-8;base64," + Base64.getEncoder().encodeToString(matOfByte.toArray());
    }

}
