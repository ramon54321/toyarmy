package org.toyarmy.game.items.armour;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ramon Brand on 5/3/2017.
 * Toy Army - Unit movement and Rendering test application.
 * Code open source and free to use by anyone.
 */
public class BodyArmour {

    public static Map<String, BodyArmour> bodyArmourMap = new HashMap<>();

    static {
        bodyArmourMap.put("LightweightCarrier", new BodyArmour("LightweightCarrier",1000));
    }

    public static BodyArmour getNewBodyArmour(String type) {
        BodyArmour template = bodyArmourMap.get(type);
        if(template == null)
            return null;

        return new BodyArmour(template.type, template.armour);
    }

    private String type;
    private float armour;

    public BodyArmour(String type, float armour) {
        this.type = type;
        this.armour = armour;
    }

    public String getType() {
        return type;
    }

    public float getArmour() {
        return armour;
    }
}
