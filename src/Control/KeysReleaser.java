package Control;

/**
 * Created by kareem on 7/4/17.
 */
public  abstract class KeysReleaser extends Thread {
//    public void
    private int extend;
    private volatile boolean isWaiting;
    @Override
    public void run() {
        while (true)
        {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setExtend(-1);
            if (extend <= 0)
            {
                releaseAll();
                setWaiting(true);
                while (isWaiting);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void extend()
    {
        setWaiting(false);
            setExtend(1);
    }
    public abstract void releaseAll();
    private synchronized void setExtend(int val)
    {
        extend += val;
    }

    private synchronized void setWaiting(boolean waiting) {
        this.isWaiting = waiting;
    }
}
