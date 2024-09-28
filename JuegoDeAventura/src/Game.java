import java.util.*;


public class Game {
    private List<Puzzle> puzzles;
    private int currentPuzzleIndex;
    private UndoRedoManager undoRedoManager;
    private GameState gameState;

    public Game() {
        puzzles = new ArrayList<>();
        undoRedoManager = new UndoRedoManager();
        gameState = new GameState();
        initializePuzzles();
        currentPuzzleIndex = 0;
    }

    private void initializePuzzles() {
        puzzles.add(new NumericPuzzle(42)); // Acertijo numérico
        puzzles.add(new TextPuzzle("abracadabra")); // Acertijo textual
        Queue<String> sequence = new LinkedList<>(Arrays.asList("paso1", "paso2", "paso3"));
        puzzles.add(new SequencePuzzle(sequence)); // Acertijo de secuencia
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (currentPuzzleIndex < puzzles.size()) {
            Puzzle current = puzzles.get(currentPuzzleIndex);
            System.out.println(current.getDescription());
            String solution = scanner.nextLine();

            if (solution.equalsIgnoreCase("guardar")) {
                saveGame();
            } else if (solution.equalsIgnoreCase("cargar")) {
                loadGame();
            } else if (solution.equalsIgnoreCase("retroceder")) {
                undo();
            } else if (solution.equalsIgnoreCase("avanzar")) {
                redo();
            } else {
                if (current.solve(solution)) {
                    System.out.println("¡Correcto!");
                    gameState.updateState(currentPuzzleIndex);
                    undoRedoManager.saveState(gameState);
                    currentPuzzleIndex++;
                } else {
                    System.out.println("Incorrecto, intenta de nuevo.");
                }
            }
        }
        System.out.println("¡Felicidades! Has completado todos los acertijos.");
        scanner.close();
    }

    private void saveGame() {
        gameState.saveToFile("savegame.txt");
        System.out.println("Progreso guardado.");
    }

    private void loadGame() {
        gameState = GameState.loadFromFile("savegame.txt");
        if (gameState != null) {
            currentPuzzleIndex = gameState.getCurrentPuzzleIndex();
            System.out.println("Progreso cargado.");
        } else {
            System.out.println("No se pudo cargar el progreso.");
        }
    }

    private void undo() {
        GameState previousState = undoRedoManager.undo();
        if (previousState != null) {
            gameState = previousState;
            currentPuzzleIndex = gameState.getCurrentPuzzleIndex();
            System.out.println("Has retrocedido.");
        } else {
            System.out.println("No se puede retroceder más.");
        }
    }

    private void redo() {
        GameState nextState = undoRedoManager.redo();
        if (nextState != null) {
            gameState = nextState;
            currentPuzzleIndex = gameState.getCurrentPuzzleIndex();
            System.out.println("Has avanzado.");
        } else {
            System.out.println("No se puede avanzar más.");
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}
