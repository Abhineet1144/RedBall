package redball.engine.renderer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryUtil;
import redball.engine.entity.GameObject;
import redball.engine.entity.components.SpriteRenderer;
import redball.engine.renderer.texture.Texture;
import redball.engine.renderer.texture.TextureManager;

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
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class WindowManager {
    private static long window;
    private int width = 1920;
    private int height = 1080;
    int vao;

    public void init() {
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
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);

        glfwSetFramebufferSizeCallback(window, (win, w, h) -> {
            glViewport(0, 0, w, h);

            width = w;
            height = h;
        });
    }

    public void loop(Shader shader) {
        GameObject obj = new GameObject("1");
        obj.addComponent(new SpriteRenderer(TextureManager.getTexture("resources/container.jpg")));

        GameObject obj1 = new GameObject("2");
        obj1.addComponent(new SpriteRenderer(TextureManager.getTexture("resources/red.jpeg")));

        BatchRenderer batchRenderer = new BatchRenderer();
        batchRenderer.add(obj);
        batchRenderer.add(obj1);

        vao = batchRenderer.updateAllVertices();

        double lastTime = 0;
        double delatTime = 0;

        while (!GLFW.glfwWindowShouldClose(window)) {
            // CLEAR PREVIOUS
            GL11.glClearColor(0, 0, 0, 0);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            shader.use();
            int loc = GL20.glGetUniformLocation(shader.getID(), "u_Textures");
            int[] samplers = {0, 1, 2, 3, 4, 5, 6, 7};
            GL20.glUniform1iv(loc, samplers);

            TextureManager.bindTextures();

            glBindVertexArray(vao);
            glDrawElements(GL_TRIANGLES, 8, GL_UNSIGNED_INT, 0L);

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
