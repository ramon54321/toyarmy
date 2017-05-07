package org.toyarmy.game.systems.optics;

/**
 * Created by Ramon Brand on 5/7/2017.
 * Toy Army - Unit movement and Rendering test application.
 * Code open source and free to use by anyone.
 */
public class OpticsSystem {

    private float range;
    private float fieldOfView;

    public OpticsSystem(float range, float fieldOfView) {
        this.range = range;
        this.fieldOfView = fieldOfView;
    }

    public float getRange() {
        return range;
    }

    public float getFieldOfView() {
        return fieldOfView;
    }
}
