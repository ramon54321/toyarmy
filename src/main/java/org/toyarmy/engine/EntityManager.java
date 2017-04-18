package org.toyarmy.engine;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ramon Brand on 4/15/2017.
 * Toy Army - Unit movement and Rendering test application.
 * Code open source and free to use by anyone.
 */
public class EntityManager {

    private Set<Entity> entities = new HashSet<Entity>();

    private EntityManager(){}
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

    public Entity createNewEntity(){
        Entity entity = new Entity();
        EntityManager.getInstance().addEntity(entity);
        return entity;
    }

    public Set<Entity> getEntities(){
        return this.entities;
    }
}
