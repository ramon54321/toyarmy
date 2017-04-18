package org.toyarmy.graphics.rendering.shaders;

import org.toyarmy.graphics.rendering.utilities.FileLoader;

import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram {
	
	private final int shaderProgramId;
	private int vertexShaderId;
	private int fragmentShaderId;
	
	public ShaderProgram(String filePathVertex, String filePathFragment){
		shaderProgramId = glCreateProgram();
		start(filePathVertex, filePathFragment);
	}
	
	private void start(String filePathVertex, String filePathFragment){
		setVertexShader(filePathVertex);
		setFragmentShader(filePathFragment);
		linkProgram();
	}
	
	private void setVertexShader(String filePath){
		vertexShaderId = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertexShaderId, FileLoader.loadFileToString(filePath));
		glCompileShader(vertexShaderId);
		glAttachShader(shaderProgramId, vertexShaderId);
	}
	
	private void setFragmentShader(String filePath){
		fragmentShaderId = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragmentShaderId, FileLoader.loadFileToString(filePath));
		glCompileShader(fragmentShaderId);
		glAttachShader(shaderProgramId, fragmentShaderId);
	}
	
	private void linkProgram(){
		glLinkProgram(shaderProgramId);
        glValidateProgram(shaderProgramId);
        if (glGetProgrami(shaderProgramId, GL_VALIDATE_STATUS) == 0) {
            System.err.println("Warning validating Shader code: " + glGetProgramInfoLog(shaderProgramId, 1024));
        }
	}
	
	public void setUniform(String name, float[] value){
		int location = glGetUniformLocation(shaderProgramId, name);
		glUniformMatrix4fv(location, false, value);
	}

	public int getShaderProgramId() {
		return shaderProgramId;
	}

	public int getVertexShaderId() {
		return vertexShaderId;
	}

	public int getFragmentShaderId() {
		return fragmentShaderId;
	}
	
	public void bind(){
		glUseProgram(shaderProgramId);
	}
}
