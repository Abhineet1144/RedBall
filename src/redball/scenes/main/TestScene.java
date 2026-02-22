package redball.scenes.main;

import org.joml.Vector3f;
import redball.engine.entity.ECSWorld;
import redball.engine.entity.GameObject;
import redball.engine.entity.components.CameraComponent;
import redball.engine.entity.components.SpriteRenderer;
import redball.engine.entity.components.Transform;
import redball.engine.renderer.BatchRenderer;
import redball.engine.renderer.RenderManager;
import redball.engine.renderer.texture.TextureManager;
import redball.engine.renderer.texture.TextureMap;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class TestScene extends AbstractScene {
    float x = 0;
    GameObject obj;
    GameObject camera = new GameObject("Camera");

    @Override
    public void start() {
        camera.addComponent(new Transform(new Vector3f(0.0f, 0.0f, 0.0f), 0.0f, new Vector3f(250.0f)));
        camera.addComponent(new CameraComponent(1920, 1080));

        obj = ECSWorld.createGameObject("1");
        obj.addComponent(new Transform(new Vector3f(400.0f, 400.0f, -1.0f), 90.0f, new Vector3f(200.0f, 200.0f, 1.0f)));
        obj.addComponent(new SpriteRenderer(TextureManager.getTexture(TextureMap.BACKGROUND)));

        RenderManager.prepare();
    }

    @Override
    public void update(double deltaTime) {
        RenderManager.render(camera);
        obj.update((float) deltaTime);
        Transform t = obj.getComponent(Transform.class);
        t.setXPosition(x += (float) (deltaTime) * 100);
    }
}