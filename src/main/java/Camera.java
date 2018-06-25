import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import java.util.Base64;

public class Camera {

    private static VideoCapture videoCapture = new VideoCapture();
    private static Mat frame = new Mat();
    private static MatOfByte matOfByte = new MatOfByte();

    /**
     * Open the computer's default webcam
     */
    public static void openCamera(){
        videoCapture.open(0);
    }

    /**
     * [We want to show images in HTML which are provided from the Server.
     * HTML supports images in Base64 String format.
     * So, we encode our frames in base64 Strings, and then we
     * transmit those through the websocket]
     *
     * Reads the webcam's next image frame as a Mat object.
     * Converts the Mat image to jpeg.
     * Encodes the jpeg image to base64 String representation.
     * @return the full data URI of the image to be shown in HTML
     */
    public static String encodeWebcamFrame() {
        videoCapture.read(frame); // reads webcam's next frame
        Imgcodecs.imencode(".jpeg", frame, matOfByte);
        return "data:image/jpeg;charset=utf-8;base64," + Base64.getEncoder().encodeToString(matOfByte.toArray());
    }

}
