package redball.engine.entity;

import redball.engine.entity.components.Component;

import java.util.ArrayList;
import java.util.List;

public class GameObject {
    // Name of the gameobject
    private String name;
    // List of all components
    private List<Component> components;

    /**
     * @description Creates new gameobject of given name.
     * @param name of the object.
     */
    public GameObject(String name) {
        this.name = name;
        components = new ArrayList<>();
    }

    /**
     * @description gets a component from a gameobject.
     * @param componentClass type of component.
     * @return the component.
     */
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

    /**
     * @description removes a component from a gameobject
     * @param componentClass type of component.
     * @return true if success else false
     */
    public <T extends Component> boolean removeComponent(Class<T> componentClass) {
        for (Component c : components) {
            if (componentClass.isInstance(c)) {
                components.remove(c);
                return true;
            }
        }
        return false;
    }

    /**
     * @description adds a component to a gameobject
     * @param c type of component
     */
    public <T extends Component> T addComponent(Component c) {
        this.components.add(c);
        c.gameObject = this;
        return (T) getComponent(c.getClass());
    }

    /**
     * @description updates the gameobject (called every frame)
     * @param dt delta time
     */
    public void update(float dt) {
        for (Component c : components) {
            c.update(dt);
        }
    }

    /**
     * @description initializes the gameobjects (called once, in first frame)
     */
    public void start() {
        for (Component c : components) {
            c.start();
        }
    }

    /**
     * @return the name of a gameobject
     */
    public String getName() {
        return name;
    }
}