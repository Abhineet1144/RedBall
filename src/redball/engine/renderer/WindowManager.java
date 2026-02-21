package redball.engine.renderer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_ONE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;

public class WindowManager {
    private static long window;
    private int width;
    private int height;

    public void init() {
        GLFWErrorCallback.createPrint(System.err);

        if (!GLFW.glfwInit()) {
            throw new IllegalArgumentException("Can't create window");
        }

        window = GLFW.glfwCreateWindow(1920, 1080, "Red Ball", MemoryUtil.NULL, MemoryUtil.NULL);
        if (window == MemoryUtil.NULL) {
            throw new RuntimeException("Can't create window");
        }
        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();
        GLFW.glfwShowWindow(window);

        glfwSwapInterval(0);
        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
    }

    public void loop() {
        double lastTime = 0;
        double delatTime = 0;

        while (!GLFW.glfwWindowShouldClose(window)) {
            // CLEAR PREVIOUS
            GL11.glClearColor(0, 0, 0, 0);
            glClear(GL_COLOR_BUFFER_BIT);

            // PREPARE FOR NEXT
            glfwSwapBuffers(window);
            glfwPollEvents();

            // Calculate delta time
            double time = glfwGetTime();
            delatTime = time - lastTime;
            lastTime = time;
        }
    }

    public void setVSync(int val) {
        glfwSwapBuffers(val);
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
