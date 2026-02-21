package redball.engine;

import redball.engine.renderer.Shader;
import redball.engine.renderer.WindowManager;
import redball.engine.utils.AssetPool;

public class Engine {
    private static boolean started = false;
    private static WindowManager windowManager = null;
    private static Shader shader = null;

    public static void start() {
        if (started) {
            return;
        }

        started = true;
        windowManager = new WindowManager();
        windowManager.init();

        shader = new Shader(AssetPool.getVertexShaderSource(), AssetPool.getFragmentShaderSource());

        windowManager.loop();
    }

    public WindowManager getWindowManager() {
        return windowManager;
    }

    public Shader getShader() {
        return shader;
    }
}
