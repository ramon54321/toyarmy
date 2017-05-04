package org.toyarmy.game.systems.health;

/**
 * Created by Ramon Brand on 5/4/2017.
 * Toy Army - Unit movement and Rendering test application.
 * Code open source and free to use by anyone.
 */
public class HealthComponent {

    private HealthSystem healthSystem;
    private float currentLeak;
    private float maxLeak;
    private float currentFunctionality;
    private float maxFunctionality;
    private float vitalRequirement;

    public HealthComponent(HealthSystem healthSystem, float currentLeak, float maxLeak, float currentFunctionality, float maxFunctionality, float vitalRequirement) {
        this.healthSystem = healthSystem;
        this.currentLeak = currentLeak;
        this.maxLeak = maxLeak;
        this.currentFunctionality = currentFunctionality;
        this.maxFunctionality = maxFunctionality;
        this.vitalRequirement = vitalRequirement;
    }

    public void decreaseLeak(float amount) {
        currentLeak -= amount;

        if(currentLeak <= 0)
            currentLeak = 0;

        healthSystem.calculateBloodLossRate();
    }

    // Returns true if the leak is maxed out
    public boolean increaseLeak(float amount) {
        if(currentLeak >= maxLeak) {
            healthSystem.calculateBloodLossRate();
            return true;
        }

        currentLeak += amount;

        if(currentLeak >= maxLeak) {
            currentLeak = maxLeak;
            healthSystem.calculateBloodLossRate();
            return true;
        }

        healthSystem.calculateBloodLossRate();
        return false;
    }

    // Returns true if still within vital requirement
    public boolean decreaseFunctionality(float amount) {
        if(currentFunctionality <= 0)
            return true;

        currentFunctionality -= amount;

        if(currentFunctionality <= 0)
            currentFunctionality = 0;

        if(currentFunctionality < vitalRequirement)
            return false;

        return true;
    }

    // Returns true if the functionality is maxed out
    public boolean increaseFunctionality(float amount) {
        if(currentFunctionality >= maxFunctionality)
            return true;

        currentFunctionality += amount;

        if(currentFunctionality >= maxFunctionality) {
            currentFunctionality = maxFunctionality;
            return true;
        }

        return false;
    }

    public float getCurrentLeak() {
        return currentLeak;
    }

    public float getMaxLeak() {
        return maxLeak;
    }

    public float getCurrentFunctionality() {
        return currentFunctionality;
    }

    public float getMaxFunctionality() {
        return maxFunctionality;
    }

    public float getVitalRequirement() {
        return vitalRequirement;
    }
}
