package car.assets.scripts;

import org.joml.Vector2f;
import redball.engine.entity.ECSWorld;
import redball.engine.entity.GameObject;
import redball.engine.entity.components.Component;
import redball.engine.input.KeyboardInput;
import org.lwjgl.glfw.GLFW;

import java.io.Serial;

public class Test extends Component {
    @Serial
    private static final long serialVersionUID = 1L;

    private boolean spawned = false;
    public GameObject go;
    private int offset = 0;
    @Override
    public void update(float dt) {
        if (KeyboardInput.isKeyDown(GLFW.GLFW_KEY_LEFT)) {
            if (!spawned) {
                ECSWorld.instantiate(go, new Vector2f(100+offset, 800));
                spawned = true;
                offset += 350;
            }
        } else {
            spawned = false;
        }
    }
}
