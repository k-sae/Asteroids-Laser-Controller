package Control;

import org.opencv.core.*;

import java.awt.*;
import java.awt.Point;

/**
 * Created by PC - MeiR on 7/4/2017.
 */
public class test {


    public static void main(String[] args) {
        MovesPredictor movesPredictor = new MovesPredictor();
        org.opencv.core.Point x = new org.opencv.core.Point();
        org.opencv.core.Point y = new org.opencv.core.Point();
        x.x=0;
        x.y=0;
        y.x=2;
        y.y=3;
        System.out.println(movesPredictor.fixAngle(45));
    }
}
