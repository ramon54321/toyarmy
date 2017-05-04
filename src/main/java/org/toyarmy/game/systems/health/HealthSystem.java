package org.toyarmy.game.systems.health;

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

    public HealthSystem() {
        head = new HealthComponent(this, 0,10,10,10,8);
        torso = new HealthComponent(this, 0,28,10,10,4);
        lArm = new HealthComponent(this, 0,16,10,10,0);
        rArm = new HealthComponent(this, 0,16,10,10,0);
        lLeg = new HealthComponent(this, 0,24,10,10,0);
        rLeg = new HealthComponent(this, 0,24,10,10,0);

        this.bloodPercentage = 100;
        this.bloodLossRate = 0;
    }

    public void update(float deltaTime) {
        bloodPercentage -= bloodLossRate * deltaTime;
    }

    public void calculateBloodLossRate() {
        bloodLossRate = head.getCurrentLeak() + torso.getCurrentLeak() + lArm.getCurrentLeak() + rArm.getCurrentLeak() + lLeg.getCurrentLeak() + rLeg.getCurrentLeak();
    }
}
