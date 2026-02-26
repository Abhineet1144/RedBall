package redball.scenes.scripts;

import redball.engine.core.Engine;
import redball.engine.entity.GameObject;
import redball.engine.entity.components.Component;
import redball.engine.entity.components.Transform;

public class CameraFollow extends Component {

    Transform camT;
    Transform ballT;
    Transform backGT;

    GameObject ball;
    GameObject background;

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