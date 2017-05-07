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
    private float scale = 1;

    public float getDepth() {
        return depth;
    }

    private  float depth = 0;

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Vector2f getPosition() {
        return position;
    }

    public float getRotation() {
        return rotation;
    }

    public TransformComponent(Entity parentEntity, float depth) {
        super(parentEntity);
        this.depth = depth;
    }

    public void translate(Vector2f vector2f){
        this.position.add(vector2f);
    }

    public void rotate(float rotate){
        rotation += rotate;

        if(rotation >= 360) {
            rotation -= 360;
            return;
        }

        if(rotation < 0) {
            rotation += 360;
            return;
        }
    }

    @Override
    public void updateComponent(float deltaTime) {

    }

    @Override
    public void renderComponent() {

    }
}
