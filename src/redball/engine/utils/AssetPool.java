package redball.engine.utils;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

public class AssetPool {
    private static final String VERTEX_SHADER_PATH = "shaders/vertexShader.vert";
    private static final String FRAGMENT_SHADER_PATH = "shaders/fragmentShader.frag";

    public static String getFragmentShaderSource() {
        try {
            return new String(IOUtils.toByteArray(new FileInputStream(FRAGMENT_SHADER_PATH)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getVertexShaderSource() {
        try {
            return new String(IOUtils.toByteArray(new FileInputStream(VERTEX_SHADER_PATH)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}