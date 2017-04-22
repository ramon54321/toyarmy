package org.toyarmy.graphics.rendering;

import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.toyarmy.Main;

public class Camera {
	
	private Vector3f position = new Vector3f();
	
	private Matrix4f projectionMatrix = new Matrix4f();

    private float aspectRatio;
    private float zoom = 16f;

	public Camera(){
		this.aspectRatio = Main.instance.getDisplay().getWIDTH() / Main.instance.getDisplay().getHEIGHT();
		this.projectionMatrix = new Matrix4f().ortho(-aspectRatio * zoom, aspectRatio * zoom, -1 * zoom, 1 * zoom, 0, 100);
		this.position.x = 0;
		this.position.y = 0;
		this.position.z = 0;
	}

    public Vector2f screenToWorld(Vector2f screenPosition){
        float x = (screenPosition.x - (Main.instance.getDisplay().getWIDTH()/2)) / Main.instance.getDisplay().getWIDTH() * zoom*2*aspectRatio + position.x;
        float y = ((Main.instance.getDisplay().getHEIGHT() - screenPosition.y) - (Main.instance.getDisplay().getHEIGHT()/2)) / Main.instance.getDisplay().getHEIGHT() * zoom*2 + position.y;
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
