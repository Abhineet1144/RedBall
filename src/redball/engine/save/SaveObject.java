package redball.engine.save;

import org.apache.commons.lang3.SerializationUtils;
import redball.engine.entity.ECSWorld;
import redball.engine.entity.GameObject;
import redball.engine.renderer.texture.TextureManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SaveObject implements Serializable {
    private final ArrayList<GameObject> gameObjects;
    private final ArrayList<String> textures;

    public SaveObject() {
        this.gameObjects = (ArrayList<GameObject>) ECSWorld.getGameObjects();
        this.textures = new ArrayList<>(TextureManager.listBoundTextures());
    }

    public static SaveObject parseFrom(byte[] bytes) {
        return (SaveObject) SerializationUtils.deserialize(bytes);
    }


    public byte[] toByteArray() {
        return SerializationUtils.serialize(this);
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public Collection<String> getTextures() {
        return textures;
    }
}
