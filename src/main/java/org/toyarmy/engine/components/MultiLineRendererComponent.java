package org.toyarmy.engine.components;

import org.joml.Math;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.toyarmy.Main;
import org.toyarmy.engine.Component;
import org.toyarmy.engine.Entity;
import org.toyarmy.engine.math.LineSegment;
import org.toyarmy.game.behaviours.CollisionBehaviour;
import org.toyarmy.game.utility.CollisionSegment;
import org.toyarmy.graphics.rendering.shaders.ShaderProgram;
import org.toyarmy.graphics.rendering.utilities.BufferLoader;

import java.nio.FloatBuffer;
import java.util.ArrayList;

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
public class MultiLineRendererComponent extends Component {

    public static final int SOURCE_NONE = 0;
    public static final int SOURCE_COLLISION = 0;

    private static ShaderProgram shaderProgram = new ShaderProgram("shaders/line.vertex", "shaders/line.fragment");
    private TransformComponent transformComponent;

    public int source = SOURCE_NONE;

    public MultiLineRendererComponent(Entity parentEntity, int source) {
        super(parentEntity);

        this.transformComponent = (TransformComponent) parentEntity.getComponent(TransformComponent.class);
        this.source = source;
        updateSource();
    }

    public void updateSource() {
        if(source == SOURCE_COLLISION) {
            lineSegments.clear();
            CollisionBehaviour collisionBehaviour = ((CollisionBehaviour)parentEntity.getComponent(CollisionBehaviour.class));

            if(collisionBehaviour == null)
                return;

            for(CollisionSegment collisionSegment : collisionBehaviour.getCollisionSegments()) {
                lineSegments.add(collisionSegment.getCollisionLineSegment());
            }

            setColor(1.0f, 0.0f, 0.0f);
        }
    }

    private ArrayList<LineSegment> lineSegments = new ArrayList<>();

    private float red = 0.0f, green = 1.0f, blue = 0.0f;
    public void setColor(float red, float green, float blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    @Override
    public void updateComponent(float deltaTime) {

    }

    private void buildMeshAndBind(){
        int vaoId = glGenVertexArrays();
        int positionsId = glGenBuffers();
        int colorsId = glGenBuffers();

        glBindVertexArray(vaoId);

        float[] positions = new float[lineSegments.size() * 4];
        for(int i = 0; i < lineSegments.size(); i++) {
            positions[i*4 + 0] = lineSegments.get(i).point1.x;
            positions[i*4 + 1] = lineSegments.get(i).point1.y;
            positions[i*4 + 2] = lineSegments.get(i).point2.x;
            positions[i*4 + 3] = lineSegments.get(i).point2.y;
        }

        FloatBuffer positionsBuffer = BufferLoader.getFloatBuffer(positions);

        glBindBuffer(GL_ARRAY_BUFFER, positionsId);
        glBufferData(GL_ARRAY_BUFFER, positionsBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);

        float[] colors = new float[lineSegments.size() * 6];
        for(int i = 0; i < lineSegments.size(); i++) {
            colors[i*6 + 0] = red;
            colors[i*6 + 1] = green;
            colors[i*6 + 2] = blue;
            colors[i*6 + 3] = red;
            colors[i*6 + 4] = green;
            colors[i*6 + 5] = blue;
        }
        FloatBuffer colorsBuffer = BufferLoader.getFloatBuffer(colors);

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

        glDrawArrays(GL_LINES,0,lineSegments.size() * 2);
    }
}
