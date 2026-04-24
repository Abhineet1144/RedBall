package redball.engine.editor.commands;

import org.joml.Vector3f;
import redball.engine.entity.components.Transform;

public class TransformCommand implements Command {

    private Transform transform;

    // old
    public Vector3f oldPos;
    public float oldRot;
    public Vector3f oldScale;

    // new
    public Vector3f newPos;
    public float newRot;
    public Vector3f newScale;

    public TransformCommand(Transform transform) {
        this.transform = transform;
    }

    @Override
    public void execute() {
        transform.setXPosition(newPos.x);
        transform.setYPosition(newPos.y);

        transform.setRotation(newRot);

        transform.setXScale(newScale.x);
        transform.setYScale(newScale.y);
    }

    @Override
    public void undo() {
        transform.setXPosition(oldPos.x);
        transform.setYPosition(oldPos.y);

        transform.setRotation(oldRot);

        transform.setXScale(oldScale.x);
        transform.setYScale(oldScale.y);
    }
}
