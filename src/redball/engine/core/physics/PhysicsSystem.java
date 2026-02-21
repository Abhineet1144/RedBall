package redball.engine.core.physics;

import org.dyn4j.dynamics.Body;
import org.dyn4j.world.World;

public class PhysicsSystem {
    World<Body> world;

    public PhysicsSystem() {
        world = new World<>();
    }
}