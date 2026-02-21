package redball.engine.entity.components;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transform extends Component {
    public Vector3f position;
    public Vector3f rotation;
    public Vector3f scale;
    private Matrix4f matrix;

    public Transform(Vector3f position, Vector3f rotation, Vector3f scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.matrix = new Matrix4f();
    }

    public Matrix4f getMatrix() {
        matrix.identity().translate(position).rotateXYZ(rotation).scale(scale);
        return matrix;
    }

    public void update(float dt) {
    }
}
