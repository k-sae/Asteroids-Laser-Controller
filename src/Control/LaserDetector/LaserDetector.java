package Control.LaserDetector;

import View.Utils;
import javafx.scene.image.Image;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by kareem on 7/2/17.
 */
//TODO #kareem
    // provide a setters for Laser type (shiny red , normal red , green , etc)
public class LaserDetector {
    private ScheduledExecutorService timer;
    private VideoCapture capture;
    private List<OnLaserDetectionListener> onLaserDetectionListeners;
    private List<OnFrameProcessedListener> onFrameProcessedListeners;
    private List<OnMaskProcessedListener> onMaskProcessedListeners;

    public LaserDetector() {
        onFrameProcessedListeners = new ArrayList<>();
        onMaskProcessedListeners = new ArrayList<>();
        onLaserDetectionListeners = new ArrayList<>();
    }

    public void startDetecting() {
        capture = new VideoCapture();
        capture.open(0);
        Runnable frameGrabber = new Runnable() {

            @Override
            public void run() {
                // effectively grab and process a single frame
                Mat frame = grabFrame();
                // convert and show the frame
                Image imageToShow = Utils.mat2Image(frame);
                //TODO trigger listener up here
            }
        };

        this.timer = Executors.newSingleThreadScheduledExecutor();
        this.timer.scheduleAtFixedRate(frameGrabber, 0, 20, TimeUnit.MILLISECONDS);
    }

    private Mat grabFrame() {
        // init everything
        Mat frame = new Mat();

        // check if the capture is open
        if (this.capture.isOpened()) {
            try {
                // read the current frame
                this.capture.read(frame);

                // if the frame is not empty, process it
                if (!frame.empty()){
                    trackLaser(frame).assignTo(frame);
                }

            } catch (Exception e) {
                // log the error
                System.err.println("Exception during the image elaboration: " + e);
            }
        }

        return frame;
    }
    private Mat trackLaser(Mat frame)
    {
        Mat mask = new Mat();
        Mat hsv = new Mat();
        Imgproc.cvtColor(frame,hsv , Imgproc.COLOR_BGR2HSV);
        //shiny RED Color
        Scalar lowerScale = new Scalar(0, 0, 255);
        Scalar upperScale = new Scalar(255, 255, 255);
        Core.inRange(hsv, lowerScale, upperScale, mask);
        final List<MatOfPoint> matOfPoints = new ArrayList<>();
        Imgproc.findContours(mask, matOfPoints, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        if (matOfPoints.size() > 0) {
//            Core.MinMaxLocResult minMaxLocResult = Core.minMaxLoc(mask);
            Point center = new Point();
            float[] radius = new float[1];
            Imgproc.minEnclosingCircle(new MatOfPoint2f(matOfPoints.get(maxPoint(matOfPoints)).toArray()),center,radius);
//            System.out.println(center.x + ":" + center.y);
            //Trigger Listener up here
            triggerLaserDetectionFrame(center);
            Imgproc.circle(frame,center , 7, new Scalar(255, 0, 0), 2);
        }
        triggerProcessingListeners(onFrameProcessedListeners, frame);
        triggerProcessingListeners(onMaskProcessedListeners, mask);
        return frame ;
    }
    private int maxPoint(List<MatOfPoint> matOfPoints)
    {
        int matOfPoint= 0;
        double maxArea = 0;
        for (int i = 0; i < matOfPoints.size(); i++) {
            double area = Imgproc.contourArea(matOfPoints.get(i).clone());
            if (maxArea < area) {
                maxArea = area;
                matOfPoint = i;
            }
        }
        return matOfPoint;
    }

    //Listeners down here Just ignore them :)

    public void setOnLaserDetectionListeners(OnLaserDetectionListener onLaserDetectionListener)
    {
        onLaserDetectionListeners.add(onLaserDetectionListener);
    }
    public void setOnFrameProcessedListeners(OnFrameProcessedListener onFrameProcessedListener)
    {
        onFrameProcessedListeners.add(onFrameProcessedListener);
    }
    public void setOnMaskProcessedListeners(OnMaskProcessedListener onMaskProcessedListener)
    {
        onMaskProcessedListeners.add(onMaskProcessedListener);
    }

    private void triggerProcessingListeners(List<?> processingListeners, Mat mat)
    {
        for (ProcessingListener processingListener: (List<ProcessingListener>) processingListeners
             ) {
            processingListener.onFinish(mat);
        }
    }

    private void triggerLaserDetectionFrame(Point point)
    {
        for (OnLaserDetectionListener onLaserDetectionListener: onLaserDetectionListeners
             ) {
            onLaserDetectionListener.onDetection(point);
        }
    }
}
