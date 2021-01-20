package pl.factoriald.remastris.Entity;

import java.util.concurrent.ThreadLocalRandom;

public class Utility {

    public static int getRandomNumber(int minincluded, int maxincluded){
        return minincluded + (int)(Math.random() * ((maxincluded - minincluded) + 1));
    }
}
