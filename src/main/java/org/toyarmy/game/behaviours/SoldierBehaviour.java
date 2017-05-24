package org.toyarmy.game.behaviours;

import org.joml.Vector2f;
import org.toyarmy.debug.Controller;
import org.toyarmy.engine.Entity;
import org.toyarmy.engine.components.TransformComponent;
import org.toyarmy.engine.math.IntersectData;
import org.toyarmy.engine.math.IntersectDataDistanceComparator;
import org.toyarmy.engine.math.LineSegment;
import org.toyarmy.engine.math.VectorMath;
import org.toyarmy.game.ammunition.Ammunition;
import org.toyarmy.game.items.armour.BodyArmour;
import org.toyarmy.game.items.magazines.Magazine;
import org.toyarmy.game.items.weapons.Weapon;
import org.toyarmy.game.systems.health.HealthSystem;
import org.toyarmy.game.systems.movement.MovementSystem;
import org.toyarmy.game.systems.optics.OpticsSystem;
import org.toyarmy.game.utility.BallisticData;
import org.toyarmy.game.utility.CollisionSegment;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Ramon Brand on 4/19/2017.
 * Toy Army - Unit movement and Rendering test application.
 * Code open source and free to use by anyone.
 */
public class SoldierBehaviour extends Behaviour {

    private TransformComponent transformComponent;

    private OpticsSystem opticsSystem;
    private MovementSystem movementSystem;
    private HealthSystem healthSystem;
    private Weapon weaponPrimary;

    private int selectedWeapon = 0;

    @Override
    public void start() {
        super.start();

        this.transformComponent = (TransformComponent) parentEntity.getComponent(TransformComponent.class);

        // Setup Soldier
        opticsSystem = new OpticsSystem(parentEntity, 12, 140);
        movementSystem = new MovementSystem(transformComponent);
        healthSystem = new HealthSystem();
        Magazine mag = Magazine.getNewMagazine("Stanag30");
        weaponPrimary = Weapon.getNewWeapon("M16A4");
        weaponPrimary.setMagazine(mag);
    }

    @Override
    public void updateComponent(float deltaTime) {
        super.updateComponent(deltaTime);
        opticsSystem.update(deltaTime);
        movementSystem.update(deltaTime);
        healthSystem.update(deltaTime);
    }

    // OpticsSystem


    // HealthSystem
    public void takeHit(BallisticData ballisticData) {
        System.out.println("Taking hit: Velocity = " + ballisticData.velocity + " , Energy = " + ballisticData.energy + " , Ammunition Mass = " + ballisticData.ammunition.getBulletMass());

        healthSystem.takeHit(ballisticData);
    }

    // Shooting
    public boolean shootTarget(SoldierBehaviour soldierBehaviour) {

        if(!weaponPrimary.getMagazine().takeRound())
            return false;

        BallisticData ballisticData = getBulletBallisticsTo(soldierBehaviour.transformComponent.getPosition());
        System.out.println("Magazine: " + weaponPrimary.getMagazine().getCurrentCount() + " / " + weaponPrimary.getMagazine().getMaxCount());
        soldierBehaviour.takeHit(ballisticData);
        return true;
    }

