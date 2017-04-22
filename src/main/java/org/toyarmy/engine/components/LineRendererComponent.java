package org.toyarmy.engine.components;

import org.joml.Math;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.toyarmy.Main;
import org.toyarmy.engine.Component;
import org.toyarmy.engine.Entity;
import org.toyarmy.graphics.rendering.shaders.ShaderProgram;
import org.toyarmy.graphics.rendering.utilities.BufferLoader;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/**
 * Created by Ramon Brand on 4/15/2017.
 * Toy Army - Unit movement and Rendering test application.
 * Code open source and free to use by anyone.
 */
public class LineRendererComponent extends Component {

    private static ShaderProgram shaderProgram = new ShaderProgram("shaders/line.vertex", "shaders/line.fragment");
    private TransformComponent transformComponent;

    public LineRendererComponent(Entity parentEntity) {
        super(parentEntity);

        this.transformComponent = (TransformComponent) parentEntity.getComponent(TransformComponent.class);
    }

    private float red = 0.0f, green = 1.0f, blue = 0.0f;
    public void setColor(float red, float green, float blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    private float x1 = 0.0f, y1 = 0.0f, x2 = 1.0f, y2 = 1.0f;
    public void setLine(float x1, float y1, float x2, float y2){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
    public void setPoint1(float x1, float y1){
        this.x1 = x1;
        this.y1 = y1;
    }
    public void setPoint2(float x2, float y2){
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void updateComponent(float deltaTime) {

    }

    private void buildMeshAndBind(){
        int vaoId = glGenVertexArrays();
        int positionsId = glGenBuffers();
        int colorsId = glGenBuffers();

        glBindVertexArray(vaoId);

        FloatBuffer positionsBuffer = BufferLoader.getFloatBuffer(new float[]{
                x1, y1,
                x2, y2
        });
        glBindBuffer(GL_ARRAY_BUFFER, positionsId);
        glBufferData(GL_ARRAY_BUFFER, positionsBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);

        FloatBuffer colorsBuffer = BufferLoader.getFloatBuffer(new float[]{
                red, green, blue,
                red, green, blue
        });
        glBindBuffer(GL_ARRAY_BUFFER, colorsId);
        glBufferData(GL_ARRAY_BUFFER, colorsBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);

        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
    }

    @Override
    public void renderComponent() {
        buildMeshAndBind();
        shaderProgram.bind();

        float[] projectionBuffer = new float[16];
        Main.instance.getCamera().getProjectionMatrix().get(projectionBuffer);
        shaderProgram.setUniform("projection", projectionBuffer);

        Vector3f cameraPosition = Main.instance.getCamera().getPosition();
        Vector2f entityPosition = transformComponent.getPosition();
        float entityRotation = transformComponent.getRotation();
        float[] cameraBuffer = new float[16];
        Main.instance.getCamera().getViewMatrix().translate(entityPosition.x, entityPosition.y, 0).translate(-cameraPosition.x, -cameraPosition.y, cameraPosition.z).rotateZ(entityRotation / -(180f / (float)Math.PI)).get(cameraBuffer);
        shaderProgram.setUniform("camera", cameraBuffer);

        glDrawArrays(GL_LINES,0,2);
    }
}
