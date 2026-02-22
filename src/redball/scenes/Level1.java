package redball.scenes;

import org.joml.Vector3f;

import redball.engine.core.*;
import redball.engine.entity.*;
import redball.engine.entity.components.*;
import redball.engine.renderer.*;
import redball.engine.renderer.texture.*;
import redball.engine.utils.*;


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
