package redball.engine.renderer;

import redball.engine.entity.ECSWorld;
import redball.engine.entity.GameObject;

import java.util.ArrayList;
import java.util.List;

import static redball.engine.renderer.BatchRenderer.MAX_ENTITIES;


public class RenderManager {
    private static List<BatchRenderer> batches = new ArrayList<>();

    public static void prepare() {
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
    }

    public static void render() {
        for (BatchRenderer batchRenderer : batches) {
            batchRenderer.render();
        }
    }
}