package org.toyarmy.game.utility;

import org.toyarmy.game.ammunition.Ammunition;

/**
 * Created by Ramon Brand on 4/24/2017.
 * Toy Army - Unit movement and Rendering test application.
 * Code open source and free to use by anyone.
 */
public class BallisticData {

    public float velocity;
    public float energy;

    public Ammunition ammunition;

    public BallisticData(float velocity, float energy, Ammunition ammunition) {
        this.velocity = velocity;
        this.energy = energy;
        this.ammunition = ammunition;
    }
}
