package org.toyarmy.graphics;

import org.joml.Vector2f;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Display {
	
	private final int WIDTH;
	private final int HEIGHT;
	
	public Display(int width, int height, String title){
		WIDTH = width;
		HEIGHT = height;
		start(title);
	}
	
	private long windowId;
	
	public int getWIDTH() {
		return WIDTH;
	}

	public int getHEIGHT() {
		return HEIGHT;
	}

	public long getWindowId() {
		return windowId;
	}

	private void start(String title){
		glfwInit();
		
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_STENCIL_BITS, 4);
        glfwWindowHint(GLFW_SAMPLES, 4);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
        
        windowId = glfwCreateWindow(WIDTH, HEIGHT, title, NULL, NULL);
        
        glfwMakeContextCurrent(windowId);
        
        glfwSwapInterval(0);
        
        glfwShowWindow(windowId);
        
        GL.createCapabilities();
        
        glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
        

        glEnable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        
        glDepthFunc(GL_LEQUAL);
        glEnable(GL_CULL_FACE);
        
        glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
	}
}
