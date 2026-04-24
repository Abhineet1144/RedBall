package redball.engine.editor.commands;

import redball.engine.entity.components.Component;

import java.lang.reflect.Field;

public class FieldChangeCommand implements Command{

    private Component component;

    private Field field;
    public Object oldValue;
    public Object newValue;

    public FieldChangeCommand(Component component, Field field, Object oldValue) {
        this.component = component;
        this.field = field;
        this.oldValue = oldValue;
    }

    @Override
    public void execute() {
        try {
            field.set(component, newValue);
        } catch (IllegalAccessException ignored) {}
    }

    @Override
    public void undo() {
        try {
            field.set(component, oldValue);
        } catch (IllegalAccessException ignored) {}
    }
}
