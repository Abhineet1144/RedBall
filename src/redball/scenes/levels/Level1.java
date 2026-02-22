package redball.scenes.levels;

import org.joml.Vector3f;

import redball.engine.entity.ECSWorld;
import redball.engine.entity.GameObject;
import redball.engine.entity.components.CameraComponent;
import redball.engine.entity.components.SpriteRenderer;
import redball.engine.entity.components.Transform;
import redball.engine.renderer.RenderManager;
import redball.engine.renderer.texture.TextureManager;
import redball.engine.renderer.texture.TextureMap;
import redball.scenes.main.AbstractScene;

public class Level1 extends AbstractScene {

    GameObject camera = new GameObject("Camera");
    GameObject bg = new GameObject("Background");
    GameObject obj;
    float x;

    @Override
    public void start() {
//        ECSWorld.clearGameObjects();
        camera.addComponent(new Transform(new Vector3f(0.0f, 0.0f, 0.0f), 0.0f, new Vector3f(250.0f)));
        camera.addComponent(new CameraComponent(1920, 1080));

        bg.addComponent(new Transform(new Vector3f(400f, 400f, -1f), 0f, new Vector3f(200, 200, 1)));
        bg.addComponent(new SpriteRenderer(TextureManager.getTexture(TextureMap.BACKGROUND)));

        RenderManager.prepare();
    }

    @Override
    public void update(double deltaTime) {
        RenderManager.render(camera);
        bg.getComponent(Transform.class).setXPosition(900);
        Transform t = bg.getComponent(Transform.class);
        t.setXPosition(x += (float) (deltaTime) * 100);
    }
}
