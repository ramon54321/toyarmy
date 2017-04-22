package org.toyarmy.engine.math;

import org.joml.Vector2f;

/**
 * Created by Ramon Brand on 4/19/2017.
 * Toy Army - Unit movement and Rendering test application.
 * Code open source and free to use by anyone.
 */
public class LineSegment {
    public Vector2f point1, point2;
    public LineSegment(Vector2f point1, Vector2f point2) {
        this.point1 = point1;
        this.point2 = point2;
    }
    public LineSegment(LineSegment segment) {
        this.point1 = new Vector2f(segment.point1);
        this.point2 = new Vector2f(segment.point2);
    }

    public float getLength(){
        return VectorMath.distanceBetween(point1, point2);
    }

    public static boolean isIntersecting(LineSegment line1, LineSegment line2) {
        // 4 orientation possibilities
        int o1 = VectorMath.tripointOrientation(line1.point1, line1.point2, line2.point1);
        int o2 = VectorMath.tripointOrientation(line1.point1, line1.point2, line2.point2);
        int o3 = VectorMath.tripointOrientation(line2.point1, line2.point2, line1.point1);
        int o4 = VectorMath.tripointOrientation(line2.point1, line2.point2, line1.point2);

        // General case
        if(o1 != o2 && o3 != o4)
            return true;

        return false;
    }
}
