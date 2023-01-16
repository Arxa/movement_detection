import org.json.JSONObject;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfInt;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import java.util.AbstractMap;
import java.util.Base64;
import java.util.Map;

public class Camera {

    private static final VideoCapture videoCapture = new VideoCapture();

    /**
     * Opens the computer's default webcam
     */
    public static void openCamera() {
        videoCapture.open(0);
    }

    /**
     * [We want to show images in HTML which are provided from the Server.
     * HTML supports images in Base64 String format.
     * So, we encode our frames in base64 Strings, and then we
     * transmit those through the websocket]
     * <p>
     * Reads the webcam's next image frame1 as a Mat object.
     * Converts the Mat image to jpeg.
     * Encodes the jpeg image to base64 String representation.
     *
     * @return the full data URI of the image to be shown in HTML
     */
    public static JSONObject encodeWebcamFrame() {
        Mat frame1 = new Mat();
        Mat frame2 = new Mat();
        videoCapture.read(frame1);
        videoCapture.read(frame2);
        Map.Entry<Mat, Boolean> framePair = detectMotion(frame1, frame2);
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".jpeg", framePair.getKey(), matOfByte);
        String frameData = "data:image/jpeg;charset=utf-8;base64," + Base64.getEncoder().encodeToString(matOfByte.toArray());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("image", frameData);
        jsonObject.put("sound", framePair.getValue());
        return jsonObject;
    }

    /**
     * Detects movement between two images based on their absolute difference.
     *
     * @param first  First image
     * @param second Second image
     * @return The second image with the area(s) of movement being painted with bounding boxes.
     */
    public static Map.Entry<Mat, Boolean> detectMotion(Mat first, Mat second) {
        Mat painted = new Mat();
        second.copyTo(painted);
        //Imgproc.resize(first,first,new Size(300,300));
        //Imgproc.resize(second,second,new Size(300,300));
        Imgproc.GaussianBlur(first, first, new Size(15, 15), 0); // reducing noise
        Imgproc.GaussianBlur(second, second, new Size(15, 15), 0);
        Imgproc.cvtColor(first, first, Imgproc.COLOR_RGB2GRAY); // greyscale
        Imgproc.cvtColor(second, second, Imgproc.COLOR_RGB2GRAY);
        Mat diff = new Mat();
        Core.absdiff(first, second, diff);
        Imgproc.threshold(diff, diff, 50, 255, Imgproc.THRESH_BINARY); //binarization
        Imgproc.dilate(diff, diff, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(35, 35)));

        Mat stats = new Mat();
        int numberOfLabels = Imgproc.connectedComponentsWithStats(diff, new Mat(), stats, new Mat(), 8, CvType.CV_32S);

        boolean movementDetected = false;
        // Label 0 is considered to be the background label, so we skip it
        for (int i = 1; i < numberOfLabels; i++) {
            if (stats.get(i, 4)[0] < 1000) continue; // ignore really small detected areas
            movementDetected = true;
            // stats columns; [0-4] : [left top width height area}
            Rect textBlock = new Rect(new Point(stats.get(i, 0)[0], stats.get(i, 1)[0]),
                    new Size(stats.get(i, 2)[0], stats.get(i, 3)[0]));
            Imgproc.rectangle(painted, new Point(textBlock.x, textBlock.y),
                    new Point(textBlock.x + textBlock.width, textBlock.y + textBlock.height), new Scalar(255.0), 2);
        }
        return new AbstractMap.SimpleEntry<>(painted, movementDetected);
    }

//    public static void saveImage(Mat image, String name) {
//        MatOfInt params = new MatOfInt(Imgcodecs.IMWRITE_PNG_COMPRESSION);
//        Imgcodecs.imwrite("C:\\Users\\arxakoulini\\Desktop\\camera_security_system\\src\\main\\resources\\web\\alarm_sound.wav" + name, image, params);
//    }

}
