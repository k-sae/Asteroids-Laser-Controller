package Control;

import org.opencv.core.Point;

import java.awt.MouseInfo;
import java.util.concurrent.TimeUnit;

public class MousePosition {

    public  Point GetMousePosition() throws InterruptedException {




          //  TimeUnit.SECONDS.sleep(1/2);
            double mouseX = MouseInfo.getPointerInfo().getLocation().getX();
            double mouseY = MouseInfo.getPointerInfo().getLocation().getY();
        Point point=new Point();
        point.x=mouseX;
        point.y=mouseY;
        return point;


    }

}
