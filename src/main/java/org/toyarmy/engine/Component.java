package org.toyarmy.engine;

/**
 * Created by Ramon Brand on 4/15/2017.
 */
public abstract class Component {

    protected Entity parentEntity;

    public Component(Entity parentEntity){
        this.parentEntity = parentEntity;
    }

    public abstract void updateComponent(float deltaTime);

    public abstract void renderComponent();

    public void delete(){

    }

}
