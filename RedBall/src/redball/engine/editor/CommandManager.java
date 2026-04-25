package redball.engine.editor;

import redball.engine.core.Engine;
import redball.engine.editor.commands.Command;
import redball.engine.entity.ECSWorld;
import redball.engine.entity.GameObject;

import java.util.Stack;

public class CommandManager {
    private static Stack<Command> redoStack = new Stack<>();
    private static Stack<Command> undoStack = new Stack<>();
    private static GameObject copyInstance = null;
    private static final int MAX_SIZE = 50;

    public static void pushToUndoStack(Command command) {
        if (undoStack.size() > MAX_SIZE) {
            undoStack.removeFirst();
        }
        undoStack.push(command);
    }

    public static void undo() {
        if (Engine.isPlaying()) return;
        if (!undoStack.isEmpty()) {
            Command command = undoStack.pop();
            command.undo();
            redoStack.push(command);
        }
    }

    public static void redo() {
        if (Engine.isPlaying()) return;
        if (!redoStack.isEmpty()) {
            Command command = redoStack.pop();
            command.execute();
            undoStack.push(command);
        }
    }

    public static void copy(GameObject gameObject) {
        setCopyInstance(gameObject.deepCopy());
    }

    public static void paste() {
        ECSWorld.addPrefab(copyInstance);
    }

    public static GameObject getCopyInstance() {
        return copyInstance;
    }

    public static void setCopyInstance(GameObject copyInstance) {
        CommandManager.copyInstance = copyInstance;
    }
}