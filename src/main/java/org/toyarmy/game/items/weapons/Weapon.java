package org.toyarmy.game.items.weapons;

import org.toyarmy.game.items.Item;
import org.toyarmy.game.items.magazines.Magazine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by Ramon Brand on 4/23/2017.
 * Toy Army - Unit movement and Rendering test application.
 * Code open source and free to use by anyone.
 */
public class Weapon extends Item {

    public static Map<String, Weapon> weaponMap = new HashMap<>();

    static {
        weaponMap.put("M16A4", new Weapon(3.26f, 700f, 1.0f, null, new HashSet<>(Arrays.asList("Stanag30", "Stanag20"))));
    }

    private float unloadedWeight;
    private float rateOfFire;
    private float barrelVelocityBoost;

    public Magazine getMagazine() {
        return magazine;
    }

    public boolean setMagazine(Magazine magazine) {
        if(!acceptedMagazines.contains(magazine.getType()))
            return false;

        this.magazine = magazine;
        return true;
    }

    private HashSet<String> acceptedMagazines;
    private Magazine magazine;

    public static Weapon getNewWeapon(String type) {
        Weapon template = weaponMap.get(type);
        if(template == null)
            return null;

        return new Weapon(template.unloadedWeight, template.rateOfFire, template.barrelVelocityBoost, template.magazine, template.acceptedMagazines);
    }

    private Weapon(float unloadedWeight, float rateOfFire, float barrelVelocityBoost, Magazine magazine, HashSet<String> acceptedMagazines) {
        this.unloadedWeight = unloadedWeight;
        this.rateOfFire = rateOfFire;
        this.barrelVelocityBoost = barrelVelocityBoost;
        this.magazine = magazine;
        this.acceptedMagazines = acceptedMagazines;
    }

    public float getUnloadedWeight() {
        return unloadedWeight;
    }

    public float getRateOfFire() {
        return rateOfFire;
    }

    public float getBarrelVelocityBoost() {
        return barrelVelocityBoost;
    }
}
