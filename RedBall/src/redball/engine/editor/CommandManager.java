package redball.engine.editor;

import redball.engine.core.Engine;
import redball.engine.editor.commands.Command;

import java.util.Stack;

public class CommandManager {
    private static Stack<Command> redoStack = new Stack<>();
    private static Stack<Command> undoStack = new Stack<>();

    public static void pushToUndoStack(Command command) {
        undoStack.push(command);
    }

    public static void undo() throws IllegalAccessException {
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
}
