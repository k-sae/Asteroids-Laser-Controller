package Control;

import Control.LaserDetector.LaserDetector;
import Control.LaserDetector.OnLaserDetectionListener;
import org.opencv.core.Point;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by kareem on 7/2/17.
 */
public class AsteroidsController implements OnLaserDetectionListener {

    //WARNING!!!
    //this class working with 2 threads concurrently beside the Ui thread So make sure to Take Care of every interact
    private LaserDetector laserDetector;
    private Robot robot;
    private GameState gameState;
    private Point laserLocation;
    public  AsteroidsController(LaserDetector laserDetector)
    {
        this.laserDetector = laserDetector;
        gameState = new GameState();
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void start()
    {
        laserDetector.setOnLaserDetectionListeners(this);
        startGameStream();
    }

     private void startGameStream()
     {
         new Thread(new Runnable() {
             @Override
             public void run() {
         ArrayList<String> params = new ArrayList<>(1);
         params.add(getGameBin());
         ProcessBuilder processBuilder = new ProcessBuilder(params);
         processBuilder.redirectErrorStream(true); // for some reason it will not work without it :0
         try {
             Process process = processBuilder.start();
             BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
             String line;
             while ((line = input.readLine()) != null) {
                AnalyzeGameStates(line);
             }
         } catch (IOException e) {
             e.printStackTrace();
         }

             }
         }).start();
     }

    @Override
    public void onDetection(Point point) {
        unifyResolution(point);
        laserLocation = point;
    }

    private String getGameBin()
    {
        String s = System.getProperty("os.name").toLowerCase();
        if (s.contains("lin")) return "gameBin/linux/Asteroids";
        else return "gameBin/windows/asteroids.exe";
    }
    // TODO #belal #1
    //  make sure the wait between key events so game be able to capture keys
    /**
     * this function reads the data from game
     * u will have to give commands according to game states
     * @param line full asteroid game state in one line
     */
    private void AnalyzeGameStates(String line)
    {
        //implement this function
        gameState.parse(line);
        //pseudo code
        // if game type = menu
//            robot.keyPress(KeyEvent.VK_1);
//            sleep(200ms) // increase or decrease them for what u think is enough
//            robot.keyRelease(KeyEvent.VK_1);
        //etc..
        //lastly
        alterKeyCombination(laserLocation, gameState.getPlayerLocation());
    }

    private void unifyResolution(Point point)
    {
        //TODO #belal
        //change this to match the the resolution of game
        // u may leave to me if u want
        //Tips:
        //     1- dont create a new instance for better performance
        //     2- HF :D

    }

    //TODO #thirdMember

    /**
     * this function figures out the suitable key combinations to make the spaceship reach its location
     *
     * @param laserLocation
     * @param playerLocation
     */
    private void alterKeyCombination(Point laserLocation, Point playerLocation)
    {
        //tips:
        //  try not to create new instances only if needed
        //  this function will be called a lot and may cause memory leak on linux
        //  don't delete comments
    }

}
