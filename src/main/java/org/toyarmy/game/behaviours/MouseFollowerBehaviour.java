package org.toyarmy.game.behaviours;

import org.joml.Vector2f;
import org.toyarmy.Main;
import org.toyarmy.engine.Entity;
import org.toyarmy.engine.components.TransformComponent;

import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;

/**
 * Created by Ramon Brand on 4/18/2017.
 * Toy Army - Unit movement and Rendering test application.
 * Code open source and free to use by anyone.
 */
public class MouseFollowerBehaviour extends Behaviour {

    private static long windowId = Main.instance.getDisplay().getWindowId();
    private TransformComponent transformComponent;
    private Vector2f translateVector;

    @Override
    public void start() {
        transformComponent = (TransformComponent) parentEntity.getComponent(TransformComponent.class);
    }

    @Override
    public void updateComponent(float deltaTime) {
        translateVector = getMouseWorldPosition().sub(transformComponent.getPosition());

        if(translateVector.lengthSquared() > 1)
            translateVector.normalize();

        transformComponent.translate(translateVector.mul(deltaTime * 6));
    }

    public static Vector2f getMouseWorldPosition(){
        double[] curX = new double[1];
        double[] curY = new double[1];
        glfwGetCursorPos(windowId, curX, curY);
        Vector2f mousePos = new Vector2f((float) curX[0], (float) curY[0]);
        return Main.instance.getCamera().screenToWorld(mousePos);
    }

    public MouseFollowerBehaviour(Entity parentEntity) {
        super(parentEntity);
    }
}