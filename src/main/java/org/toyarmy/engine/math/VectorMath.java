package org.toyarmy.engine.math;

import org.joml.Vector2f;

/**
 * Created by Ramon Brand on 4/20/2017.
 * Toy Army - Unit movement and Rendering test application.
 * Code open source and free to use by anyone.
 */
public class VectorMath {

    public static float distanceBetween(Vector2f start, Vector2f end){
        return (float) Math.sqrt(distanceBetweenSquared(start, end));
    }

    public static float distanceBetweenSquared(Vector2f start, Vector2f end){
        Vector2f vector2f = new Vector2f(end.x - start.x, end.y - start.y);
        return vector2f.x * vector2f.x + vector2f.y * vector2f.y;
    }

    public static int tripointOrientation(Vector2f point1, Vector2f point2, Vector2f point3) {
        float val = ((point2.y - point1.y) * (point3.x - point2.x) - (point2.x - point1.x) * (point3.y - point2.y));

        if (val == 0) return 0;

        return (val > 0) ? 1 : 2;
    }

}
