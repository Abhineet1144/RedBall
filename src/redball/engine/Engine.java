package redball.engine;

import redball.engine.renderer.Shader;
import redball.engine.renderer.WindowManager;
import redball.engine.utils.AssetPool;
import redball.scenes.levels.Level1;
import redball.scenes.main.TestScene;

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

//        windowManager.useActiveScene(new Level1());
        windowManager.useActiveScene(new TestScene());
        windowManager.loop(shader);
    }

    public static WindowManager getWindowManager() {
        return windowManager;
    }

    public static Shader getShader() {
        return shader;
    }
}
