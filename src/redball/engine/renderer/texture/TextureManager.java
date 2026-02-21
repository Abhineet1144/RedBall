package redball.engine.renderer.texture;

import java.util.HashMap;
import java.util.Map;

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
}
