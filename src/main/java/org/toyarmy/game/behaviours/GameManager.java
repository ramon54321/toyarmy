package org.toyarmy.game.behaviours;

import org.joml.Vector2f;
import org.toyarmy.Main;
import org.toyarmy.engine.Entity;
import org.toyarmy.engine.EntityManager;
import org.toyarmy.engine.components.SpriteRendererComponent;
import org.toyarmy.engine.components.TransformComponent;
import org.toyarmy.graphics.rendering.Camera;
import org.toyarmy.graphics.rendering.Texture;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by Ramon Brand on 4/15/2017.
 * Toy Army - Unit movement and Rendering test application.
 * Code open source and free to use by anyone.
 */
public class GameManager extends Behaviour {

    private long windowId = Main.instance.getDisplay().getWindowId();
    private Camera camera = Main.instance.getCamera();

    // Camera
    private float camera_speed = 12f;

    public GameManager(Entity parentEntity) {
        super(parentEntity);
    }

    @Override
    public void start() {
        System.out.println("Game manager started.");

        Texture texture = new Texture("texture.png");

        Entity testEntity = EntityManager.getInstance().createNewEntity();
        testEntity.addComponent(new SpriteRendererComponent(testEntity, texture));
        testEntity.addComponent(new MouseFollowerBehaviour(testEntity));
    }

    @Override
    public void updateComponent(float deltaTime) {

        updateCamera(deltaTime);

    }

    private void updateCamera(float deltaTime){
        if(glfwGetMouseButton(windowId, GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS){
            System.out.println("Clicking");
        }

        if(glfwGetKey(windowId, GLFW_KEY_A) == GLFW_PRESS){
            camera.translate(-camera_speed * deltaTime, 0);
        } else if(glfwGetKey(windowId, GLFW_KEY_D) == GLFW_PRESS){
            camera.translate(camera_speed * deltaTime, 0);
        }
        if(glfwGetKey(windowId, GLFW_KEY_W) == GLFW_PRESS){
            camera.translate(0, camera_speed * deltaTime);
        } else if(glfwGetKey(windowId, GLFW_KEY_S) == GLFW_PRESS){
            camera.translate(0, -camera_speed * deltaTime);
        }
    }
}
