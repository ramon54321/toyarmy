package org.toyarmy.graphics.rendering;

import org.toyarmy.graphics.rendering.utilities.BufferLoader;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class Quad {

	private final int vaoId;
	private final int vboPositionsId;
	private final int vboUvsId;
	private final int vboIndexId;
	
	private final float[] positionsData;
	private final float[] uvsData;
	private final int[] indexData;

	public static final int QUAD_CENTER = 1;

	public Quad(int origin){
		if(origin == QUAD_CENTER) {
			vaoId = glGenVertexArrays();
			vboPositionsId = glGenBuffers();
			vboUvsId = glGenBuffers();
			vboIndexId = glGenBuffers();
			this.positionsData = new float[] {-0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f};
			this.uvsData = new float[] {0, 1, 1, 1, 1, 0, 0, 0};
			this.indexData = new int[] {0, 1, 2, 2, 3, 0};
			start();
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	public Quad(float[] positionsData, float[] uvsData, int[] indexData){
		vaoId = glGenVertexArrays();
		vboPositionsId = glGenBuffers();
		vboUvsId = glGenBuffers();
		vboIndexId = glGenBuffers();
		this.positionsData = positionsData;
		this.uvsData = uvsData;
		this.indexData = indexData;
		start();
	}

	private void start(){
		glBindVertexArray(vaoId);

		// Setup positions vbo
		FloatBuffer positionsBuffer = BufferLoader.getFloatBuffer(positionsData);
		glBindBuffer(GL_ARRAY_BUFFER, vboPositionsId);
		glBufferData(GL_ARRAY_BUFFER, positionsBuffer, GL_STATIC_DRAW);
		glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);

		// Setup uvs vbo
		FloatBuffer uvsBuffer = BufferLoader.getFloatBuffer(uvsData);
		glBindBuffer(GL_ARRAY_BUFFER, vboUvsId);
		glBufferData(GL_ARRAY_BUFFER, uvsBuffer, GL_STATIC_DRAW);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);

		// Index vbo
		IntBuffer indexBuffer = BufferLoader.getIntBuffer(indexData);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboIndexId);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL_STATIC_DRAW);
	}
	
	public void bind(){
		glBindVertexArray(vaoId);
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
	}
	
	public void unbind(){
		glBindVertexArray(0);
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
	}
	
	public int getIndexLength(){
		return indexData.length;
	}
	
	public int getVertexCount(){
		return positionsData.length;
	}
	
	public int getVaoId() {
		return vaoId;
	}
}
