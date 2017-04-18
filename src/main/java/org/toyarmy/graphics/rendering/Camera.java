package org.toyarmy.graphics.rendering;

import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Camera {
	
	private Vector3f position = new Vector3f();
	
	private Matrix4f projectionMatrix = new Matrix4f();

    private float aspectRatio = 1280f/720f;
    private float zoom = 10f;

	public Camera(){
        this.projectionMatrix = new Matrix4f().ortho2D(-aspectRatio * zoom, aspectRatio * zoom, -1 * zoom, 1 * zoom);
		
		this.position.x = 0;
		this.position.y = 0;
		this.position.z = 2;
	}

    public Vector2f screenToWorld(Vector2f screenPosition){
        float x = (screenPosition.x - (1280/2)) / 1280 * zoom*2*aspectRatio + position.x;
        float y = ((720 - screenPosition.y) - (720/2)) / 720 * zoom*2 + position.y;
        return new Vector2f(x, y);
    }
	
	public Vector3f getPosition(){
		return this.position;
	}

    public void translate(float x, float y){
        this.position.x += x;
        this.position.y += y;
    }
	
	public Matrix4f getProjectionMatrix(){
		return this.projectionMatrix;
	}
	
	public Matrix4f getViewMatrix(){
		return new Matrix4f().identity();
	}
}
