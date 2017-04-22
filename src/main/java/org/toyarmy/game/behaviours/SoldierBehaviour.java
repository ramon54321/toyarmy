package org.toyarmy.game.behaviours;

import org.joml.Vector2f;
import org.toyarmy.engine.Entity;
import org.toyarmy.engine.components.LineRendererComponent;
import org.toyarmy.engine.components.TransformComponent;
import org.toyarmy.engine.math.LineSegment;
import org.toyarmy.engine.math.VectorMath;

import java.util.ArrayList;

/**
 * Created by Ramon Brand on 4/19/2017.
 * Toy Army - Unit movement and Rendering test application.
 * Code open source and free to use by anyone.
 */
public class SoldierBehaviour extends Behaviour {

    private TransformComponent transformComponent;

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void updateComponent(float deltaTime) {
        super.updateComponent(deltaTime);
    }

    public boolean hasLineOfSightTo(Vector2f target) {

        // Calculate Segment to Target
        LineSegment hereToTarget = new LineSegment(transformComponent.getPosition(), target);

        GameManager.instance.mainLineRenderer.setPoint1(hereToTarget.point1.x, hereToTarget.point1.y);
        GameManager.instance.mainLineRenderer.setPoint2(hereToTarget.point2.x, hereToTarget.point2.y);

        // Find nearby objects
        ArrayList<CollisionBehaviour> nearbyCollisionComponents = new ArrayList<>();
        float distanceToTarget = hereToTarget.getLength();
        for(CollisionBehaviour collisionBehaviour : GameManager.instance.collisionEntitiesCollisionBehaviours) {
            TransformComponent collisionObjectTransformComponent = collisionBehaviour.getTransformComponent();
            if(VectorMath.distanceBetween(transformComponent.getPosition(), collisionObjectTransformComponent.getPosition()) < distanceToTarget)
                nearbyCollisionComponents.add(collisionBehaviour);
        }

        //System.out.println("Nearby: " + nearbyCollisionComponents.size());

        // Check for intersects
        for(CollisionBehaviour collisionBehaviour : nearbyCollisionComponents) {
            for(LineSegment lineSegment : collisionBehaviour.getCollisionSegmentsWithRotation()) {
                Vector2f collisionPosition = collisionBehaviour.getTransformComponent().getPosition();
                Vector2f point1 = new Vector2f(lineSegment.point1).add(collisionPosition);
                Vector2f point2 = new Vector2f(lineSegment.point2).add(collisionPosition);
                if(LineSegment.isIntersecting(hereToTarget, new LineSegment(point1, point2))) {
                    GameManager.instance.mainLineRenderer.setColor(1.0f, 0.0f, 0.0f);
                    return false;
                }
            }
        }
        GameManager.instance.mainLineRenderer.setColor(0.0f, 1.0f, 0.0f);
        return true;
    }

    public SoldierBehaviour(Entity parentEntity) {
        super(parentEntity);

        this.transformComponent = (TransformComponent) parentEntity.getComponent(TransformComponent.class);
    }
}
