package redball.engine.scene;

import redball.engine.core.Engine;
import redball.engine.save.SaveManager;
import redball.engine.utils.PakWriter;

import java.io.File;
import java.util.HashMap;

public class SceneManager {
    private static HashMap<Integer, String> sceneList = new HashMap<>();

    public static void init() {
        int index = 0;
        if (Engine.isBuild) {
            for (String key : PakWriter.getManifestFile().keySet()) {
                if (key.endsWith(".scene")) {
                    sceneList.put(index, PakWriter.getManifestFile().get(key));
                    System.out.println(sceneList.get(index));
                    System.out.println(key);
                    index++;
                }
            }
        } else {
            File[] scenes = new File(AssetManager.getINSTANCE().getScenesDirectory()).listFiles();

            if (scenes != null) {
                for (File scene : scenes) {
                    sceneList.put(index, scene.getPath());
                    index++;
                }
            }
        }
    }

    public static void loadDefault() {
        SaveManager.loadScene(sceneList.get(2));
        AssetManager.getINSTANCE().currentWorkingScene = sceneList.get(2);
    }

    public static void switchScenes(int index) {
        // need to check for null
        SaveManager.loadScene(sceneList.get(index));
    }

    public static HashMap<Integer, String> getSceneList() {
        return sceneList;
    }
}
