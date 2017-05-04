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

    public static Vector2f findIntersectionOf(LineSegment lineSegment1, LineSegment lineSegment2) {

        // Get points in memory
        float l1p1x = lineSegment1.point1.x;
        float l1p1y = lineSegment1.point1.y;
        float l1p2x = lineSegment1.point2.x;
        float l1p2y = lineSegment1.point2.y;
        float l2p1x = lineSegment2.point1.x;
        float l2p1y = lineSegment2.point1.y;
        float l2p2x = lineSegment2.point2.x;
        float l2p2y = lineSegment2.point2.y;

        float xPoint;
        float yPoint;

        if(l1p1x == l1p2x) {
            xPoint = l1p1x;

            // Get slope
            float l2slope = (l2p2y - l2p1y) / (l2p2x - l2p1x);

            // Solve y-intercept
            float l2yintercept = (l2slope * l2p1x - l2p1y) * -1f;

            // Point of intersection
            yPoint = l2slope * xPoint + l2yintercept;
        } else if (l2p1x == l2p2x) {
            xPoint = l2p1x;

            // Get slope
            float l1slope = (l1p2y - l1p1y) / (l1p2x - l1p1x);

            // Solve y-intercept
            float l1yintercept = (l1slope * l1p1x - l1p1y) * -1f;

            // Point of intersection
            yPoint = l1slope * xPoint + l1yintercept;
        } else {

            // Get slope
            float l1slope = (l1p2y - l1p1y) / (l1p2x - l1p1x);
            float l2slope = (l2p2y - l2p1y) / (l2p2x - l2p1x);

            // Solve y-intercept
            float l1yintercept = (l1slope * l1p1x - l1p1y) * -1f;
            float l2yintercept = (l2slope * l2p1x - l2p1y) * -1f;

            // Point of intersection
            xPoint = (l2yintercept - l1yintercept) / (l1slope - l2slope);
            yPoint = l1slope * xPoint + l1yintercept;
        }

        // Check if point is in segment1
        if(xPoint > Math.max(l1p1x, l1p2x))
            return null;

        if(xPoint < Math.min(l1p1x, l1p2x))
            return null;

        // Check if point is in segment2
        if(xPoint > Math.max(l2p1x, l2p2x))
            return null;

        if(xPoint < Math.min(l2p1x, l2p2x))
            return null;

        // Check if point is in segment1
        if(yPoint > Math.max(l1p1y, l1p2y))
            return null;

        if(yPoint < Math.min(l1p1y, l1p2y))
            return null;

        // Check if point is in segment2
        if(yPoint > Math.max(l2p1y, l2p2y))
            return null;

        if(yPoint < Math.min(l2p1y, l2p2y))
            return null;

        //System.out.println("INT: " + xPoint + " , " + yPoint);

        return new Vector2f(xPoint, yPoint);
    }
}
