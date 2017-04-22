package org.toyarmy.engine;

import org.toyarmy.engine.components.TransformComponent;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Ramon Brand on 4/15/2017.
 * Toy Army - Unit movement and Rendering test application.
 * Code open source and free to use by anyone.
 */
public class EntityManager {

    private Set<Entity> entities = new TreeSet<Entity>(new EntityDepthComparator());

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

    public Entity createNewEntity(float depth){
        Entity entity = new Entity(depth);
        EntityManager.getInstance().addEntity(entity);
        return entity;
    }

    public Set<Entity> getEntities(){
        return this.entities;
    }
}
