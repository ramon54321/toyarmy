package org.toyarmy.game.items.armour;

/**
 * Created by Ramon Brand on 5/5/2017.
 * Toy Army - Unit movement and Rendering test application.
 * Code open source and free to use by anyone.
 */
public class BodyArmourMap {

    private float armourHead;
    private float armourTorso;
    private float armourlArm;
    private float armourrArm;
    private float armourlLeg;
    private float armourrLeg;

    public BodyArmourMap(float armourHead, float armourTorso, float armourlArm, float armourrArm, float armourlLeg, float armourrLeg) {
        this.armourHead = armourHead;
        this.armourTorso = armourTorso;
        this.armourlArm = armourlArm;
        this.armourrArm = armourrArm;
        this.armourlLeg = armourlLeg;
        this.armourrLeg = armourrLeg;
    }

    public float getArmourHead() {
        return armourHead;
    }

    public float getArmourTorso() {
        return armourTorso;
    }

    public float getArmourlArm() {
        return armourlArm;
    }

    public float getArmourrArm() {
        return armourrArm;
    }

    public float getArmourlLeg() {
        return armourlLeg;
    }

    public float getArmourrLeg() {
        return armourrLeg;
    }
}
