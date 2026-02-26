package redball.engine.renderer;

import redball.engine.core.Engine;
import redball.engine.entity.ECSWorld;
import redball.engine.entity.GameObject;
import redball.engine.entity.components.CameraComponent;

import java.util.ArrayList;
import java.util.List;

import static redball.engine.renderer.BatchRenderer.MAX_ENTITIES;


public class RenderManager {
    private static List<BatchRenderer> batches = new ArrayList<>();

    public static void prepare(GameObject camera) {
        List<GameObject> gos = ECSWorld.getGameObjects();

        for (int i = 0; i < gos.size(); i += MAX_ENTITIES) {
            List<GameObject> chunk = gos.subList(i, Math.min(i + MAX_ENTITIES, gos.size()));
            BatchRenderer batch = new BatchRenderer(chunk);
            batch.prepare();
            batches.add(batch);
            System.out.println("Total number of batches: " + batches.size());
            System.out.println("Entity count: " + batch.entityCount);
            System.out.println("Total entity count: " + gos.size());
        }
        ECSWorld.start();
        camera.start();
    }

    public static void render(GameObject camera) {
        Engine.getShader().setMat4f("projection", camera.getComponent(CameraComponent.class).getProjectionMatrix());
        Engine.getShader().setMat4f("view", camera.getComponent(CameraComponent.class).getViewMatrix());

        Engine.getShader().initTextureSamplers();

        for (BatchRenderer batchRenderer : batches) {
            batchRenderer.bindTextures();
            batchRenderer.updateVertices();
            batchRenderer.render();
        }
    }

    public static void clear() {
        batches.clear();
    }
}