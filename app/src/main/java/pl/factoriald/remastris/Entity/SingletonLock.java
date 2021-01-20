package pl.factoriald.remastris.Entity;

public class SingletonLock {
    private static SingletonLock instance;

    public static synchronized SingletonLock getInstance() {
        if (instance == null) {
            instance = new SingletonLock();
        }
        return instance;
    }
}
