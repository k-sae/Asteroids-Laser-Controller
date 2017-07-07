package Control.MouseUtils;

import com.sun.xml.internal.stream.util.ThreadLocalBufferAllocator;
import org.opencv.core.Point;

import java.awt.MouseInfo;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MouseUtils {
    private ArrayList<OnMouseMoveListener> onMouseMoveListeners = new ArrayList<>();
    Location location = new Location();
    public MouseUtils(){

          //  TimeUnit.SECONDS.sleep(1/2);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    location.setX(MouseInfo.getPointerInfo().getLocation().getX());
                    location.setY(MouseInfo.getPointerInfo().getLocation().getY());
                    triggerOnMouseMoveListener();
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    public void setOnMouseMoveListeners(OnMouseMoveListener onMouseMoveListener) {
        onMouseMoveListeners.add(onMouseMoveListener);
    }
    public void removeOnMouseMoveListeners(OnMouseMoveListener onMouseMoveListener) {
        onMouseMoveListeners.remove(onMouseMoveListener);
    }
    private void triggerOnMouseMoveListener()
    {
        for (int i = 0; i < onMouseMoveListeners.size(); i++) {
            onMouseMoveListeners.get(i).onMove(location);
        }
    }
}
