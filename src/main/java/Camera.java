import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class Camera {

    private static VideoCapture videoCapture = new VideoCapture();
    private static Mat frame = new Mat();
    private static MatOfByte matOfByte = new MatOfByte();

    public static void openCamera(){
        videoCapture.open(0);
    }

    public static String getEncodedFrame()
    {
        videoCapture.read(frame);
        Imgcodecs.imencode(".jpeg",frame,matOfByte);
        String message =  Base64.getEncoder().encodeToString(matOfByte.toArray());
        return "data:image/jpeg;charset=utf-8;base64,"+message;
    }

//    private static void showResult(Mat img) {
//        Imgproc.resize(img, img, new Size(640, 480));
//        MatOfByte matOfByte = new MatOfByte();
//        Imgcodecs.imencode(".jpg", img, matOfByte);
//        byte[] byteArray = matOfByte.toArray();
//        BufferedImage bufImage = null;
//        try {
//            InputStream in = new ByteArrayInputStream(byteArray);
//            bufImage = ImageIO.read(in);
//            JFrame frame = new JFrame();
//            frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//            frame.getContentPane().add(new JLabel(new ImageIcon(bufImage)));
//            frame.pack();
//            frame.setVisible(true);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
