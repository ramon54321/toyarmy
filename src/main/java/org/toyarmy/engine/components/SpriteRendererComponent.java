package org.toyarmy.engine.components;

import org.joml.Math;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.toyarmy.Main;
import org.toyarmy.engine.Component;
import org.toyarmy.engine.Entity;
import org.toyarmy.graphics.rendering.Quad;
import org.toyarmy.graphics.rendering.Texture;
import org.toyarmy.graphics.rendering.shaders.ShaderProgram;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by Ramon Brand on 4/15/2017.
 * Toy Army - Unit movement and Rendering test application.
 * Code open source and free to use by anyone.
 */
public class SpriteRendererComponent extends Component {

    private static Quad quad = new Quad(Quad.QUAD_CENTER);
    private static ShaderProgram shaderProgram = new ShaderProgram("shaders/quad.vertex", "shaders/quad.fragment");
    private Texture texture;
    private TransformComponent transformComponent;

    public SpriteRendererComponent(Entity parentEntity, Texture texture) {
        super(parentEntity);
        this.texture = texture;

        this.transformComponent = (TransformComponent) parentEntity.getComponent(TransformComponent.class);
    }

    @Override
    public void updateComponent(float deltaTime) {

    }

    @Override
    public void renderComponent() {
        quad.bind();
        shaderProgram.bind();
        texture.bind();

        float[] projectionBuffer = new float[16];
        Main.instance.getCamera().getProjectionMatrix().get(projectionBuffer);
        shaderProgram.setUniform("projection", projectionBuffer);

        Vector3f cameraPosition = Main.instance.getCamera().getPosition();
        Vector2f entityPosition = transformComponent.getPosition();
        float entityRotation = transformComponent.getRotation();
        float entityScale = transformComponent.getScale();
        float[] cameraBuffer = new float[16];
        Main.instance.getCamera().getViewMatrix().translate(entityPosition.x, entityPosition.y, 0).translate(-cameraPosition.x, -cameraPosition.y, -cameraPosition.z).rotateZ(entityRotation / -(180f / (float)Math.PI)).scale(entityScale).get(cameraBuffer);
        shaderProgram.setUniform("camera", cameraBuffer);

        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);

        quad.unbind();
        shaderProgram.unbind();
        texture.unbind();
    }
}
