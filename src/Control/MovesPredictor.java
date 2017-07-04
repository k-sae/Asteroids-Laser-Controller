package Control;

import org.opencv.core.Mat;

import java.awt.*;

import Control.LaserDetector.LaserDetector;
import Control.LaserDetector.OnFrameProcessedListener;
import Control.LaserDetector.OnLaserDetectionListener;
import org.opencv.core.Mat;
import org.opencv.core.Point;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by PC - MeiR on 7/4/2017.
 */
public class MovesPredictor {
    private Point unrealPoint = new Point();

    private double HypotenuseLength;
    private double horizontalLength;


    public double horizontal_xLineAngle(Point fstPoint, Point secPoint) {

        if (fstPoint.y == secPoint.y && fstPoint.x < secPoint.x)
            return 0.0;

        else if (fstPoint.y == secPoint.y && fstPoint.x > secPoint.x)
            return 180.0;

        else if (fstPoint.x == secPoint.x && fstPoint.y < secPoint.y)
            return 90.0;

        else if (fstPoint.x == secPoint.x && fstPoint.y > secPoint.y)
            return 270.0;
        else if (fstPoint.x == secPoint.x && fstPoint.y == secPoint.y)
            return 0.0;

        this.HypotenuseLength = lengthBetween2Points(fstPoint, secPoint);
        this.horizontalLength = horizontal_xLineLength(fstPoint, secPoint);

        if (fstPoint.y < secPoint.y)
            return (secPoint.x < fstPoint.x) ? Math.acos(this.horizontalLength / this.HypotenuseLength) * (180 / Math.PI) + 90 : Math.acos(this.horizontalLength / this.HypotenuseLength) * (180 / Math.PI);


        return ((secPoint.x < fstPoint.x) ? Math.acos(this.horizontalLength / this.HypotenuseLength) * (180 / Math.PI) : Math.acos(this.horizontalLength / this.HypotenuseLength) * (180 / Math.PI) + 90) + 180;


    }

    public double horizontal_xLineLength(Point fstPoint, Point secPoint) {

        this.unrealPoint.x = fstPoint.x;
        this.unrealPoint.y = secPoint.y;

        return lengthBetween2Points(this.unrealPoint, secPoint);
    }

    public double lengthBetween2Points(Point fstPoint, Point secPoint) {
        return Math.sqrt(Math.pow(fstPoint.x - secPoint.x, 2) + Math.pow(fstPoint.y - secPoint.y, 2));
    }

    public double fixAngle(double angle) {

        angle = (angle < 0) ? -1 * (((-1) * angle) % 360) : angle % 360;


        //inastroid game we consider the third  is the first
        if (angle > 0 && angle < 90)
            angle += 180;
        else if (angle > 90 && angle < 180)
            angle += 180;

        else if (angle > 180 && angle < 270)
            angle -= 180;

        else if (angle > 270 && angle < 360)
            angle -= 180;

        return angle;

    }
}
