package View;

import Control.AsteroidsController;
import Control.LaserDetector.LaserDetector;
import Control.LaserDetector.OnFrameProcessedListener;
import Control.LaserDetector.OnMaskProcessedListener;
import Control.MovesPredictor;

import Model.Saver;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.opencv.core.*;
import org.opencv.video.Video;
import org.opencv.videoio.VideoWriter;
import org.opencv.videoio.Videoio;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by kareem on 7/1/17.
 */
//TODO use video writer to export mask
public class Main extends Application implements OnFrameProcessedListener, OnMaskProcessedListener{
    ImageView mask;
    ImageView frame;
    LaserDetector laserDetector;
    int count = 0;
    Saver<Mat> matBufferedSaver ;
    public static void main(String[] args){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
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
        matBufferedSaver = new Saver<Mat>(){
            @Override
            protected void save(Mat item, int num) {
                try {
                    ImageIO.write(Utils.matToBufferedImage(item),"jpeg", new File("images/im" + num+ ".jpg"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        primaryStage.show();
    }
    //overriding stop to close all Active threads
    @Override
    public void stop() throws Exception {
        super.stop();
        System.out.println("Rendering, Don't Close NOW !!");
        while (matBufferedSaver.isBusy()) Thread.sleep(500);
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
        laserDetector.setOnMaskProcessedListeners(this::onFinishMask);
        laserDetector.startDetecting();
    }
    @Override
    public void onFinish(Mat frame) {
        //For Debugging
        updateImageView(this.frame, frame);

    }

    public void onFinishMask(Mat frame) {
        //For Debugging
        matBufferedSaver.addObject(frame);
    }

    private void startGameSync()
    {
        AsteroidsController asteroidsController = new AsteroidsController(laserDetector);
        asteroidsController.start();
    }
}
