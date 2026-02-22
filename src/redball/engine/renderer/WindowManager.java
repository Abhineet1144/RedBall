package redball.engine.renderer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;
import redball.engine.entity.GameObject;
import redball.engine.entity.components.SpriteRenderer;
import redball.engine.renderer.texture.TextureManager;
import redball.scenes.main.AbstractScene;
import redball.scenes.main.EmptyScene;

import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_MAXIMIZED;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetFramebufferSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.opengl.GL11.*;

public class WindowManager {
    private static long window = 0L;
    private int width = 1920;
    private int height = 1080;
    private int fpsCap = Integer.MAX_VALUE;
    private AbstractScene scene = new EmptyScene();

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

        window = GLFW.glfwCreateWindow(width, height, "Red Ball", MemoryUtil.NULL, MemoryUtil.NULL);
        if (window == MemoryUtil.NULL) {
            throw new RuntimeException("Can't create window");
        }
        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();
        GLFW.glfwShowWindow(window);

        glfwSwapInterval(0);
        glEnable(GL_BLEND);
        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LESS);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glfwSetFramebufferSizeCallback(window, (win, w, h) -> {
            glViewport(0, 0, w, h);

            width = w;
            height = h;
        });
    }

    public void loop(Shader shader) {
        shader.use();

        double lastTime = glfwGetTime();
        double lastSecond = lastTime;
        int fps = 0;

        while (!GLFW.glfwWindowShouldClose(window)) {

            double time = glfwGetTime();
            double deltaTime = time - lastTime;
            lastTime = time;

            // CLEAR
            glClearColor(0, 0, 0, 1);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            // RENDER
            scene.update(deltaTime);

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
