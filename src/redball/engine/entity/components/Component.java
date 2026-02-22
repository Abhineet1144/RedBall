package redball.engine.entity.components;

import redball.engine.entity.GameObject;

public abstract class Component {
    public GameObject gameObject = null;
    private boolean isDirty = true;

    protected void markAsDirty() {
        this.isDirty = true;
    }

    public void markAsClean() {
        this.isDirty = false;
    }

    public void start() {};

    public abstract void update(float dt);

    public boolean isDirty() {
        return isDirty;
    }
}