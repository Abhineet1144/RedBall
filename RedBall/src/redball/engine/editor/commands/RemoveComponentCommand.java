package redball.engine.editor.commands;

import redball.engine.entity.GameObject;
import redball.engine.entity.components.Component;
import redball.engine.entity.components.SpriteRenderer;
import redball.engine.renderer.RenderManager;

public class RemoveComponentCommand implements Command{

    private GameObject gameObject;
    private Component component;

    public RemoveComponentCommand(GameObject gameObject, Component component) {
        this.gameObject = gameObject;
        this.component = component;
    }

    @Override
    public void execute() {
        gameObject.removeComponent(component.getClass());
    }

    @Override
    public void undo() {
        gameObject.addComponent(component);
        if (component instanceof SpriteRenderer) {
            RenderManager.rebuild();
        }
    }
}
