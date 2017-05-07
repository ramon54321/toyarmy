package org.toyarmy.game.items.armour;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ramon Brand on 5/3/2017.
 * Toy Army - Unit movement and Rendering test application.
 * Code open source and free to use by anyone.
 */
public class BodyArmour {

    public static Map<String, BodyArmour> bodyArmourDataMap = new HashMap<>();

    static {
        bodyArmourDataMap.put("LightweightCarrier", new BodyArmour("LightweightCarrier", new BodyArmourMap(0,1000, 0,0,0,0)));
    }

    public static BodyArmour getNewBodyArmour(String type) {
        BodyArmour template = bodyArmourDataMap.get(type);
        if(template == null)
            return null;

        return new BodyArmour(template.type, template.bodyArmourMap);
    }

    private String type;
    private BodyArmourMap bodyArmourMap;

    public BodyArmour(String type, BodyArmourMap bodyArmourMap) {
        this.type = type;
        this.bodyArmourMap = bodyArmourMap;
    }

    public String getType() {
        return type;
    }

    public BodyArmourMap getBodyArmourMap() {
        return bodyArmourMap;
    }
}
