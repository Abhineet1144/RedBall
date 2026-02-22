package redball.engine.entity.components;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.Mass;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;
import redball.engine.core.PhysicsSystem;

import static redball.engine.core.PhysicsSystem.PPM;


public class Rigidbody extends Component {
    private Body body;


    public Rigidbody() {
        body = new Body();
    }

    @Override
    public void start() {
        Transform transform = this.gameObject.getComponent(Transform.class);

        body.getTransform().setTranslation(transform.position.x / PPM, transform.position.y / PPM);
        body.getTransform().setRotation(Math.toRadians(transform.rotation));
        setRectangleFixture();
        setBodyType(MassType.NORMAL);
        PhysicsSystem.getWorld().addBody(body);
        super.markAsDirty();
    }

    public void setBodyType(MassType type) {
        this.body.setMass(type);
    }

    public MassType getBodyType() {
        return this.body.getMass().getType();
    }

    public void setCircleFixture() {
        Transform transform = this.gameObject.getComponent(Transform.class);
        body.removeAllFixtures();
        body.addFixture(Geometry.createCircle((transform.scale.x / PPM) / 2));
        body.updateMass();
    }

    public void setRectangleFixture() {
        Transform transform = this.gameObject.getComponent(Transform.class);
        body.removeAllFixtures();
        body.addFixture(Geometry.createRectangle(transform.scale.x / PPM, transform.scale.y / PPM));
        body.updateMass();
    }

    // detects overlaps but has no collision response
    public void setSensor(boolean set) {
        body.getFixture(0).setSensor(set);
    }

    public void setMass(int mass) {
        Mass m = new Mass(new Vector2(0, 0), mass, 1.0);
        body.setMass(m);
    }

    @Override
    public void update(float dt) {
    }

    public Body getBody() {
        return body;
    }
}