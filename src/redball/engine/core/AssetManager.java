package redball.engine.core;

import java.io.*;
import java.lang.reflect.InvocationTargetException;

public class AssetManager {
    private static AssetManager INSTANCE;
    private static String workingDirectory;
    public static String scenesDirectory;
    private static File file;

    public AssetManager(String directory) {
        workingDirectory = directory;
        scenesDirectory = workingDirectory + File.separatorChar + "assets/scenes" + File.separatorChar;
        file = new File(workingDirectory);
    }

    public void saveScene() {
        BufferedWriter sceneWriter = null;
        try {
            sceneWriter = new BufferedWriter(new FileWriter(scenesDirectory + "Scene.scene"));
            sceneWriter.write(Serialization.serialize());
            sceneWriter.close();
        } catch (IOException | IllegalAccessException e) {
            System.out.println("ERROR:" + e);
        }
    }

    public void loadScene() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        BufferedReader sceneReader = null;
        String scene = "";
        try {
            sceneReader = new BufferedReader(new FileReader(scenesDirectory + "Scene.scene"));
            String line;
            while ((line = sceneReader.readLine()) != null) {
                scene += line;
            }
        } catch (IOException e) {
            System.out.println("ERROR:" + e);
        }
        Serialization.deserialize(scene);
        System.out.println("d");
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