package redball.engine.editor.commands;

import redball.engine.entity.GameObject;
import redball.engine.entity.components.Component;
import redball.engine.entity.components.SpriteRenderer;
import redball.engine.renderer.RenderManager;

public class AddComponentCommand implements Command {

    private GameObject gameObject;
    private Component component;

    public AddComponentCommand(GameObject gameObject, Component component) {
        this.gameObject = gameObject;
        this.component = component;
    }

    @Override
    public void execute() {
        gameObject.addComponent(component);
        if (component instanceof SpriteRenderer) {
            RenderManager.rebuild();
        }
    }

    @Override
    public void undo() {
        gameObject.removeComponent(component.getClass());
        if (component instanceof SpriteRenderer) {
            RenderManager.rebuild();
        }
    }
}
