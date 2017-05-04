package org.toyarmy.game.behaviours;

import org.joml.Matrix3f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.toyarmy.engine.Entity;
import org.toyarmy.engine.components.TransformComponent;
import org.toyarmy.engine.math.LineSegment;
import org.toyarmy.game.utility.CollisionSegment;

import java.util.ArrayList;

/**
 * Created by Ramon Brand on 4/20/2017.
 * Toy Army - Unit movement and Rendering test application.
 * Code open source and free to use by anyone.
 */
public class CollisionBehaviour extends Behaviour {

    private TransformComponent transformComponent;

    private ArrayList<CollisionSegment> collisionSegments = new ArrayList<>();

    @Override
    public void updateComponent(float deltaTime) {

    }

    public ArrayList<CollisionSegment> getCollisionSegments() {
        updateCollisionSegmentRotations();
        return collisionSegments;
    }

    private void updateCollisionSegmentRotations() {
        float rotation = (float) Math.toRadians(transformComponent.getRotation());
        Matrix3f rotationMatrix = new Matrix3f().rotateZ(-rotation);

        for(CollisionSegment collisionSegment : collisionSegments) {
            LineSegment segment = collisionSegment.getCollisionLineSegment();
            Vector3f point1 = new Vector3f(segment.point1.x, segment.point1.y, 1);
            Vector3f point2 = new Vector3f(segment.point2.x, segment.point2.y, 1);
            rotationMatrix.transform(point1);
            rotationMatrix.transform(point2);
            LineSegment rotatedSegment = new LineSegment(new Vector2f(point1.x, point1.y), new Vector2f(point2.x, point2.y));
            collisionSegment.setCollisionLineSegmentRotated(rotatedSegment);
        }
    }

    public CollisionBehaviour(Entity parentEntity) {
        super(parentEntity);

        this.transformComponent = (TransformComponent) parentEntity.getComponent(TransformComponent.class);
        GameManager.instance.collisionEntitiesCollisionBehaviours.add(this);
    }

    public TransformComponent getTransformComponent() {
        return transformComponent;
    }
}
