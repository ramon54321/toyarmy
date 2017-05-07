package org.toyarmy.game.systems.health;

import org.toyarmy.debug.Controller;
import org.toyarmy.game.Globals;
import org.toyarmy.game.items.armour.BodyArmour;
import org.toyarmy.game.utility.BallisticData;
import org.toyarmy.game.utility.ChanceManager;

/**
 * Created by Ramon Brand on 5/3/2017.
 * Toy Army - Unit movement and Rendering test application.
 * Code open source and free to use by anyone.
 */
public class HealthSystem {

    private HealthComponent head;
    private HealthComponent torso;
    private HealthComponent lArm;
    private HealthComponent rArm;
    private HealthComponent lLeg;
    private HealthComponent rLeg;

    private float bloodPercentage;
    private float bloodLossRate;
    private float bloodRegenerationRate;

    private BodyArmour bodyArmour;

    public HealthSystem() {
        head = new HealthComponent(this, 0,10,50,50,10);
        torso = new HealthComponent(this, 0,28,400,400,50);
        lArm = new HealthComponent(this, 0,16,200,200,0);
        rArm = new HealthComponent(this, 0,16,200,200,0);
        lLeg = new HealthComponent(this, 0,24,240,240,0);
        rLeg = new HealthComponent(this, 0,24,240,240,0);

        this.bloodPercentage = 100;
        this.bloodLossRate = 0;
        this.bloodRegenerationRate = 1;

        bodyArmour = BodyArmour.getNewBodyArmour("LightweightCarrier");
    }

    public float getBloodPercentage() {
        return bloodPercentage;
    }

    public void update(float deltaTime) {
        bloodPercentage -= (bloodLossRate * deltaTime) / 60f;

        if(bloodPercentage < 5)
            die();


        if(bloodPercentage < 100) {
            bloodPercentage += bloodRegenerationRate * deltaTime;
            if(bloodPercentage > 100)
                bloodPercentage = 100;
        }
    }

    private void die() {

    }

    public void takeHit(BallisticData ballisticData) {

        float hitRandom = ChanceManager.random.nextFloat();
        int hitIndex = ChanceManager.getChanceIndex(hitRandom, new float[]{4, 8.75f, 4, 4, 6, 6});

        System.out.println("Outcome: " + hitIndex);

        switch (hitIndex) {
            case 0: // Head
                if(!ballisticData.dissipateBy(bodyArmour.getBodyArmourMap().getArmourHead()))
                    break;
                if(!head.decreaseFunctionality(ballisticData.energy))
                    die();
                head.increaseLeak((ballisticData.energy / Globals.bleedingDivider) * ballisticData.ammunition.getAntiPersonnel());
                break;
            case 1: // Torso
                if(!ballisticData.dissipateBy(bodyArmour.getBodyArmourMap().getArmourTorso()))
                    break;
                if(!torso.decreaseFunctionality(ballisticData.energy))
                    die();
                torso.increaseLeak((ballisticData.energy / Globals.bleedingDivider) * ballisticData.ammunition.getAntiPersonnel());
                break;
            case 2: // Left Arm
                if(!ballisticData.dissipateBy(bodyArmour.getBodyArmourMap().getArmourlArm()))
                    break;
                if(!lArm.decreaseFunctionality(ballisticData.energy))
                    die();
                lArm.increaseLeak((ballisticData.energy / Globals.bleedingDivider) * ballisticData.ammunition.getAntiPersonnel());
                break;
            case 3: // Right Arm
                if(!ballisticData.dissipateBy(bodyArmour.getBodyArmourMap().getArmourrArm()))
                    break;
                if(!rArm.decreaseFunctionality(ballisticData.energy))
                    die();
                rArm.increaseLeak((ballisticData.energy / Globals.bleedingDivider) * ballisticData.ammunition.getAntiPersonnel());
                break;
            case 4: // Left Leg
                if(!ballisticData.dissipateBy(bodyArmour.getBodyArmourMap().getArmourlLeg()))
                    break;
                if(!lLeg.decreaseFunctionality(ballisticData.energy))
                    die();
                lLeg.increaseLeak((ballisticData.energy / Globals.bleedingDivider) * ballisticData.ammunition.getAntiPersonnel());
                break;
            case 5: // Right Leg
                if(!ballisticData.dissipateBy(bodyArmour.getBodyArmourMap().getArmourrLeg()))
                    break;
                if(!rLeg.decreaseFunctionality(ballisticData.energy))
                    die();
                rLeg.increaseLeak((ballisticData.energy / Globals.bleedingDivider) * ballisticData.ammunition.getAntiPersonnel());
                break;
        }
        System.out.println(this);
    }

    public void calculateBloodLossRate() {
        bloodLossRate = head.getCurrentLeak() + torso.getCurrentLeak() + lArm.getCurrentLeak() + rArm.getCurrentLeak() + lLeg.getCurrentLeak() + rLeg.getCurrentLeak();
    }

    public BodyArmour getBodyArmour() {
        return bodyArmour;
    }

    @Override
    public String toString() {
        return "Head: Leaking at " + head.getCurrentLeak() + "/" + head.getMaxLeak() + " Functioning at " + head.getCurrentFunctionality() + "/" + head.getMaxFunctionality() + "\n"
                + "Torso: Leaking at " + torso.getCurrentLeak() + "/" + torso.getMaxLeak() + " Functioning at " + torso.getCurrentFunctionality() + "/" + torso.getMaxFunctionality() + "\n"
                + "Left Arm: Leaking at " + lArm.getCurrentLeak() + "/" + lArm.getMaxLeak() + " Functioning at " + lArm.getCurrentFunctionality() + "/" + lArm.getMaxFunctionality() + "\n"
                + "Right Arm: Leaking at " + rArm.getCurrentLeak() + "/" + rArm.getMaxLeak() + " Functioning at " + rArm.getCurrentFunctionality() + "/" + rArm.getMaxFunctionality() + "\n"
                + "Left Leg: Leaking at " + lLeg.getCurrentLeak() + "/" + lLeg.getMaxLeak() + " Functioning at " + lLeg.getCurrentFunctionality() + "/" + lLeg.getMaxFunctionality() + "\n"
                + "Right Leg: Leaking at " + rLeg.getCurrentLeak() + "/" + rLeg.getMaxLeak() + " Functioning at " + rLeg.getCurrentFunctionality() + "/" + rLeg.getMaxFunctionality() + "\n";
    }
}
