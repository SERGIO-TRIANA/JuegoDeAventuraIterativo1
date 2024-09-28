import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Arrays;


class UndoRedoManager {
    private Stack<GameState> undoStack;
    private Stack<GameState> redoStack;
    private static final int MAX_SIZE = 5;

    public UndoRedoManager() {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

    public void saveState(GameState state) {
        if (undoStack.size() == MAX_SIZE) {
            undoStack.remove(0); // Limitar el tamaño de la pila
        }
        undoStack.push(state);
        redoStack.clear(); // Limpiar la pila de rehacer cuando se guarda un nuevo estado
    }

    public GameState undo() {
        if (!undoStack.isEmpty()) {
            GameState previousState = undoStack.pop();
            redoStack.push(previousState);
            return previousState;
        }
        return null;
    }

    public GameState redo() {
        if (!redoStack.isEmpty()) {
            GameState nextState = redoStack.pop();
            undoStack.push(nextState);
            return nextState;
        }
        return null;
    }
}

