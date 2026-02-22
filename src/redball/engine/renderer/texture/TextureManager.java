package redball.engine.renderer.texture;

import java.util.HashMap;
import java.util.Map;

import redball.engine.entity.components.SpriteRenderer;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public class TextureManager {
    private static Map<String, Texture> textureMap = new HashMap<>();

    private TextureManager() {}

    public static Texture getTexture(String path) {
        if (textureMap.containsKey(path)) {
            return textureMap.get(path);
        } else {
            Texture tex = new Texture(path);
            textureMap.put(path, tex);
            return tex;
        }
    }

    public static void bindTextures() {
        for (Texture texture : textureMap.values()) {
            glActiveTexture(texture.getTexID());
            glBindTexture(GL_TEXTURE_2D, texture.getTexID());
        }
    }
}
