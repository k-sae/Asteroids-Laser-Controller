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
    //.. etc


    public void parse(String gameStates_str)
    {
        //TODO #belal #3
        // like json parsing
        // may u can use Gson but it think it will be slower its ur call
        // manual will be cpu, memory friendly
        // from string u have to get useful info and assign it to the appropriate variables
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
}
