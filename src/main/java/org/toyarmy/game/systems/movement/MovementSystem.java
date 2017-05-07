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

    private float maxSpeed = 2;
    private float currentSpeed = 0;
    private float acceleration = 1f;
    private boolean braking = false;
    private Vector2f brakingVector;

    public MovementSystem(TransformComponent transformComponent) {
        this.transformComponent = transformComponent;
        setTargetBearing(270);
    }

    public void setTarget(Vector2f target) {
        this.target = target;
        this.braking = false;
    }

    public void setTargetBearing(float targetBearing) {
        this.targetBearing = targetBearing;
    }

    public void update(float deltaTime) {

        float rotationSpeed = (VectorMath.getBearingDifference(transformComponent.getRotation(), targetBearing) / 180) * 2;
        transformComponent.rotate(rotationSpeed * deltaTime * rotationBaseSpeed);

        if(target != null) {


            Vector2f movementVector = new Vector2f();
            target.sub(transformComponent.getPosition(), movementVector);

            float distanceToTarget = movementVector.length();

            movementVector.normalize();

            // Clear Movement Target
            //if(currentSpeed < 0.05f) {
            //    target = null;
            //    return;
            //}

            float breakingDistance = ((currentSpeed / acceleration) * (currentSpeed / acceleration)) * acceleration / 2;

            // Brake from now on
            if((distanceToTarget < breakingDistance || distanceToTarget < 0.1f) && !braking) {
                braking = true;
                brakingVector = new Vector2f(movementVector);
                System.out.println(brakingVector);
            }

            // Breaking
            if(braking) {
                currentSpeed -= acceleration * deltaTime;
                if(currentSpeed < 0)
                    currentSpeed = 0;
                transformComponent.translate(new Vector2f(brakingVector).mul(deltaTime * currentSpeed));
                return;
            }

            // Accelerating
            if(currentSpeed < maxSpeed) {
                currentSpeed += acceleration * deltaTime;
                if(currentSpeed > maxSpeed)
                    currentSpeed = maxSpeed;
                transformComponent.translate(movementVector.mul(deltaTime * currentSpeed));
                return;
            }

            // Normal Movement
            transformComponent.translate(movementVector.mul(deltaTime * currentSpeed));
        }
    }

}
