package org.toyarmy.game.systems.optics;

import org.joml.Vector2f;
import org.toyarmy.engine.Entity;
import org.toyarmy.engine.EntityManager;
import org.toyarmy.engine.math.VectorMath;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ramon Brand on 5/7/2017.
 * Toy Army - Unit movement and Rendering test application.
 * Code open source and free to use by anyone.
 */
public class OpticsSystem {

    private Entity parentEntity;
    private float range;
    private float fieldOfView;

    public OpticsSystem(Entity parentEntity, float range, float fieldOfView) {
        this.parentEntity = parentEntity;
        this.range = range;
        this.fieldOfView = fieldOfView;
    }

    public Set<Entity> getEntitiesInFov() {
        Set<Entity> entitiesInView = new HashSet<>();

        Set<Entity> entitiesInRange = EntityManager.getInstance().getEntitiesWithinSquaredRadius(parentEntity, range * range);

        for (Entity entity : entitiesInRange) {
            float bearingToEntity = VectorMath.getBearingFrom(parentEntity.getTransformComponent().getPosition(), entity.getTransformComponent().getPosition());
            float differenceBetweenBearings = Math.abs(VectorMath.getBearingDifference(parentEntity.getTransformComponent().getRotation(), bearingToEntity));
            if(differenceBetweenBearings <= fieldOfView/2)
                entitiesInView.add(entity);
        }

        return entitiesInView;
    }

    public void update(float deltaTime) {

    }

    public float getRange() {
        return range;
    }

    public float getFieldOfView() {
        return fieldOfView;
    }
}
