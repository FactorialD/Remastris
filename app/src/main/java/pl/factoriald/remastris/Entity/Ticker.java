package pl.factoriald.remastris.Entity;

public class Ticker{

    Tickable gameField;

    Thread thread;
    int delay;

    private Object mPauseLock;
    private boolean mPaused;
    private boolean mFinished;

    public Ticker(Tickable gameField,int delay) {
        this.gameField = gameField;
        mPauseLock = new Object();
        mPaused = false;
        mFinished = false;

        this.thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!mFinished) {
                    // Do stuff.
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    gameField.doTick();
                    synchronized (mPauseLock) {
                        while (mPaused) {
                            try {
                                mPauseLock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });

        this.delay = delay;
    }

    public void start(){
        thread.start();

    }

    public void pause(){
        synchronized (mPauseLock) {
            mPaused = true;
        }
    }

    public void unpause(){
        synchronized (mPauseLock) {
            mPaused = false;
        }
    }

    public void finish(){
        synchronized (mPauseLock) {
            mFinished = true;
        }
    }

}
