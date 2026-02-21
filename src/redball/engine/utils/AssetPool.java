package redball.engine.utils;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

public class AssetPool {
    public static String getFragmentShader() {
        try {
            return new String(IOUtils.toByteArray(new FileInputStream("fragmentShader.frag")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getVertexShader() {
        try {
            return new String(IOUtils.toByteArray(new FileInputStream("vertexShader.vert")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}