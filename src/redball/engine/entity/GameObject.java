package redball.engine.entity;

import redball.engine.entity.components.Component;

import java.util.ArrayList;
import java.util.List;

public class GameObject {
    private String name;
    private List<Component> components;

    public GameObject(String name) {
        this.name = name;
        components = new ArrayList<>();
    }

    public <T extends Component> T getComponent(Class<T> componentClass) {
        for (Component c : components) {
            try {
                if (componentClass.isInstance(c)) {
                    return componentClass.cast(c);
                }
            } catch (ClassCastException e) {
                e.printStackTrace();
                assert false : "Error: Casting Component";
            }
        }
        return null;
    }

    public <T extends Component> boolean removeComponent(Class<T> componentClass) {
        for (Component c : components) {
            if (componentClass.isInstance(c)) {
                components.remove(c);
                return true;
            }
        }
        return false;
    }

    public void addComponent(Component c) {
        this.components.add(c);
        c.gameObject = this;
    }

    public void update(float dt) {
        for (Component c : components) {
            c.update(dt);
        }
    }

    public void start(float dt) {
        for (Component c : components) {
            c.start(dt);
        }
    }
}