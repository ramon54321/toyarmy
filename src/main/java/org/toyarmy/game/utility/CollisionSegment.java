package org.toyarmy.game.utility;

import org.toyarmy.engine.math.LineSegment;

/**
 * Created by Ramon Brand on 4/22/2017.
 * Toy Army - Unit movement and Rendering test application.
 * Code open source and free to use by anyone.
 */
public class CollisionSegment {

    private LineSegment collisionLineSegment;
    private LineSegment collisionLineSegmentRotated;
    private CollisionMaterial collisionMaterial;

    public LineSegment getCollisionLineSegmentRotated() {
        return collisionLineSegmentRotated;
    }

    public void setCollisionLineSegmentRotated(LineSegment collisionLineSegmentRotated) {
        this.collisionLineSegmentRotated = collisionLineSegmentRotated;
    }

    public LineSegment getCollisionLineSegment() {
        return new LineSegment(collisionLineSegment);
    }

    public CollisionMaterial getCollisionMaterial() {
        return collisionMaterial;
    }

    public CollisionSegment(LineSegment collisionLineSegment, CollisionMaterial collisionMaterial) {
        this.collisionLineSegment = collisionLineSegment;
        this.collisionMaterial = collisionMaterial;
    }
}
