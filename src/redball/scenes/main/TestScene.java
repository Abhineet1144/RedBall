package redball.scenes.main;

import redball.engine.entity.GameObject;
import redball.engine.entity.components.SpriteRenderer;
import redball.engine.renderer.BatchRenderer;
import redball.engine.renderer.RenderManager;
import redball.engine.renderer.texture.TextureManager;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class TestScene extends AbstractScene {
    private int vao;

    @Override
    public void start() {
        GameObject obj = new GameObject("1");
        obj.addComponent(new SpriteRenderer(TextureManager.getTexture("resources/container.jpg")));

        GameObject obj1 = new GameObject("2");
        obj1.addComponent(new SpriteRenderer(TextureManager.getTexture("resources/red.jpeg")));

        BatchRenderer batchRenderer = RenderManager.newBatchRenderer();
        batchRenderer.add(obj);
        batchRenderer.add(obj1);

        vao = batchRenderer.updateAllVertices();
    }

    @Override
    public void update(double deltaTime) {
        glBindVertexArray(vao);
        glDrawElements(GL_TRIANGLES, 12, GL_UNSIGNED_INT, 0L);
    }
}
