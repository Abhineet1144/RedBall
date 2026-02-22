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
        super.markAsDirty();
    }

    public void setXPosition(float xPos) {
        this.position.x = xPos;
        super.markAsDirty();
    }

    public void setYPosition(float yPos) {
        this.position.y = yPos;
        super.markAsDirty();
    }

    public void setZPosition(float zPos) {
        this.position.z = zPos;
        super.markAsDirty();
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
        super.markAsDirty();
    }

    public void setXScale(float xPos) {
        this.scale.x = xPos;
        super.markAsDirty();
    }

    public void setYScale(float yPos) {
        this.scale.y = yPos;
        super.markAsDirty();
    }

    public void setZScale(float zPos) {
        this.scale.z = zPos;
        super.markAsDirty();
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
