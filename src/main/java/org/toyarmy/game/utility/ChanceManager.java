package org.toyarmy.game.utility;

import java.util.Random;

/**
 * Created by Ramon Brand on 5/5/2017.
 * Toy Army - Unit movement and Rendering test application.
 * Code open source and free to use by anyone.
 */
public class ChanceManager {

    public static Random random = new Random();

    public static int getChanceIndex(float value, float[] weights) {

        int numberOfElements = weights.length;
        float[] chances = new float[numberOfElements];

        float total = 0;

        for(int i = 0; i < numberOfElements; i++) {
            total += weights[i];
        }

        for(int i = 0; i < numberOfElements; i++) {
            chances[i] = weights[i] / total;
        }

        float last = 0;
        for(int i = 0; i < numberOfElements; i++) {
            float next = last + chances[i];
            if(value >= last && value < next)
                return i;
            last = next;
        }

        return -1;
    }

}
