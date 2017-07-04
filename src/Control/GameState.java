package Control;

import org.opencv.core.Point;

/**
 * Created by kareem on 7/3/17.
 * this class will contain all the needed data from game
 * also a parse function to parse string to Gamestates
 */
//TODO #belal #2
public class GameState {
    private Point playerLocation;  // u may prefer to use this as a Location class with x, y axis up to u :)
    private int gameType;
    private double playerAngle;


    //.. etc


    public GameState() {
        playerLocation = new Point();
    }

    public void parse(String gameStates_str) {
        //TODO #belal #3
        // like json parsing
        // may u can use Gson but it think it will be slower its ur call
        // manual will be cpu, memory friendly
        // from string u have to get useful info and assign it to the appropriate variables
        if (gameStates_str.length()< 9)return;
//        gameStates_str = gameStates_str.toUpperCase();
        String[] data = gameStates_str.split(",");
        playerLocation.x = Double.valueOf(data[0].substring(3));
        playerLocation.y = Double.valueOf(data[1].substring(0,data[1].length()-2));
        playerAngle = Double.valueOf(data[2].substring(1).substring(0,data[2].length() - 3));

    }

    public Point getPlayerLocation() {
        return playerLocation;
    }

    public void setPlayerLocation(Point playerLocation) {
        this.playerLocation = playerLocation;
    }

    public int getGameType() {
        return gameType;
    }

    public void setGameType(int gameType) {
        this.gameType = gameType;
    }

    public double getPlayerAngle() {
        return playerAngle;
    }

    public void setPlayerAngle(double angel) {
        this.playerAngle = angel;
    }

}