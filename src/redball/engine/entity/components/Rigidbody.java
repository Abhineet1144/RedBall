package redball.engine.entity.components;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import redball.engine.core.physics.PhysicsSystem;

public class Rigidbody extends Component {
    private Body body;
    public static final float PPM = 32.0f;

    public Rigidbody() {
        body = new Body();
    }

    @Override
    public void start() {
        Transform transform = this.gameObject.getComponent(Transform.class);

        body.getTransform().setTranslation(transform.position.x / PPM, transform.position.y / PPM);
        body.getTransform().setRotation(transform.rotation);
        body.addFixture(Geometry.createRectangle(transform.scale.x / PPM, transform.scale.y / PPM));
        body.setMass(MassType.NORMAL);
        PhysicsSystem.getWorld().addBody(body);
    }

    @Override
    public void update(float dt) {
    }

    public Body getBody() {
        return body;
    }
}