package redball.scenes.levels;

import org.joml.Vector3f;

import redball.engine.core.PhysicsSystem;
import redball.engine.entity.ECSWorld;
import redball.engine.entity.GameObject;
import redball.engine.entity.components.CameraComponent;
import redball.engine.entity.components.Rigidbody;
import redball.engine.entity.components.SpriteRenderer;
import redball.engine.entity.components.Transform;
import redball.engine.renderer.RenderManager;
import redball.engine.renderer.texture.TextureManager;
import redball.engine.renderer.texture.TextureMap;
import redball.scenes.main.AbstractScene;

public class Level1 extends AbstractScene {

    GameObject camera = new GameObject("Camera");
    GameObject bg = ECSWorld.createGameObject("Background");
    float x;

    @Override
    public void start() {
        PhysicsSystem.init();

        camera.addComponent(new Transform(new Vector3f(0.0f, 0.0f, 0.0f), 0.0f, new Vector3f(250.0f)));
        camera.addComponent(new CameraComponent(1920, 1080));

        bg.addComponent(new Transform(new Vector3f(1920f / 2, 1080f / 2, -1f), 0f, new Vector3f(1920, 1080, 1)));
        bg.addComponent(new SpriteRenderer(TextureManager.getTexture(TextureMap.BACKGROUND)));

        RenderManager.prepare();
    }

    @Override
    public void update(float deltaTime) {
        RenderManager.render(camera);
        bg.update(deltaTime);
        bg.getComponent(Transform.class).setXPosition(1920f / 2);
    }
}
