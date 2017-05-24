package org.toyarmy.game.systems.movement;

import org.joml.Vector2f;
import org.toyarmy.engine.components.TransformComponent;
import org.toyarmy.engine.math.VectorMath;

/**
 * Created by Ramon Brand on 5/6/2017.
 * Toy Army - Unit movement and Rendering test application.
 * Code open source and free to use by anyone.
 */
public class MovementSystem {

    private TransformComponent transformComponent;

    private Vector2f target;
    private float targetBearing;

    private float rotationBaseSpeed = 270;

    private float maxSpeed = 2.4f;
    private float acceleration = 1.0f;

    public MovementSystem(TransformComponent transformComponent) {
        this.transformComponent = transformComponent;
        setTargetBearing(270);
    }

    public void setTarget(Vector2f target) {
        this.target = target;
    }

    public void setTargetBearing(float targetBearing) {
        this.targetBearing = targetBearing;
    }



    private Vector2f movementVector = new Vector2f();

    public void update(float deltaTime) {

        float rotationSpeed = (VectorMath.getBearingDifference(transformComponent.getRotation(), targetBearing) / 180) * 2;
        transformComponent.rotate(rotationSpeed * deltaTime * rotationBaseSpeed);

        if(target != null) {

            Vector2f toTargetVector = new Vector2f();
            target.sub(transformComponent.getPosition(), toTargetVector);

            if(toTargetVector.length() > 1) {
                toTargetVector.normalize();
                toTargetVector.mul(0.02f * acceleration);
                movementVector.add(toTargetVector);
            } else {
                movementVector.x = toTargetVector.x;
                movementVector.y = toTargetVector.y;
            }

            if(movementVector.length() > 1)
                movementVector.normalize();

            // Normal Movement
            transformComponent.translate(new Vector2f(movementVector).mul(deltaTime * maxSpeed));
        }
    }

}
