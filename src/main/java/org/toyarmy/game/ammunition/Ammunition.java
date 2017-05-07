package org.toyarmy.game.ammunition;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ramon Brand on 4/23/2017.
 * Toy Army - Unit movement and Rendering test application.
 * Code open source and free to use by anyone.
 */
public class Ammunition {

    public static Map<String, Ammunition> ammunitionMap = new HashMap<>();

    static {
        ammunitionMap.put("556x45Ball", new Ammunition("556x45Ball", 0.004f, 920f,1.0f, 0.99836f, 1.0f));
    }

    private String type;
    private float bulletMass = 0.004f;
    private float standardMuzzleVelocity = 920f;
    private float armourPiercing = 1.0f;
    private float velocityRetention = 0.99836f;
    private float antiPersonnel = 1.0f;

    public Ammunition(String type, float bulletMass, float standardMuzzleVelocity, float armourPiercing, float velocityRetention, float antiPersonnel) {
        this.type = type;
        this.bulletMass = bulletMass;
        this.standardMuzzleVelocity = standardMuzzleVelocity;
        this.armourPiercing = armourPiercing;
        this.velocityRetention = velocityRetention;
        this.antiPersonnel = antiPersonnel;
    }

    public float getBulletMass() {
        return bulletMass;
    }

    public float getStandardMuzzleVelocity() {
        return standardMuzzleVelocity;
    }

    public float getArmourPiercing() {
        return armourPiercing;
    }

    public float getVelocityRetention() {
        return velocityRetention;
    }

    public float getAntiPersonnel() {
        return antiPersonnel;
    }

    @Override
    public String toString() {
        return this.type + " - Bullet Mass: " + this.bulletMass + " - Velocity Retention /m: " + this.velocityRetention + " - Standard Muzzle Velocity: " + this.standardMuzzleVelocity + " - Armour Piercing: "
                + this.armourPiercing + " - Anti Personnel: " + this.antiPersonnel;
    }
}
