package org.toyarmy.engine;

import org.toyarmy.engine.components.TransformComponent;

import java.util.*;

/**
 * Created by Ramon Brand on 4/15/2017.
 * Toy Army - Unit movement and Rendering test application.
 * Code open source and free to use by anyone.
 */
public class EntityManager {

    private static final int maxEntites = 2048;

    private Set<Entity> entities = new TreeSet<Entity>(new EntityDepthComparator());
    private Entity[] entitiesArray = new Entity[maxEntites];
    private Stack<Integer> freeEntities = new Stack<>();

    private EntityManager(){
        for(int i = maxEntites-1; i >= 0; i--) {
            freeEntities.push(i);
        }
    }
    public static EntityManager instance = null;
    public static EntityManager getInstance(){
        if(instance == null)
            instance = new EntityManager();
        return instance;
    }

    public void update(float deltaTime){
        for (Entity entity : entities) {
            entity.updateComponents(deltaTime);
        }
    }

    private void addEntity(Entity entity){
        this.entities.add(entity);
    }

    public Entity createNewEntity(float depth){
        int newEntityId = freeEntities.pop();
        Entity entity = new Entity(newEntityId, depth);
        EntityManager.getInstance().addEntity(entity);
        entitiesArray[newEntityId] = entity;
        return entity;
    }

    public Entity getEntityById(int id) {
        return entitiesArray[id];
    }

    public Set<Entity> getEntities(){
        return this.entities;
    }
}
