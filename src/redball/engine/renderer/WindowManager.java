package redball.engine.renderer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;
import redball.engine.core.PhysicsSystem;
import redball.engine.entity.ECSWorld;
import redball.engine.utils.AbstractScene;
import redball.scenes.Level1;
import redball.scenes.TestScene;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class WindowManager {
    private static long window = 0L;
    private int width = 1920;
    private int height = 1080;
    private int fpsCap = Integer.MAX_VALUE;
    private AbstractScene scene;
    private boolean spaceWasPressed = false;

    public void init() {
        if (window != 0L) {
            return;
        }

        GLFWErrorCallback.createPrint(System.err).set();

        if (!GLFW.glfwInit()) {
            throw new IllegalArgumentException("Can't create window");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);
        // antialiasing: 2,4,8,16
        glfwWindowHint(GLFW_SAMPLES, 4);

        window = GLFW.glfwCreateWindow(width, height, "Red Ball", MemoryUtil.NULL, MemoryUtil.NULL);
        if (window == MemoryUtil.NULL) {
            throw new RuntimeException("Can't create window");
        }
        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();
        GLFW.glfwShowWindow(window);

        glfwSwapInterval(0);
        glEnable(GL_BLEND);
        glDisable(GL_DEPTH_TEST);
        glDepthFunc(GL_LESS);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        setVSync(1);

        glfwSetFramebufferSizeCallback(window, (win, w, h) -> {
            glViewport(0, 0, w, h);
            width = w;
            height = h;
        });
    }

    public void loop(Shader shader) {
        double lastTime = glfwGetTime();
        double lastSecond = lastTime;
        int fps = 0;
        shader.use();

        while (!GLFW.glfwWindowShouldClose(window)) {

            double time = glfwGetTime();
            double deltaTime = time - lastTime;
            lastTime = time;
            // CLEAR
            glClearColor(0, 0, 0, 1);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            // RENDER
            boolean spacePressed = GLFW.glfwGetKey(window, GLFW.GLFW_KEY_SPACE) == GLFW.GLFW_PRESS;
            if (spacePressed && !spaceWasPressed) {
                changeScene(0);
            }
            spaceWasPressed = spacePressed;
            scene.update((float) deltaTime);

            // SWAP
            glfwSwapBuffers(window);
            glfwPollEvents();

            // FPS Counter
            if (time - lastSecond >= 1.0) {
                setTitle("Red Ball. FPS: " + fps);
                fps = 0;
                lastSecond += 1.0;
            }
            fps++;
        }

        glfwTerminate();
    }

    public void changeScene(int newScene) {
        switch (newScene) {
            case 0:
                ECSWorld.removeAll();
                RenderManager.clear();
                useActiveScene(new Level1());
                break;
            case 1:
                ECSWorld.removeAll();
                RenderManager.clear();
                useActiveScene(new TestScene());
                break;
            default:
                assert false : "Unknown scene '" + newScene + "'";
                break;
        }
    }

    public void useActiveScene(AbstractScene scene) {
        scene.start();
        this.scene = scene;
    }

    public void setVSync(int val) {
        glfwSwapInterval(val);
    }

    public void setTitle(String name) {
        glfwSetWindowTitle(window, name);
    }

    public long getWindow() {
        return window;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
