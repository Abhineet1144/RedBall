package redball.engine.entity.components;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transform extends Component {
    private Matrix4f transform;

    public Transform(Vector3f position, Vector3f rotation, Vector3f scale) {
        transform = new Matrix4f();
        transform.translate(position);
        transform.rotateXYZ(rotation);
        transform.scale(scale);
    }

    @Override
    public void update(float dt) {

    }

    public Matrix4f getTransform() {
        return transform;
    }
}