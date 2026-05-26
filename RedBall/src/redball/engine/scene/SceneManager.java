package redball.engine.scene;

import redball.engine.core.Engine;
import redball.engine.save.SaveManager;
import redball.engine.utils.PakWriter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class SceneManager {
    private static HashMap<Integer, String> sceneList;

    public static void init() {
        sceneList = new HashMap<>();
        int index = 0;
        if (Engine.isBuild) {
            ArrayList<String> scenes = new ArrayList<>();
            for (String key : PakWriter.getIndex().keySet()) {
                if (key.endsWith(".scene")) {
                    scenes.add(key);
                }
            }
            Collections.sort(scenes);
            for (String scene : scenes) {
                sceneList.put(index++, scene);
            }
        } else {
            File[] scenes = new File(AssetManager.getINSTANCE().getScenesDirectory()).listFiles();

            if (scenes != null) {
                Arrays.sort(scenes);
                for (File scene : scenes) {
                    sceneList.put(index++, scene.getPath());
                }
            }
        }
    }

    public static void loadDefault() throws IOException {
        SaveManager.loadScene(sceneList.get(0));
        if (!Engine.isBuild) {
            AssetManager.getINSTANCE().currentWorkingScene = sceneList.get(0);
        }
    }

    public static void switchScenes(int index) throws IOException {
        SaveManager.loadScene(sceneList.get(index));
    }

    public static HashMap<Integer, String> getSceneList() {
        return sceneList;
    }
}
