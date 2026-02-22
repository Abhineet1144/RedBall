package redball.engine.entity.components;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transform extends Component {
    public Vector3f position;
    public float rotation;
    public Vector3f scale;
    private Matrix4f matrix;

    public Transform(Vector3f position, float rotation, Vector3f scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.matrix = new Matrix4f();
    }

    public Matrix4f getMatrix() {
        matrix.identity().translate(position).rotateZ((float) Math.toRadians(rotation)).scale(scale);
        return matrix;
    }

    @Override
    public void update(float dt) {
    }

    public Vector3f getPosition() {
        return position;
    }
}
