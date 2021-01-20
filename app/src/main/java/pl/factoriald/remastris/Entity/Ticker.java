package pl.factoriald.remastris.Entity;

import android.util.Log;

import lombok.Getter;

public class Ticker{

    private static Ticker instance;


    Tickable tickable;

    Thread thread;
    @Getter
    int delay = 1000;

    private volatile boolean paused = false;
    private final SingletonLock lock = SingletonLock.getInstance();

    //private final Object mPauseLock;
    //private boolean mPaused;
    //private boolean mFinished;

    public static Ticker getInstance(int delay){
        Log.d("TICKER", "create ticker with delay" + delay);
        if (instance == null) {
            instance = new Ticker(delay);
        }else{
            instance.delay = delay;
        }

        return instance;

    }

    public static Ticker getInstanceUnchecked(){

        return instance;

    }

    public static Ticker getNewInstance(int delay){
        Log.d("TICKER", "recreate ticker with delay" + delay);
        instance = new Ticker(delay);

        return instance;

    }

    public Ticker(int delay) {

        this.tickable = null;
//        mPauseLock = new Object();
//        mPaused = false;
//        mFinished = false;
        this.delay = delay;
        Log.d("LOKKKK" , "New instance of lock created. Delay is " + this.delay);
        this.thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (;;) {
                    synchronized (lock) {
                        while (paused) {
                            //Log.d("LOCK_WAIT", lock.toString());
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {}
                        }
                    }

                    tickable.doTick();
                    try {
                        //Log.d("DELAY", delay +"");
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
//                while (!mFinished) {
//                    // Do stuff.
//
//
//                        while (mPaused) {
//                            Log.d("TICKER", "Ticker paused");
//                            synchronized (mPauseLock) {
//                            try {
//                                mPauseLock.wait();
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                            }
//                        }
//                    Log.d("TICKER", "Ticker unpaused");
//                    tickable.doTick();
//                    try {
//                        Thread.sleep(delay);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                }
                //Log.d("TICKER", "Ticker finished");
            }
        });


    }

    public void addTickable(Tickable t){
        this.tickable = t;

    }

    public void start(){
        if(!thread.isAlive()){
            thread.start();
        }
        setPaused(false);


    }

    public void pause(){
//        synchronized (mPauseLock) {
//            mPaused = true;
//        }
        setPaused(true);
    }

    public void unpause(){
//        mPaused = false;
//        synchronized (mPauseLock) {
//            mPauseLock.notify();
//        }
             Log.d("TICKER", "UNPAUSE");
        setPaused(false);

    }

//    public void finish(){
//        synchronized (mPauseLock) {
//            mFinished = true;
//        }
//    }

    void setPaused(boolean shouldPause) {
        synchronized (lock) {
            Log.d("LOCK_SWITCH", lock.toString());
            paused = shouldPause;
            if (!paused) {
                lock.notify();
            }
            Log.d("LOCK_SWITCH", "Paused: " + paused);
        }
    }

    public boolean isPaused(){
        return paused;
    }

}
