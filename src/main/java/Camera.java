import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import java.nio.file.Paths;
import java.util.Base64;

public class Camera {

    private static VideoCapture videoCapture = new VideoCapture();
    private static Mat frame1 = new Mat();
    private static Mat frame2 = new Mat();
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
     * Reads the webcam's next image frame1 as a Mat object.
     * Converts the Mat image to jpeg.
     * Encodes the jpeg image to base64 String representation.
     * @return the full data URI of the image to be shown in HTML
     */
    public static String encodeWebcamFrame() {
        Mat temp = new Mat();
        videoCapture.read(frame1); // reads webcam's next frame1
        frame1.copyTo(temp);
        videoCapture.read(frame2); // reads webcam's next frame1
        Mat painted = detectMotion(frame1,frame2);
        Imgcodecs.imencode(".jpeg", painted, matOfByte);
        return "data:image/jpeg;charset=utf-8;base64," + Base64.getEncoder().encodeToString(matOfByte.toArray());
    }

    public static Mat detectMotion(Mat first,Mat second){
        Mat painted = new Mat();
        second.copyTo(painted);
        //Imgproc.resize(first,first,new Size(300,300));
        //Imgproc.resize(second,second,new Size(300,300));
        Imgproc.GaussianBlur(first,first,new Size(15,15),0);
        Imgproc.GaussianBlur(second,second,new Size(15,15),0);
        Imgproc.cvtColor(first,first,Imgproc.COLOR_RGB2GRAY);
        Imgproc.cvtColor(second,second,Imgproc.COLOR_RGB2GRAY);
        Mat diff = new Mat();
        Core.absdiff(first,second,diff);
        Imgproc.threshold(diff,diff,50,255,Imgproc.THRESH_BINARY);
        Imgproc.dilate(diff,diff,Imgproc.getStructuringElement(Imgproc.MORPH_RECT,new Size(35,35)));

        Mat stats = new Mat();
        int numberOfLabels = Imgproc.connectedComponentsWithStats(diff,new Mat(),stats,new Mat(),8, CvType.CV_32S);

        // Label 0 is considered to be the background label, so we skip it
        for (int i = 1; i < numberOfLabels; i++)
        {
            if (stats.get(i,4)[0] < 500) continue;
            // stats columns; [0-4] : [left top width height area}
            Rect textBlock = new Rect(new Point(stats.get(i,0)[0],stats.get(i,1)[0]),
                    new Size(stats.get(i,2)[0],stats.get(i,3)[0]));
            Imgproc.rectangle(painted, new Point(textBlock.x,textBlock.y),
                    new Point(textBlock.x+textBlock.width,textBlock.y+textBlock.height),new Scalar(255.0),2);
        }
        saveImage(diff,"diff.png");
        return painted;
    }

    public static void saveImage(Mat image,String name){
        MatOfInt params = new MatOfInt(Imgcodecs.CV_IMWRITE_PNG_COMPRESSION);
        Imgcodecs.imwrite("C:\\Users\\arxakoulini\\Desktop\\camera_security_system\\src\\main\\resources\\web\\"+name,image,params);
    }

}
