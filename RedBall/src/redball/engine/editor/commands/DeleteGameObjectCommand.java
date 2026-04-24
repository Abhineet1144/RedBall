package redball.engine.editor.commands;

import redball.engine.entity.ECSWorld;
import redball.engine.entity.GameObject;
import redball.engine.renderer.RenderManager;

import java.util.Iterator;

public class DeleteGameObjectCommand implements Command{
    private GameObject gameObject;

    public DeleteGameObjectCommand(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    @Override
    public void execute() {
        Iterator<GameObject> gameObjectIterator = ECSWorld.getGameObjects().iterator();
        while (gameObjectIterator.hasNext()) {
            GameObject object = gameObjectIterator.next();
            if (object.getName().equals(gameObject.getName())) {
                gameObjectIterator.remove();
                RenderManager.rebuild();
            }
        }
    }

    @Override
    public void undo() {
        ECSWorld.addPrefab(gameObject);
    }
}
