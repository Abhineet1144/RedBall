package redball.engine.editor.commands;

import redball.engine.entity.GameObject;
import redball.engine.entity.components.Component;

public class AddComponentCommand implements Command{

    private GameObject gameObject;
    private Component component;

    public AddComponentCommand(GameObject gameObject, Component component) {
        this.gameObject = gameObject;
        this.component = component;
    }

    @Override
    public void execute() {
        gameObject.addComponent(component);
    }

    @Override
    public void undo() {
        gameObject.removeComponent(component.getClass());
    }
}
