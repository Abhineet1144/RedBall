package redball.engine.renderer.texture;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.stb.STBImage;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public class Texture {
    private static int usedTexSlots = 1;
    private static int[] texSlots = new int[] {
            GL13.GL_TEXTURE0, GL13.GL_TEXTURE1, GL13.GL_TEXTURE2, GL13.GL_TEXTURE3, GL13.GL_TEXTURE4, GL13.GL_TEXTURE5
    };

    private int texSlot;
    private int usedTexSlot;
    private int texId;
    private int width;
    private int height;
    private String filePath;

    public Texture(String filePath) {
        this.filePath = filePath;
        texId = GL11.glGenTextures();
        usedTexSlot = usedTexSlots;
        texSlot = texSlots[usedTexSlots-1];
        usedTexSlots++;
        GL13.glActiveTexture(texSlot);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texId);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);
        STBImage.stbi_set_flip_vertically_on_load(true);
        ByteBuffer textureImg = STBImage.stbi_load(filePath, width, height, channels, 0);

        if (textureImg != null) {
            this.width = width.get(0);
            this.height = height.get(0);
            int format = channels.get(0) == 4 ? GL11.GL_RGBA : GL11.GL_RGB;
            GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, format, this.width, this.height, 0, format, GL11.GL_UNSIGNED_BYTE, textureImg);
            STBImage.stbi_image_free(textureImg);
        } else {
            System.err.println("Failed to load texture: " + filePath);
            System.err.println(STBImage.stbi_failure_reason());
        }
    }

    public void bindTexture() {
        glActiveTexture(getTexID());
        glBindTexture(GL_TEXTURE_2D, getTexID());
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getTexID() {
        return texId;
    }

    public int getTexSlot() {
        return texSlot;
    }

    public int getUsedTexSlot() {
        return usedTexSlot;
    }

    public String getFilePath() {
        return filePath;
    }
}
