package org.toyarmy.game.behaviours;

import org.toyarmy.engine.Component;
import org.toyarmy.engine.Entity;

/**
 * Created by Ramon Brand on 4/15/2017.
 */
public abstract class Behaviour extends Component {

    public Behaviour(Entity parentEntity){
        super(parentEntity);
        start();
    }

    public void start() {

    }

    @Override
    public void updateComponent(float deltaTime) {

    }

    @Override
    public void renderComponent() {

    }
}
