package redball.engine.entity.components;

import redball.engine.entity.GameObject;

public abstract class Component {
    public GameObject gameObject = null;

    public void start(float dt) {};

    public abstract void update(float dt);
}