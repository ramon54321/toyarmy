package org.toyarmy.engine.components;

import org.joml.Vector2f;
import org.toyarmy.engine.Component;
import org.toyarmy.engine.Entity;

/**
 * Created by Ramon Brand on 4/15/2017.
 */
public class TransformComponent extends Component {

    private Vector2f position = new Vector2f(0,0);
    private float rotation = 0;

    public Vector2f getPosition() {
        return position;
    }

    public float getRotation() {
        return rotation;
    }

    public TransformComponent(Entity parentEntity) {
        super(parentEntity);
    }

    public void translate(Vector2f vector2f){
        this.position.add(vector2f);
    }

    public void rotate(float rotate){
        rotation += rotate;
    }

    @Override
    public void updateComponent(float deltaTime) {

    }

    @Override
    public void renderComponent() {

    }
}
