package org.toyarmy.engine.math;

import java.util.Comparator;

/**
 * Created by Ramon Brand on 4/22/2017.
 * Toy Army - Unit movement and Rendering test application.
 * Code open source and free to use by anyone.
 */
public class IntersectDataDistanceComparator implements Comparator<IntersectData> {

    @Override
    public int compare(IntersectData i1, IntersectData i2) {
        if(i1.distanceFromOrigin > i2.distanceFromOrigin)
            return 1;
        else
            return -1;
    }

}
