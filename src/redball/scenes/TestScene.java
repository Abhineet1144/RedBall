package redball.scenes;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.contact.Contact;
import org.dyn4j.geometry.Vector2;
import org.dyn4j.world.ContactCollisionData;
import org.dyn4j.world.listener.ContactListenerAdapter;
import org.joml.Vector3f;

import org.lwjgl.glfw.GLFW;
import redball.engine.core.*;
import redball.engine.entity.*;
import redball.engine.entity.components.*;
import redball.engine.input.KeyboardInput;
import redball.engine.renderer.*;
import redball.engine.renderer.texture.*;
import redball.engine.utils.*;

public class TestScene extends AbstractScene {
    GameObject ball;
    GameObject groundL;
    GameObject groundC;
    GameObject background;
    GameObject groundR;
    GameObject camera = new GameObject("Camera");
    boolean wasSpaceDown = false;

    @Override
    public void start() {
        PhysicsSystem.init();
        camera.addComponent(new Transform(new Vector3f(0.0f, 0.0f, 0.0f), 0.0f, new Vector3f(250.0f)));
        camera.addComponent(new CameraComponent(1920, 1080));

        background = ECSWorld.createGameObject("BackGround");
        background.addComponent(new Transform(new Vector3f(1920f / 2, 1080f / 2, -1f), 0f, new Vector3f(1920, 1080, 1)));
        background.addComponent(new SpriteRenderer(TextureManager.getTexture(TextureMap.BACKGROUND)));

        ball = ECSWorld.createGameObject("Ball");
        ball.addComponent(new Transform(new Vector3f(400.0f, 800.0f, -1.0f), 90.0f, new Vector3f(100.0f, 100.0f, 1.0f)));
        Rigidbody ballRb = ball.addComponent(new Rigidbody());
        ball.addComponent(new SpriteRenderer(TextureManager.getTexture(TextureMap.BALL)));

        groundL = ECSWorld.createGameObject("GroundL");
        groundL.addComponent(new Transform(new Vector3f(150.0f, 250.0f, -1.0f), -15.0f, new Vector3f(1920.0f / 2, 20.0f, 1.0f)));
        Rigidbody groundLRb = this.groundL.addComponent(new Rigidbody());
        this.groundL.addComponent(new SpriteRenderer(TextureManager.getTexture(TextureMap.TEST1)));

        groundC = ECSWorld.createGameObject("GroundC");
        groundC.addComponent(new Transform(new Vector3f(1000.0f, 150.0f, -1.0f), 0.0f, new Vector3f(1920.0f / 2, 20.0f, 1.0f)));
        Rigidbody groundCRb = this.groundC.addComponent(new Rigidbody());
        this.groundC.addComponent(new SpriteRenderer(TextureManager.getTexture(TextureMap.TEST1)));

        groundR = ECSWorld.createGameObject("GroundR");
        groundR.addComponent(new Transform(new Vector3f(1900.0f, 250.0f, -1.0f), 15.0f, new Vector3f(1920.0f / 2, 20.0f, 1.0f)));
        Rigidbody groundRRb = this.groundR.addComponent(new Rigidbody());
        this.groundR.addComponent(new SpriteRenderer(TextureManager.getTexture(TextureMap.TEST1)));

        RenderManager.prepare();

        ballRb.setCircleFixture();
        ballRb.setMass(200);
        ballRb.setBounce(0.1);
        ballRb.setFriction(1.0);

        groundLRb.setBodyType(BodyType.STATIC);
        groundCRb.setBodyType(BodyType.STATIC);
        groundRRb.setBodyType(BodyType.STATIC);
    }

    @Override
    public void update(float deltaTime) {
        Rigidbody ballBody = ball.getComponent(Rigidbody.class);
        RenderManager.render(camera);

        boolean spaceDown = KeyboardInput.isKeyDown(GLFW.GLFW_KEY_SPACE);
        if (spaceDown && !wasSpaceDown) {
                ballBody.getBody().applyImpulse(new Vector2(0, 500));
        }
        wasSpaceDown = spaceDown;

        if (KeyboardInput.isKeyDown(GLFW.GLFW_KEY_LEFT)) {
//            ballBody.getBody().applyForce(new Vector2(-200, 0));
            ballBody.getBody().setLinearVelocity(-10,0);
        }

        if (KeyboardInput.isKeyDown(GLFW.GLFW_KEY_RIGHT)) {
//            ballBody.getBody().applyForce(new Vector2(200, 0));
            ballBody.getBody().setLinearVelocity(10,0);
        }

        ECSWorld.update(deltaTime);

//        obj.getComponent(Transform.class).setXPosition(x += deltaTime * 100);
    }
}