    private BallisticData getBulletBallisticsTo(Vector2f target) {
        Set<IntersectData> collisions = getCollisionsTo(target);

        Magazine magazine = weaponPrimary.getMagazine();
        Ammunition ammunition = Ammunition.ammunitionMap.get(magazine.getAmmunitionType());

        // Barrel bullet condition
        float bulletVelocity = weaponPrimary.getBarrelVelocityBoost() * ammunition.getStandardMuzzleVelocity();
        float bulletEnergy;

        System.out.println("V START: " + bulletVelocity);

        float lastDistance = 0;
        for(IntersectData intersectData : collisions) {

            float sectionDistance = intersectData.distanceFromOrigin - lastDistance;
            lastDistance += sectionDistance;

            // Drag velocity reduction
            bulletVelocity = bulletVelocity * (float) Math.pow(ammunition.getVelocityRetention(),sectionDistance * GameManager.RANGE_DIVISIOR);
            bulletEnergy = (ammunition.getBulletMass() * bulletVelocity * bulletVelocity) / 2f;
            System.out.println("COL VIN: " + bulletVelocity);

            float effectiveSurfaceArmour = intersectData.collisionSegment.getCollisionMaterial().armour / (ammunition.getArmourPiercing() * ammunition.getArmourPiercing());

            bulletEnergy = Math.max(0, bulletEnergy - effectiveSurfaceArmour);

            bulletVelocity = (float) Math.sqrt((2 * bulletEnergy) / ammunition.getBulletMass());
            System.out.println("COL VOUT: " + bulletVelocity);
        }

        float overallDistanceToTarget = VectorMath.distanceBetween(transformComponent.getPosition(), target);

        float remainingDistanceToTarget = overallDistanceToTarget - lastDistance;

        bulletVelocity = bulletVelocity * (float) Math.pow(ammunition.getVelocityRetention(), remainingDistanceToTarget * GameManager.RANGE_DIVISIOR);
        bulletEnergy = (ammunition.getBulletMass() * bulletVelocity * bulletVelocity) / 2f;

        System.out.println("COL VEND: " + bulletVelocity);
        return new BallisticData(bulletVelocity, bulletEnergy, ammunition);
    }

    private Set<IntersectData> getCollisionsTo(Vector2f target) {
        Vector2f localPosition = transformComponent.getPosition();

        // Calculate Segment to Target
        LineSegment hereToTarget = new LineSegment(localPosition, target);

        // Find nearby objects
        ArrayList<CollisionBehaviour> nearbyCollisionComponents = new ArrayList<>();
        float distanceToTarget = hereToTarget.getLength() * 1.5f;
        for(CollisionBehaviour collisionBehaviour : GameManager.instance.collisionEntitiesCollisionBehaviours) {
            TransformComponent collisionObjectTransformComponent = collisionBehaviour.getTransformComponent();
            if(VectorMath.distanceBetween(localPosition, collisionObjectTransformComponent.getPosition()) < distanceToTarget)
                nearbyCollisionComponents.add(collisionBehaviour);
        }

        // Check for intersects
        Set<IntersectData> intersectDataSet = new TreeSet<IntersectData>(new IntersectDataDistanceComparator());
        for(CollisionBehaviour collisionBehaviour : nearbyCollisionComponents) {
            for(CollisionSegment collisionSegment : collisionBehaviour.getCollisionSegments()) {
                LineSegment lineSegment = collisionSegment.getCollisionLineSegmentRotated();
                Vector2f collisionPosition = collisionBehaviour.getTransformComponent().getPosition();
                Vector2f point1 = new Vector2f(lineSegment.point1).add(collisionPosition);
                Vector2f point2 = new Vector2f(lineSegment.point2).add(collisionPosition);
                Vector2f intersectionPoint = LineSegment.findIntersectionOf(hereToTarget, new LineSegment(point1, point2));
                if(intersectionPoint != null) {
                    IntersectData intersectData = new IntersectData(intersectionPoint, VectorMath.distanceBetween(localPosition, intersectionPoint), collisionSegment);
                    intersectDataSet.add(intersectData);
                }
            }
        }
        return intersectDataSet;
    }

    // Getters
    public HealthSystem getHealthSystem() {
        return healthSystem;
    }

    public MovementSystem getMovementSystem() {
        return movementSystem;
    }

    public OpticsSystem getOpticsSystem() {
        return opticsSystem;
    }

    public Weapon getWeaponPrimary() {
        return weaponPrimary;
    }

    public TransformComponent getTransformComponent() {
        return transformComponent;
    }

    public SoldierBehaviour(Entity parentEntity) {
        super(parentEntity);
    }
}
