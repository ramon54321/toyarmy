package org.toyarmy.game.items.magazines;

import org.toyarmy.game.items.Item;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ramon Brand on 4/23/2017.
 * Toy Army - Unit movement and Rendering test application.
 * Code open source and free to use by anyone.
 */
public class Magazine extends Item {

    public static Map<String, Magazine> magazineMap = new HashMap<>();

    static {
        magazineMap.put("Stanag30", new Magazine("Stanag30", "556x45Ball",30));
    }

    public static Magazine getNewMagazine(String type) {
        Magazine template = magazineMap.get(type);
        if(template == null)
            return null;

        return new Magazine(template.type, template.ammunitionType, template.maxCount);
    }

    private Magazine(String type, String ammunitionType, int maxCount) {
        this.type = type;
        this.ammunitionType = ammunitionType;
        this.maxCount = maxCount;
        this.currentCount = maxCount;
    }

    private String type;
    private String ammunitionType;
    private int maxCount;
    private int currentCount;

    public String getType() {
        return type;
    }

    public String getAmmunitionType() {
        return ammunitionType;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public int getCurrentCount() {
        return currentCount;
    }

    public void addRound() {
        currentCount++;

        if(currentCount > maxCount)
            currentCount = maxCount;
    }

    public boolean takeRound() {
        currentCount--;

        if(currentCount < 0) {
            currentCount = 0;
            return false;
        }

        return true;
    }
}
