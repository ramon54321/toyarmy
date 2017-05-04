package org.toyarmy.engine.math;

import org.joml.Vector2f;
import org.toyarmy.game.utility.CollisionSegment;

/**
 * Created by Ramon Brand on 4/22/2017.
 * Toy Army - Unit movement and Rendering test application.
 * Code open source and free to use by anyone.
 */
public class IntersectData {

    public Vector2f pointOfIntersect;
    public float distanceFromOrigin;
    public CollisionSegment collisionSegment;

    public IntersectData(Vector2f pointOfIntersect, float distanceFromOrigin, CollisionSegment collisionSegment) {
        this.pointOfIntersect = pointOfIntersect;
        this.distanceFromOrigin = distanceFromOrigin;
        this.collisionSegment = collisionSegment;
    }

    @Override
    public String toString() {
        return "POI: " + pointOfIntersect + " Distance: " + distanceFromOrigin;
    }
}
