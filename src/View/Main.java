package View;

import Control.AsteroidsController;
import Control.LaserDetector.LaserDetector;
import Control.LaserDetector.OnFrameProcessedListener;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.opencv.core.*;
import org.opencv.videoio.VideoWriter;

/**
 * Created by kareem on 7/1/17.
 */
//TODO use video writer to export mask
public class Main extends Application implements OnFrameProcessedListener{
    ImageView mask;
    ImageView frame;
    LaserDetector laserDetector;
    public static void main(String[] args){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat mat = Mat.eye(3, 3, CvType.CV_8UC1);
        System.out.println("mat = \n" + mat.dump());
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();
        frame = new ImageView();
        root.setLeft(frame);
        primaryStage.setTitle("Capture Color");
        primaryStage.setScene(new Scene(root, 900, 400));
        startLaserDetection();
        startGameSync();
        primaryStage.show();
    }
    //overriding stop to close all Active threads
    @Override
    public void stop() throws Exception {
        super.stop();
        System.exit(0);
    }

    private void updateImageView(ImageView view, Mat mat)
    {
        Utils.onFXThread(view.imageProperty(), Utils.mat2Image(mat));
    }
    private void startLaserDetection()
    {
       laserDetector = new LaserDetector();
       //setting listeners
       laserDetector.setOnFrameProcessedListeners(this);
       laserDetector.startDetecting();
    }
    @Override
    public void onFinish(Mat frame) {
        //For Debugging
        updateImageView(this.frame, frame);
    }

    private void startGameSync()
    {
        AsteroidsController asteroidsController = new AsteroidsController(laserDetector);
        asteroidsController.start();
    }
}
