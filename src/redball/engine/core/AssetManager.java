package redball.engine.core;

import java.io.File;

public class AssetManager {
    private static AssetManager INSTANCE;
    private static String workingDirectory;
    private static File file;

    public AssetManager(String directory) {
        workingDirectory = directory;
        file = new File(directory);
        }

    public static String getWorkingDirectory() {
        return workingDirectory;
    }

    public static void setWorkingDirectory(String workingDirectory) {
        AssetManager.workingDirectory = workingDirectory;
    }

    public static void init(String workingDirectory) {
        INSTANCE = new AssetManager(workingDirectory);
    }

    public static AssetManager getINSTANCE() {
        return INSTANCE;
    }

    public static File getFile() {
        return file;
    }

    public static void setFile(File file) {
        AssetManager.file = file;
    }
}