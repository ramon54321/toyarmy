package org.toyarmy.engine.math;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

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

    public static float getAngleBetween(Vector2f vector1, Vector2f vector2) {
        return (float) (Math.acos((vector1.dot(vector2))/(vector1.length() * vector2.length())) * (180 / Math.PI));
    }

    public static float getBearingFrom(Vector2f from, Vector2f to) {
        return getBearingOf(new Vector2f(to).sub(from));
    }

    public static float getBearingOf(Vector2f vector) {

        float raw = (float) (Math.atan2(vector.y, vector.x) * (180 / Math.PI));

        if(raw == 180)
            return 270;

        if((raw > 0 && raw <= 90) || (raw <= 0 && raw >= -90))
            return 90 - raw;

        if(raw > 90)
            return 360 - (raw - 90);

        if(raw < -90)
            return 180 + (-raw - 90);

        return -1;
    }

    public static float lerp(float point1, float point2, float alpha) {
        return point1 + alpha * (point2 - point1);
    }

    public static float invertedLerp(float point1, float point2, float current) {
        return (current - point1) / (point2 - point1);
    }

    public static float linearToCubic(float alpha) {
        return alpha * alpha * (3.0f - 2.0f * alpha);
    }

    public static float getBearingDifference(float startBearing, float targetBearing) {
        float differenceRaw = targetBearing - startBearing;


        if(differenceRaw > 180) {// Left -> Neg
            return (360 - differenceRaw) * -1;
        } else if(differenceRaw < -180) {
            return 360 - (differenceRaw * -1);
        } else {//Right
            return differenceRaw;
        }
    }

    public static Vector2f getRotatedVector(Vector2f vector, float angle) {
        Vector3f rotatedVector = new Vector3f(vector.x, vector.y, 0);
        Matrix3f transformMatrix = new Matrix3f();
        transformMatrix.rotate(-angle / (180 / (float) Math.PI), 0, 0, 1);
        transformMatrix.transform(rotatedVector);
        return new Vector2f(rotatedVector.x, rotatedVector.y);
    }
}
