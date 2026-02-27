package redball.engine.core;

import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.ImGuiStyle;
import imgui.flag.*;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import imgui.type.ImString;
import org.dyn4j.geometry.Rectangle;
import org.reflections.Reflections;
import redball.engine.entity.ECSWorld;
import redball.engine.entity.GameObject;
import redball.engine.entity.components.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Set;

public class EditorLayer {
    private final ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw();
    private final ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();
    private String selected = null;
    private final ImGuiIO io;
    private int selectedIndex = -1;
    private String[] componentList = null;
    private Set<Class<? extends Component>> subclasses;

    public EditorLayer(Long window) {
        ImGui.createContext();
        imGuiGlfw.init(window, true);
        imGuiGl3.init("#version 150");
        io = ImGui.getIO();
        io.addConfigFlags(ImGuiConfigFlags.DpiEnableScaleFonts);
        io.getFonts().setFreeTypeRenderer(true);

        ImGuiStyle style = ImGui.getStyle();
        style.setColor(ImGuiCol.WindowBg, 0.08f, 0.08f, 0.12f, 1.00f);
        style.setColor(ImGuiCol.ChildBg, 0.10f, 0.10f, 0.15f, 1.00f);
        style.setColor(ImGuiCol.Header, 0.55f, 0.32f, 0.10f, 1.00f);
        style.setColor(ImGuiCol.HeaderHovered, 0.75f, 0.45f, 0.15f, 1.00f);
        style.setColor(ImGuiCol.HeaderActive, 0.95f, 0.58f, 0.20f, 1.00f);
        style.setColor(ImGuiCol.TitleBg, 1.37f, 0.74f, 0.39f, 1.00f);
        style.setColor(ImGuiCol.TitleBgActive, 1.17f, 0.63f, 0.33f, 1.00f);
        style.setColor(ImGuiCol.FrameBg, 0.14f, 0.14f, 0.20f, 1.00f);
        style.setColor(ImGuiCol.Button, 0.20f, 0.20f, 0.30f, 1.00f);

        // Get all components
        // needs fix
        Reflections reflections = new Reflections("redball");
        subclasses = reflections.getSubTypesOf(Component.class);
        componentList = new String[subclasses.size()];
        int index = 0;
        for (Class<? extends Component> cls : subclasses) {
            componentList[index] = cls.getSimpleName();
            System.out.println(componentList[index]);
            index++;
        }
    }

    public void renderDebug() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        imGuiGlfw.newFrame();
        imGuiGl3.newFrame();
        ImGui.newFrame();

        ImGui.begin("Hierarchy");
        for (GameObject go : ECSWorld.getGameObjects()) {
            if (ImGui.button(go.getName())) {
                selected = go.getName();
            }
            if (ImGui.beginDragDropSource()) {
                ImGui.setDragDropPayload("GAME_OBJECT", go);
                ImGui.text(go.getName());
                ImGui.endDragDropSource();
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
                if (!(c instanceof Rigidbody) && !(c instanceof Transform) && !(c instanceof Tag)) {
                    customComponents(go.getComponent(c.getClass()));
                }
            }
            addComponent(go);
            ImGui.end();
        }

        ImGui.render();
        imGuiGl3.renderDrawData(ImGui.getDrawData());
    }

    private Component selectComponent(int n) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        for (Class<? extends Component> cls : subclasses) {
            if (cls.getSimpleName().equals(componentList[n])) {
                Constructor<?> constructor = cls.getConstructors()[0];
                Object[] params = new Object[constructor.getParameterCount()];
                // params are already null by default
                Component instance = (Component) constructor.newInstance(params);
                return instance;
            }
        }
        return null;
    }

    private void addComponent(GameObject go) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        ImString searchBuffer = new ImString(256);

        if (ImGui.button("Add Component")) {
            ImGui.openPopup("##addComponent");
        }

        if (ImGui.beginPopup("##addComponent")) {
            ImGui.inputTextWithHint("##search", "Search...", searchBuffer);

            if (ImGui.beginListBox("##ListBox")) {
                for (int n = 0; n < componentList.length; n++) {

                    String query = searchBuffer.get().toLowerCase();
                    if (!query.isEmpty() && !componentList[n].toLowerCase().contains(query)) {
                        continue;
                    }

                    boolean is_selected = (selectedIndex == n);
                    if (ImGui.selectable(componentList[n], is_selected, ImGuiSelectableFlags.AllowDoubleClick)) {
                        if (ImGui.isMouseDoubleClicked(0)) {
                            selectedIndex = n;
                            Component c = go.addComponent(selectComponent(n));
                            c.start();
                        }
                    }
                    if (is_selected) {
                        ImGui.setItemDefaultFocus();
                    }
                }
                ImGui.endListBox();
            }
            ImGui.endPopup();
        }
    }


    private void transformComponent(GameObject go) {
        if (ImGui.collapsingHeader("Transform", ImGuiTreeNodeFlags.DefaultOpen)) {
            Transform transform = go.getComponent(Transform.class);
            float[] pos = {transform.getXPosition(), transform.getYPosition()};
            if (ImGui.dragFloat2("Position", pos)) {
                transform.setXPosition(pos[0]);
                transform.setYPosition(pos[1]);
            }
            float[] rot = {transform.getRotation()};
            if (ImGui.dragFloat("Rotation", rot)) {
                transform.setRotation(rot[0] % 360);
            }
            float[] scale = {transform.getScaleX(), transform.getScaleY()};
            if (ImGui.dragFloat2("Scale", scale)) {
                transform.setXScale(scale[0]);
                transform.setYScale(scale[1]);
            }
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
                    } else if (field.getType() == boolean.class) {
                        boolean val = (boolean) value;
                        if (ImGui.checkbox(name, val)) {
                            field.set(component, val);
                        }
                    } else if (field.getType() == String.class) {
                        ImString val = new ImString(value.toString());
                        if (ImGui.inputText(name, new ImString(value.toString()))) {
                            field.set(component, val);
                        }
                    } else if (Component.class.isAssignableFrom(field.getType())) {
                        Component ref = (Component) value;
                        String label = (ref != null) ? ref.gameObject.getName() + " (" + field.getType().getSimpleName() + ")" : "None (" + field.getType().getSimpleName() + ")";
                        ImGui.inputText(name, new ImString(label), ImGuiInputTextFlags.ReadOnly);

                        if (ImGui.beginDragDropTarget()) {
                            Object payload = ImGui.acceptDragDropPayload("GAME_OBJECT");
                            if (payload instanceof GameObject dropped) {
                                Component comp = dropped.getComponent((Class<? extends Component>) field.getType());
                                if (comp != null) {
                                    field.set(component, comp);
                                }
                            }
                            ImGui.endDragDropTarget();
                        }
                    } else if (field.getType() == GameObject.class) {
                        GameObject ref = (GameObject) value;
                        String label = (ref != null) ? ref.getName() + " (" + field.getType().getSimpleName() + ")" : "None (" + field.getType().getSimpleName() + ")";
                        ImGui.inputText(name, new ImString(label), ImGuiInputTextFlags.ReadOnly);

                        if (ImGui.beginDragDropTarget()) {
                            Object payload = ImGui.acceptDragDropPayload("GAME_OBJECT");
                            if (payload instanceof GameObject dropped) {
                                field.set(component, dropped);
                            }
                            ImGui.endDragDropTarget();
                        }
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
