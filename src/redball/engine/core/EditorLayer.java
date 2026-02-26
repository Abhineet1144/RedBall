package redball.engine.core;

import imgui.ImGui;
import imgui.flag.ImGuiInputTextFlags;
import imgui.flag.ImGuiTreeNodeFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import imgui.type.ImString;
import org.dyn4j.geometry.Rectangle;
import redball.engine.entity.ECSWorld;
import redball.engine.entity.GameObject;
import redball.engine.entity.components.*;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class EditorLayer {
    private final ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw();
    private final ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();
    private String selected = null;

    public EditorLayer(Long window) {
        ImGui.createContext();
        imGuiGlfw.init(window, true);
        imGuiGl3.init("#version 150");
    }

    public void renderDebug() {
        imGuiGlfw.newFrame();
        imGuiGl3.newFrame();
        ImGui.newFrame();

        ImGui.begin("Hierarchy");
        for (GameObject go : ECSWorld.getGameObjects()) {
            if (ImGui.button(go.getName())) {
                selected = go.getName();
            }
        }
        ImGui.end();

        if (selected != null) {
            ImGui.begin("Inpector");
            GameObject go = ECSWorld.findGameObjectByName(selected);
            ImGui.text("Name");
            ImGui.sameLine();
            ImGui.inputText("##Name", new ImString(go.getName()));
            tagComponent(go);
            transformComponent(go);
            rigidbodyComponent(go);
            for (Component c : go.getComponents()) {
                if (!(c instanceof  Rigidbody) && !(c instanceof Transform) && !(c instanceof Tag)) {
                    customComponents(go.getComponent(c.getClass()));
                }
            }
            ImGui.end();
        }

        ImGui.render();
        imGuiGl3.renderDrawData(ImGui.getDrawData());
    }

    private void transformComponent(GameObject go) {
        if (ImGui.collapsingHeader("Transform", ImGuiTreeNodeFlags.DefaultOpen)) {
            ImGui.dragFloat2("Position", new float[]{go.getComponent(Transform.class).getXPosition(), go.getComponent(Transform.class).getYPosition()});
        }
    }

    private void tagComponent(GameObject go) {
        Tag tag = go.getComponent(Tag.class);
        if (tag != null) {
            ImGui.text("Tag");
            ImGui.sameLine();
            ImString val = new ImString(tag.getTag(), 256);
            if (ImGui.inputText("##Tag", val)) {
                tag.setTag(val.get());
            }
        }
    }

    private void rigidbodyComponent(GameObject go) {
        Rigidbody rb = go.getComponent(Rigidbody.class);
        if (rb != null) {
            if (ImGui.collapsingHeader("RigidBody", ImGuiTreeNodeFlags.DefaultOpen)) {
                ImGui.checkbox("isDynamic", rb.getBodyType() == BodyType.DYNAMIC ? true : false);
                ImGui.inputText("Shape", new ImString(rb.getBody().getFixture(0).getShape() instanceof Rectangle ? "Rectangle" : "Circle"));
                ImGui.inputText("Mass", new ImString(String.valueOf(rb.getMass())));
                ImGui.inputText("Bounciness", new ImString(String.valueOf(rb.getBounce())));
                ImGui.inputText("Friction", new ImString(String.valueOf(rb.getFriction())));
                ImGui.inputText("Friction", new ImString(String.valueOf(rb.getFriction())));
            }
        }
    }

    private void customComponents(Component component) {
        Class<?> clazz = component.getClass();
        if (ImGui.collapsingHeader(clazz.getSimpleName(), ImGuiTreeNodeFlags.DefaultOpen)) {
            for (Field field : clazz.getDeclaredFields()) {
                if (!Modifier.isPublic(field.getModifiers())) continue;

                try {
                    String name = field.getName();
                    Object value = field.get(component);
                    if (field.getType() == float.class) {
                        float[] val = {(float) value};
                        if (ImGui.dragFloat(name, val)) {
                            field.set(component, val[0]);
                        }
                    } else if (field.getType() == int.class) {
                        int[] val = {(int) value};
                        if (ImGui.dragInt(name, val)) {
                            field.set(component, val[0]);
                        }
                    } else if (field.getType() == String.class) {
                        ImString val = new ImString(value.toString());
                        if (ImGui.inputText(name, new ImString(value.toString()))) {
                            field.set(component, val);
                        }
                    } else if (Component.class.isAssignableFrom(field.getType())) {
                        Component ref = (Component) value;
                        String label = (ref != null) ? ref.getClass().getSimpleName() + " (" + field.getType().getSimpleName() + ")" : "None (" + field.getType().getSimpleName() + ")";
                        ImGui.inputText(name, new ImString(label), ImGuiInputTextFlags.ReadOnly);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void dispose() {
        imGuiGl3.destroyDeviceObjects();
        imGuiGlfw.shutdown();
        ImGui.destroyContext();
    }
}
