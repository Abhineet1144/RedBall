package redball.scenes.main;

import org.dyn4j.geometry.MassType;
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

public class TestScene extends AbstractScene {
    GameObject obj;
    GameObject obj1;
    GameObject obj2;
    GameObject obj3;
    GameObject obj4;
    GameObject camera = new GameObject("Camera");

    @Override
    public void start() {
        PhysicsSystem.init();
        camera.addComponent(new Transform(new Vector3f(0.0f, 0.0f, 0.0f), 0.0f, new Vector3f(250.0f)));
        camera.addComponent(new CameraComponent(1920, 1080));

        obj3 = ECSWorld.createGameObject("Ground");
        obj3.addComponent(new Transform(new Vector3f(1920f / 2, 1080f / 2, -1f), 0f, new Vector3f(1920, 1080, 1)));
        obj3.addComponent(new SpriteRenderer(TextureManager.getTexture(TextureMap.BACKGROUND)));

        obj = ECSWorld.createGameObject("Ball");
        obj.addComponent(new Transform(new Vector3f(400.0f, 800.0f, -1.0f), 90.0f, new Vector3f(100.0f, 100.0f, 1.0f)));
        obj.addComponent(new Rigidbody());
        obj.addComponent(new SpriteRenderer(TextureManager.getTexture(TextureMap.BALL)));

        obj1 = ECSWorld.createGameObject("Ground");
        obj1.addComponent(new Transform(new Vector3f(150.0f, 250.0f, -1.0f), -15.0f, new Vector3f(1920.0f / 2, 20.0f, 1.0f)));
        obj1.addComponent(new Rigidbody());
        obj1.addComponent(new SpriteRenderer(TextureManager.getTexture(TextureMap.TEST1)));

        obj2 = ECSWorld.createGameObject("Ground");
        obj2.addComponent(new Transform(new Vector3f(1000.0f, 150.0f, -1.0f), 0.0f, new Vector3f(1920.0f / 2, 20.0f, 1.0f)));
        obj2.addComponent(new Rigidbody());
        obj2.addComponent(new SpriteRenderer(TextureManager.getTexture(TextureMap.TEST1)));

        obj4 = ECSWorld.createGameObject("Ground");
        obj4.addComponent(new Transform(new Vector3f(1900.0f, 250.0f, -1.0f), 15.0f, new Vector3f(1920.0f / 2, 20.0f, 1.0f)));
        obj4.addComponent(new Rigidbody());
        obj.getComponent(Rigidbody.class).setCircleFixture();
        obj4.addComponent(new SpriteRenderer(TextureManager.getTexture(TextureMap.TEST1)));

        RenderManager.prepare();

        obj.getComponent(Rigidbody.class).setMass(500);
        obj.getComponent(Rigidbody.class).getBody().getFixture(0).setRestitution(0.2);
        obj.getComponent(Rigidbody.class).getBody().getFixture(0).setFriction(0.5);

        obj1.getComponent(Rigidbody.class).setBodyType(MassType.INFINITE);

        obj2.getComponent(Rigidbody.class).setBodyType(MassType.INFINITE);

        obj4.getComponent(Rigidbody.class).setBodyType(MassType.INFINITE);
    }

    @Override
    public void update(float deltaTime) {
        RenderManager.render(camera);
        PhysicsSystem.getWorld().update(deltaTime);

        ECSWorld.update(deltaTime);
//        obj.getComponent(Rigidbody.class).getBody().applyTorque(-5);
//        obj.getComponent(Transform.class).setXPosition(x += deltaTime * 100);
    }
}