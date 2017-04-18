package org.toyarmy.graphics.rendering;

import org.toyarmy.engine.Entity;
import org.toyarmy.engine.EntityManager;
import org.toyarmy.graphics.Display;

import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created by Ramon Brand on 4/15/2017.
 */
public class RenderManager {

    private Display display;
    private Camera camera;
    private EntityManager entityManager;

    private RenderManager(Display display, Camera camera, EntityManager entityManager){
        this.display = display;
        this.camera = camera;
        this.entityManager = entityManager;
    }
    public static RenderManager instance = null;
    public static RenderManager getInstance(Display display, Camera camera, EntityManager entityManager){
        if(instance == null)
            instance = new RenderManager(display, camera, entityManager);
        return instance;
    }
    public static RenderManager getInstance(){
        return instance;
    }

    public void render(){
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        for (Entity entitiy : entityManager.getEntities()) {
            entitiy.renderComponents();
        }

        glfwSwapBuffers(display.getWindowId());
    }

    public Camera getCamera(){
        return camera;
    }
}
