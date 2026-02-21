package redball.engine.entity;

import redball.engine.entity.components.Tag;

import java.util.ArrayList;
import java.util.List;

public class ECSWorld {
    // List of all gameobjects
    private List<GameObject> gameObjects;

    public ECSWorld() {
        this.gameObjects = new ArrayList<>();
    }

    /**
     * @description Creates new gameobject of given name.
     * @param name of the object.
     */
    public void createGameObject(String name) {
        gameObjects.add(new GameObject(name));
    }

    /**
     * @description finds gameobject by given name
     * @param name of the object
     * @return gameobject if found else null
     */
    public GameObject findGameObjectByName(String name) {
        for (GameObject g : gameObjects) {
            if (g.getName().equals(name)) return g;
        }
        return null;
    }

    /**
     * @description finds gameobject by given tag
     * @param tag of the object
     * @return gameobject if found else null
     */
    public GameObject findGameObjectByTag(String tag) {
        for (GameObject g : gameObjects) {
            if (g.getComponent(Tag.class).getTag().equals(tag)) return g;
        }
        return null;
    }

    /**
     * @description removes given gameobject
     * @param gameObject we want to remove
     * @return true if found else false
     */
    public boolean removeGameObject(GameObject gameObject) {
        for (GameObject g : gameObjects) {
            if (g.equals(gameObject)) {
                gameObjects.remove(g);
                assert true : "SUCCESS: REMOVED GAMEOBJECT";
                return true;
            }
        }
        assert false : "FAILED: TO REMOVE GAMEOBJECT";
        return false;
    }

    /**
     * @description removes gameobject by given name
     * @param name of gameobject
     * @return true if found else false
     */
    public boolean removeGameObject(String name) {
        GameObject go = findGameObjectByName(name);
        if (go == null) {
            assert false : "FAILED: TO REMOVE GAMEOBJECT, IS NULL";
            return false;
        }
        return removeGameObject(go);
    }

    /**
     * @description removes gameobject by given tag
     * @param tag of gameobject
     * @return true if found else false
     */
    public boolean removeGameObjectByTag(String tag) {
        GameObject go = findGameObjectByTag(tag);
        if (go == null) {
            assert false : "FAILED: TO REMOVE GAMEOBJECT, IS NULL";
            return false;
        }
        return removeGameObject(go);
    }

    /**
     * @description updates all gameobjects
     * @param dt delta time
     */
    public void update(float dt) {
        for (GameObject g : gameObjects) {
            g.update(dt);
        }
    }
}