package org.toyarmy.engine.components;

import org.joml.Math;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.toyarmy.Main;
import org.toyarmy.engine.Component;
import org.toyarmy.engine.Entity;
import org.toyarmy.engine.math.LineSegment;
import org.toyarmy.engine.math.VectorMath;
import org.toyarmy.game.behaviours.CollisionBehaviour;
import org.toyarmy.game.behaviours.SoldierBehaviour;
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
    public static final int SOURCE_COLLISION = 1;
    public static final int SOURCE_FIELDOFVIEW = 2;

    private static ShaderProgram shaderProgram = new ShaderProgram("shaders/line.vertex", "shaders/line.fragment");
    private TransformComponent transformComponent;

    public int source = SOURCE_NONE;

    public MultiLineRendererComponent(Entity parentEntity, int source) {
        super(parentEntity);

        this.transformComponent = (TransformComponent) parentEntity.getComponent(TransformComponent.class);
        this.source = source;
        updateSource();
    }

    // Only runs on start
    public void updateSource() {
        if(source == SOURCE_COLLISION) {
            lineSegments.clear();
            CollisionBehaviour collisionBehaviour = ((CollisionBehaviour)parentEntity.getComponent(CollisionBehaviour.class));

            if(collisionBehaviour == null)
                return;

            for(CollisionSegment collisionSegment : collisionBehaviour.getCollisionSegments()) {
                lineSegments.add(collisionSegment.getCollisionLineSegment());
            }

            setColor(0.6f, 0.2f, 0.2f, 1.0f);
        } else if(source == SOURCE_FIELDOFVIEW) {
            lineSegments.clear();
            SoldierBehaviour soldierBehaviour = ((SoldierBehaviour)parentEntity.getComponent(SoldierBehaviour.class));

            if(soldierBehaviour == null)
                return;

            float radius = soldierBehaviour.getOpticsSystem().getRange();
            float fieldOfView = soldierBehaviour.getOpticsSystem().getFieldOfView();
            float theta = (float) ((fieldOfView / 2) / (180 / Math.PI));

            float leftTipX = (float) Math.sin(theta) * -radius;
            float rightTipX = (float) Math.sin(theta) * radius;
            float tipY = (float) Math.cos(theta) * radius;

            Vector2f rootPos = new Vector2f();
            Vector2f leftTip = new Vector2f().add(leftTipX, tipY);
            Vector2f rightTip = new Vector2f().add(rightTipX, tipY);

            lineSegments.add(new LineSegment(rootPos, leftTip));
            lineSegments.add(new LineSegment(rootPos, rightTip));

            int divisions = 64;

            // Draw arc
            Vector2f[] arcPoints = new Vector2f[2 + (divisions-1)];
            arcPoints[0] = leftTip;
            arcPoints[arcPoints.length-1] = rightTip;

            float divisionAngle = fieldOfView / divisions;

            Vector2f position = transformComponent.getPosition();
            Vector2f localArm = new Vector2f(leftTip).sub(position);
            for(int i = 1; i < divisions; i++) {
                Vector2f rotatedArm = VectorMath.getRotatedVector(localArm, divisionAngle * i);
                arcPoints[i] = rotatedArm.add(position);
            }

            for(int i = 0; i < arcPoints.length-1; i++) {
                lineSegments.add(new LineSegment(arcPoints[i], arcPoints[i+1]));
            }

            setColor(0.2f, 0.9f, 0.47f, 0.2f);
        }
    }

    private ArrayList<LineSegment> lineSegments = new ArrayList<>();

    private float red = 0.0f, green = 1.0f, blue = 0.0f, alpha = 1.0f;
    public void setColor(float red, float green, float blue, float alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
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

        float[] colors = new float[lineSegments.size() * 8];
        for(int i = 0; i < lineSegments.size(); i++) {
            colors[i*8 + 0] = red;
            colors[i*8 + 1] = green;
            colors[i*8 + 2] = blue;
            colors[i*8 + 3] = alpha;
            colors[i*8 + 4] = red;
            colors[i*8 + 5] = green;
            colors[i*8 + 6] = blue;
            colors[i*8 + 7] = alpha;
        }
        FloatBuffer colorsBuffer = BufferLoader.getFloatBuffer(colors);

        glBindBuffer(GL_ARRAY_BUFFER, colorsId);
        glBufferData(GL_ARRAY_BUFFER, colorsBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(1, 4, GL_FLOAT, false, 0, 0);

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
