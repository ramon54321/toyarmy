package org.toyarmy.engine;

import org.toyarmy.engine.components.TransformComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ramon Brand on 4/15/2017.
 */
public class Entity {

    protected List<Component> components = new ArrayList<>();

    public void renderComponents(){
        for (Component component : components) {
            component.renderComponent();
        }
    }

    public void updateComponents(float deltaTime){
        for (Component component : components) {
            component.updateComponent(deltaTime);
        }
    }

    public boolean hasComponent(Class componentClassToFind){
        for (Component component : components) {
            if(componentClassToFind.isInstance(component))
                return true;
        }
        return false;
    }

    public Component getComponent(Class componentClassToGet){
        for (Component component : components) {
            if(componentClassToGet.isInstance(component))
                return component;
        }
        return null;
    }

    public void addComponent(Component component){
        components.add(component);
    }

    public void deleteComponent(Component component){
        components.remove(component);
        component.delete();
    }

    public Entity(float depth) {
        addComponent(new TransformComponent(this, depth));
    }

    public void delete() {
        for (Component component : components) {
            deleteComponent(component);
        }
    }

}
