package redball.scenes.main;

import org.dyn4j.geometry.MassType;
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
    GameObject obj1;
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

        obj1 = ECSWorld.createGameObject("1");
        obj1.addComponent(new Transform(new Vector3f(100.0f, 100.0f, -1.0f), 0.0f, new Vector3f(1080.0f, 20.0f, 1.0f)));
        obj1.addComponent(new Rigidbody());
        obj1.addComponent(new SpriteRenderer(TextureManager.getTexture(TextureMap.BACKGROUND)));

        RenderManager.prepare();
        obj.start();
        obj1.start();

        obj1.getComponent(Rigidbody.class).setBodyType(MassType.INFINITE);

    }

    @Override
    public void update(float deltaTime) {
        RenderManager.render(camera);
        PhysicsSystem.getWorld().update(deltaTime);

        obj.update(deltaTime);
//        obj.getComponent(Transform.class).setXPosition(x += deltaTime * 100);
    }
}