package Model;

import java.io.BufferedWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

/**
 * Created by kareem on 7/5/17.
 */
public class Saver<T> implements Runnable {
    private int maxWaitingObj = 20;
    ArrayList<T > queue;
    private volatile int objectNo = 0;
    private volatile boolean isBusy;
    public Saver() {
        queue = new ArrayList<>();
        isBusy = false;
    }

    public synchronized void addObject(T item)
    {
        queue.add(item);
        if (!isBusy){
            initThread();
        }
    }
    protected void save(T item, int num)
    {
        //TODO
    }

    @Override
    public void run() {

    }

    public boolean isBusy() {
        return isBusy;
    }
    private void initThread()
    {
        new Thread(() -> {
           isBusy = true;
           while (queue.size() > 0) {
               save(queue.get(0), objectNo++);
               queue.remove(0);
           }
           isBusy = false;
       }).start();
    }
}
