package org.toyarmy.engine;

import org.joml.Vector2f;
import org.toyarmy.Main;

import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;

/**
 * Created by Ramon Brand on 5/6/2017.
 * Toy Army - Unit movement and Rendering test application.
 * Code open source and free to use by anyone.
 */
public class MousePosition {

    public static Vector2f getMouseWorldPosition(){
        double[] curX = new double[1];
        double[] curY = new double[1];
        glfwGetCursorPos(Main.instance.getDisplay().getWindowId(), curX, curY);
        Vector2f mousePos = new Vector2f((float) curX[0], (float) curY[0]);
        return Main.instance.getCamera().screenToWorld(mousePos);
    }

}
