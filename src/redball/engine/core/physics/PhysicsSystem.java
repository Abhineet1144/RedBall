package redball.engine.core.physics;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Vector2;
import org.dyn4j.world.World;

public class PhysicsSystem {
    private static World<Body> world = new World<>();;

    public static void init() {
        world.setGravity(new Vector2(0, -9.8));

    }

    public static World<Body> getWorld() {
        return world;
    }
}