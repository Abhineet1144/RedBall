package redball.scenes.scripts;

import org.dyn4j.geometry.Vector2;
import org.lwjgl.glfw.GLFW;
import redball.engine.entity.components.Component;
import redball.engine.entity.components.Rigidbody;
import redball.engine.input.KeyboardInput;

public class PlayerMovement extends Component {

    public Rigidbody ballBody;
    private boolean wasSpaceDown = false;
    public float maxSpeed = 27f;

    @Override
    public void start() {
        ballBody = gameObject.getComponent(Rigidbody.class);
    }

    @Override
    public void update(float dt) {
        Vector2 ballVelocity = ballBody.getBody().getLinearVelocity();
        boolean spaceDown = KeyboardInput.isKeyDown(GLFW.GLFW_KEY_SPACE) || KeyboardInput.isKeyDown(GLFW.GLFW_KEY_UP);
        if (spaceDown && !wasSpaceDown) {
            ballBody.getBody().applyImpulse(new Vector2(0, 4000));
        }
        wasSpaceDown = spaceDown;

        if (KeyboardInput.isKeyDown(GLFW.GLFW_KEY_LEFT)) {
            if (ballVelocity.x > -maxSpeed) {
                ballBody.getBody().applyForce(new Vector2(-1000000 * dt, 0));
            }
        }

        if (KeyboardInput.isKeyDown(GLFW.GLFW_KEY_RIGHT)) {
            if (ballVelocity.x < maxSpeed) {
                ballBody.getBody().applyForce(new Vector2(1000000 * dt, 0));
            }
        }
    }
}