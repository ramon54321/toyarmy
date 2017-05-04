package org.toyarmy.game.behaviours;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.toyarmy.Main;
import org.toyarmy.engine.Entity;
import org.toyarmy.engine.EntityManager;
import org.toyarmy.engine.components.LineRendererComponent;
import org.toyarmy.engine.components.MultiLineRendererComponent;
import org.toyarmy.engine.components.SpriteRendererComponent;
import org.toyarmy.engine.components.TransformComponent;
import org.toyarmy.engine.math.LineSegment;
import org.toyarmy.game.utility.CollisionMaterial;
import org.toyarmy.game.utility.CollisionSegment;
import org.toyarmy.graphics.rendering.Camera;
import org.toyarmy.graphics.rendering.Texture;

import javax.sound.sampled.Line;
import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by Ramon Brand on 4/15/2017.
 * Toy Army - Unit movement and Rendering test application.
 * Code open source and free to use by anyone.
 */
public class GameManager extends Behaviour {

    private long windowId = Main.instance.getDisplay().getWindowId();
    private Camera camera = Main.instance.getCamera();

    public static GameManager instance;

    public static final float RANGE_DIVISIOR = 10;

    // Camera
    private float camera_speed = 12f;

    // Component Lists
    public ArrayList<CollisionBehaviour> collisionEntitiesCollisionBehaviours;

    public GameManager(Entity parentEntity) {
        super(parentEntity);
    }

    private void init(){
        instance = this;
        collisionEntitiesCollisionBehaviours = new ArrayList<>();
    }

    Entity testEntity;
    Entity testEntity2;
    Entity colEntity;
    LineRendererComponent mainLineRenderer;

    @Override
    public void start() {
        init();
        System.out.println("Game manager started.");

        // General Line Renderer
        mainLineRenderer = new LineRendererComponent(parentEntity);
        parentEntity.addComponent(mainLineRenderer);

        buildEntitiesFromFile();

        Vector2f mousePosition = MouseFollowerBehaviour.getMouseWorldPosition();

        GameManager.instance.mainLineRenderer.setPoint1(0, 0);
        Vector2f targetPos = ((TransformComponent)testEntity2.getComponent(TransformComponent.class)).getPosition();
        GameManager.instance.mainLineRenderer.setPoint2(targetPos.x, targetPos.y);

        ((SoldierBehaviour)testEntity.getComponent(SoldierBehaviour.class)).shootTarget((SoldierBehaviour) testEntity2.getComponent(SoldierBehaviour.class));
    }

    private void buildEntitiesFromFile(){
        // TODO: Read from file!



        // Soldier
        testEntity = EntityManager.getInstance().createNewEntity(-1);
        testEntity.addComponent(new SpriteRendererComponent(testEntity, new Texture("unit.png")));
        //testEntity.addComponent(new MouseFollowerBehaviour(testEntity));
        testEntity.addComponent(new SoldierBehaviour(testEntity));

        testEntity2 = EntityManager.getInstance().createNewEntity(-1);
        testEntity2.addComponent(new SpriteRendererComponent(testEntity2, new Texture("unit.png")));
        //testEntity2.addComponent(new MouseFollowerBehaviour(testEntity2));
        testEntity2.addComponent(new SoldierBehaviour(testEntity2));
        ((TransformComponent)testEntity2.getComponent(TransformComponent.class)).translate(new Vector2f(2, 7));

        // Collider
        colEntity = EntityManager.getInstance().createNewEntity(-2);
        colEntity.addComponent(new SpriteRendererComponent(colEntity, new Texture("house1.png")));
        colEntity.addComponent(new CollisionBehaviour(colEntity));
        ((CollisionBehaviour)colEntity.getComponent(CollisionBehaviour.class)).getCollisionSegments().add(new CollisionSegment(new LineSegment(new Vector2f(-2.0f, 5.0f), new Vector2f(2, 5.0f)), new CollisionMaterial()));
        ((CollisionBehaviour)colEntity.getComponent(CollisionBehaviour.class)).getCollisionSegments().add(new CollisionSegment(new LineSegment(new Vector2f(-2.0f, 15.0f), new Vector2f(2, 15.0f)), new CollisionMaterial()));
        ((TransformComponent)colEntity.getComponent(TransformComponent.class)).rotate(0f);
        ((TransformComponent)colEntity.getComponent(TransformComponent.class)).setScale(1f);
        colEntity.addComponent(new MultiLineRendererComponent(colEntity, MultiLineRendererComponent.SOURCE_COLLISION));

    }

    @Override
    public void delete() {
        instance = null;
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

        if(glfwGetKey(windowId, GLFW_KEY_Q) == GLFW_PRESS){
            ((TransformComponent)testEntity.getComponent(TransformComponent.class)).rotate(-90f * deltaTime);
        } else if(glfwGetKey(windowId, GLFW_KEY_E) == GLFW_PRESS){
            ((TransformComponent)testEntity.getComponent(TransformComponent.class)).rotate(90f * deltaTime);
        }
    }
}
