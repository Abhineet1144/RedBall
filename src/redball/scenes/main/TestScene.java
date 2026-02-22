package redball.scenes.main;

import org.joml.Vector3f;

import redball.engine.core.physics.PhysicsSystem;
import redball.engine.entity.ECSWorld;
import redball.engine.entity.GameObject;
import redball.engine.entity.components.CameraComponent;
import redball.engine.entity.components.Rigidbody;
import redball.engine.entity.components.SpriteRenderer;
import redball.engine.entity.components.Transform;
import redball.engine.renderer.RenderManager;
import redball.engine.renderer.texture.TextureManager;
import redball.engine.renderer.texture.TextureMap;

public class TestScene extends AbstractScene {
    float x = 200;
    GameObject obj;
    GameObject camera = new GameObject("Camera");

    @Override
    public void start() {
        PhysicsSystem.init();
        camera.addComponent(new Transform(new Vector3f(0.0f, 0.0f, 0.0f), 0.0f, new Vector3f(250.0f)));
        camera.addComponent(new CameraComponent(1920, 1080));

        obj = ECSWorld.createGameObject("1");
        obj.addComponent(new Transform(new Vector3f(400.0f, 400.0f, -1.0f), 90.0f, new Vector3f(200.0f, 200.0f, 1.0f)));
        obj.addComponent(new Rigidbody());
        obj.addComponent(new SpriteRenderer(TextureManager.getTexture(TextureMap.BACKGROUND)));

        RenderManager.prepare();
        obj.start();
    }

    @Override
    public void update(float deltaTime) {
        RenderManager.render(camera);
        obj.update(deltaTime);
        Transform t = obj.getComponent(Transform.class);
        t.setXPosition(x += (float) (deltaTime) * 100);
    }
}