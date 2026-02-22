package redball.engine.renderer;

import java.util.*;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import redball.engine.entity.GameObject;
import redball.engine.entity.components.SpriteRenderer;
import redball.engine.entity.components.Transform;
import redball.engine.renderer.texture.Texture;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;

public class BatchRenderer {
    public static final int MAX_ENTITIES = 2;
    private static final int POS_SIZE = 3;
    private static final int COLOR_SIZE = 4;
    private static final int TEXTURE_COORDS_SIZE = 2;
    private static final int TEXTURE_ID_SIZE = 1;
    private static final int OVERALL_SIZE = POS_SIZE + COLOR_SIZE + TEXTURE_COORDS_SIZE + TEXTURE_ID_SIZE;
    private static final int OVERALL_STRIDE = OVERALL_SIZE * Float.BYTES;

    public int entityCount = 0;
    private int verticesAdded = 0;
    private List<GameObject> entities;
    private List<float[]> vertexData;
    private List<int[]> vertexIndices;
    private float[] verticesData = new float[OVERALL_SIZE * MAX_ENTITIES * 4];
    private int[] vertexIndex = new int[OVERALL_SIZE * 6];
    private int hightest = 0;
    private int vao;

    BatchRenderer(List<GameObject> go) {
        entities = new ArrayList<>(go);
        entityCount = entities.size();
        vertexData = new ArrayList<>();
        vertexIndices = new ArrayList<>();
    }

    public int updateAllVertices() {
        int offset = 0;
        int h = hightest;

        for (GameObject entity : entities) {
            int quadOffset = offset * 4 * OVERALL_SIZE;
            Texture texture = entity.getComponent(SpriteRenderer.class).getTexture();
            int textureSlot = 0;
            if (texture != null) {
                textureSlot = texture.getUsedTexSlot();
            }
            updateComponentVertices(entity, quadOffset + 0 * OVERALL_SIZE, -0.5f, 0.5f, 0, 1, textureSlot);
            updateComponentVertices(entity, quadOffset + 1 * OVERALL_SIZE, -0.5f, -0.5f, 0, 0, textureSlot);
            updateComponentVertices(entity, quadOffset + 2 * OVERALL_SIZE, 0.5f, -0.5f, 1, 0, textureSlot);
            updateComponentVertices(entity, quadOffset + 3 * OVERALL_SIZE, 0.5f, 0.5f, 1, 1, textureSlot);
            for (int i : new int[]{0, 1, 2, 2, 3, 0}) {
                int eboVal = hightest + i;
                vertexIndex[verticesAdded++] = eboVal;
                h = Math.max(h, eboVal);
            }
            offset++;
            hightest = h + 1;
        }

        System.out.println(Arrays.toString(verticesData));
        System.out.println(Arrays.toString(vertexIndex));

        int vao = glGenVertexArrays();
        int vbo = glGenBuffers();
        int EBO = glGenBuffers();
        glBindVertexArray(vao);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, verticesData, GL_STATIC_DRAW);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, vertexIndex, GL_STATIC_DRAW);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, OVERALL_STRIDE, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, 4, GL_FLOAT, false, OVERALL_STRIDE, 3 * Float.BYTES);
        glEnableVertexAttribArray(1);

        glVertexAttribPointer(2, 2, GL_FLOAT, false, OVERALL_STRIDE, 7 * Float.BYTES);
        glEnableVertexAttribArray(2);

        glVertexAttribPointer(3, 1, GL_FLOAT, false, OVERALL_STRIDE, 9 * Float.BYTES);
        glEnableVertexAttribArray(3);

        glDrawElements(GL_TRIANGLES, verticesAdded, GL_UNSIGNED_INT, 0);

        return vao;
    }

    private void updateComponentVertices(GameObject go, int off, float x, float y, int tx, int ty, int tId) {
        Matrix4f transform = go.getComponent(Transform.class).getMatrix();
        Vector4f pos = transform.transform(new Vector4f(x, y, 0, 1));

        verticesData[off] = pos.x;
        verticesData[off + 1] = pos.y;
        verticesData[off + 2] = pos.z;
        verticesData[off + 3] = 1;
        verticesData[off + 4] = 1;
        verticesData[off + 5] = 1;
        verticesData[off + 6] = 1;
        verticesData[off + 7] = tx;
        verticesData[off + 8] = ty;
        verticesData[off + 9] = tId;
    }

    public void prepare() {
        vao = updateAllVertices();
    }

    public void render() {
        glBindVertexArray(vao);
        glDrawElements(GL_TRIANGLES, verticesAdded, GL_UNSIGNED_INT, 0);
    }
}
