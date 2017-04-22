package org.toyarmy.engine;

import org.toyarmy.engine.components.TransformComponent;

import java.util.Comparator;

/**
 * Created by Ramon Brand on 4/22/2017.
 * Toy Army - Unit movement and Rendering test application.
 * Code open source and free to use by anyone.
 */
public class EntityDepthComparator implements Comparator<Entity> {

    @Override
    public int compare(Entity e1, Entity e2) {
        float depth1 = ((TransformComponent) e1.getComponent(TransformComponent.class)).getDepth();
        float depth2 = ((TransformComponent) e2.getComponent(TransformComponent.class)).getDepth();

        if(depth1 > depth2)
            return 1;
        else
            return -1;
    }
}