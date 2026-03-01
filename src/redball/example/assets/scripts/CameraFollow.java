package redball.example.assets.scripts;

import redball.engine.core.Engine;
import redball.engine.entity.GameObject;
import redball.engine.entity.components.Component;
import redball.engine.entity.components.Transform;

public class CameraFollow extends Component {

    public Transform camT;
    public Transform ballT;
    public Transform backGT;

    public GameObject ball;
    public GameObject background;

    public CameraFollow(GameObject ball, GameObject back) {
        this.ball = ball;
        this.background = back;
    }

    @Override
    public void start() {
        camT = gameObject.getComponent(Transform.class);
        ballT = ball.getComponent(Transform.class);
        backGT = background.getComponent(Transform.class);
    }

    @Override
    public void update(float dt) {
        camT.setXPosition(ballT.getXPosition() - (Engine.getWindowManager().getWidth() / 2f));
        backGT.setXPosition(ballT.getXPosition());

        if (ballT.getYPosition() > 550) {
            camT.setYPosition(ballT.getYPosition() - (Engine.getWindowManager().getHeight() / 2f));
            backGT.setYPosition(ballT.getYPosition());
        }
    }
}