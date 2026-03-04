package redball.engine.core;

import com.google.gson.*;
import redball.engine.entity.ECSWorld;
import redball.engine.entity.GameObject;
import redball.engine.entity.components.Component;
import redball.engine.renderer.Camera;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Map;

public class Serialization {
    public static String serialize() throws IllegalAccessException {
        JsonObject sceneJsonObject = new JsonObject();
        Gson gson = new Gson();
        JsonArray scene = new JsonArray();

        for (GameObject go : ECSWorld.getGameObjects()) {

            sceneJsonObject.addProperty("id", go.getId().toString());
            sceneJsonObject.addProperty("name", go.getName());
            JsonArray componentsJsonArray = new JsonArray();

            for (Component component : go.getComponents()) {
                JsonObject componentJsonObject = new JsonObject();
                for (Field field : component.getClass().getFields()) {

                    if (!Modifier.isPublic(field.getModifiers())) continue;
                    if (Camera.class.isAssignableFrom(field.getType())) continue;
                    if (field.getName().equals("gameObject")) continue;

                    if (Component.class.isAssignableFrom(field.getType()) || GameObject.class.isAssignableFrom(field.getType())) {
                        // Add reference types
                        JsonObject ref = getRefJsonObject(component, field);
                        componentJsonObject.addProperty("type", component.getClass().getTypeName());
                        componentJsonObject.add(field.getName(), ref);
                    } else {
                        // Add primitive types
                        componentJsonObject.addProperty("type", component.getClass().getTypeName());
                        componentJsonObject.add(field.getName(), new Gson().toJsonTree(field.get(component)));
                    }
                }
                componentsJsonArray.add(componentJsonObject);
            }
            sceneJsonObject.add("component", componentsJsonArray);
            scene.add(gson.toJsonTree(sceneJsonObject));
        }
        return scene.toString();
    }

    public static void deserialize(String scene) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        JsonArray jsonArray = JsonParser.parseString(scene).getAsJsonArray();
        Gson gson = new Gson();
        for (JsonElement jsonElement : jsonArray) {
            JsonObject dataObject = jsonElement.getAsJsonObject();

            String name = dataObject.get("name").getAsString();
            GameObject go = ECSWorld.createGameObject(name);
            for (JsonElement componentArray : dataObject.get("component").getAsJsonArray()) {
//                go.addComponent(comp);
            }

        }
    }

    private static JsonObject getRefJsonObject(Component component, Field field) throws IllegalAccessException {
        Object refObject = field.get(component);
        JsonObject ref = new JsonObject();
        ref.addProperty("$ref", true);
        ref.addProperty("parentId", refObject instanceof Component ? ((Component) refObject).gameObject.getId().toString() : ((GameObject) refObject).getId().toString());
        ref.addProperty("componentType", refObject instanceof Component ? ((Component) refObject).getClass().getTypeName() : ((GameObject) refObject).getClass().getTypeName());
        return ref;
    }
}