import javax.swing.*;
import java.io.*;
import java.util.*;

public class Game {
    private final List<Puzzle> puzzles;
    private int currentPuzzleIndex;
    private final UndoRedoManager undoRedoManager;
    private GameState gameState;

    public Game() {
        puzzles = new ArrayList<>();
        undoRedoManager = new UndoRedoManager();
        gameState = new GameState();
        initializePuzzles();
        currentPuzzleIndex = 0;
    }

    private void initializePuzzles() {
        puzzles.add(new NumericPuzzle(5)); // Acertijo numérico
        puzzles.add(new NumericPuzzle1(4));
        puzzles.add(new TextPuzzle("un mapa")); // Acertijo textual
        puzzles.add(new TextPuzzle1("incorrectamente"));
        Queue<String> sequence = new LinkedList<>(Arrays.asList("el aire", "el viento", "el sol"));
        puzzles.add(new SequencePuzzle(sequence)); // Acertijo de secuencia
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (currentPuzzleIndex < puzzles.size()) {
            Puzzle current = puzzles.get(currentPuzzleIndex);
            System.out.println(current.getDescription());
            System.out.flush(); // Asegura que todo lo escrito se muestra en consola
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
                    gameState.updateState(currentPuzzleIndex); // Actualiza el estado del juego
                    undoRedoManager.saveState(gameState); // Guarda el estado en el manager de undo/redo
                    currentPuzzleIndex++;
                } else {
                    System.out.println("Incorrecto, intenta de nuevo.");
                }
            }
        }
        System.out.println("¡Felicidades! Has completado todos los acertijos.");
        scanner.close();
    }

    // Método para guardar el juego en un archivo de texto, permitiendo elegir la ubicación de guardado
    private void saveGame() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Elige dónde guardar el progreso");

        // Definir el filtro para guardar archivos de texto (.txt)
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos de texto", "txt"));

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            // Asegurarse de que el archivo tenga la extensión .txt
            if (!fileToSave.getAbsolutePath().endsWith(".txt")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".txt");
            }

            gameState.saveToFile(fileToSave.getAbsolutePath()); // Guardamos el juego en la ruta elegida
            System.out.println("Progreso guardado en: " + fileToSave.getAbsolutePath());
            System.out.flush(); // Asegura que la salida se muestre inmediatamente
        }
    }

    // Método para cargar el juego desde un archivo de texto, permitiendo elegir el archivo
    private void loadGame() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Elige un archivo de guardado");

        // Definir el filtro para cargar solo archivos de texto (.txt)
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos de texto", "txt"));

        int userSelection = fileChooser.showOpenDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToOpen = fileChooser.getSelectedFile();
            GameState loadedState = GameState.loadFromFile(fileToOpen.getAbsolutePath());

            if (loadedState != null) {
                gameState = loadedState; // Restaurar el estado del juego
                currentPuzzleIndex = gameState.getCurrentPuzzleIndex();
                System.out.println("Progreso cargado desde: " + fileToOpen.getAbsolutePath());
            } else {
                System.out.println("No se pudo cargar el progreso.");
            }
            System.out.flush(); // Asegura que la salida se muestre inmediatamente
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
        System.out.flush(); // Asegura que la salida se muestre inmediatamente
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
        System.out.flush(); // Asegura que la salida se muestre inmediatamente
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            System.out.println("resuelve los acertijoso para ganar el juego.\n" +
                    "escribe guardar para guardar tu progreso.\n" +
                    "escribe cargar para cargar un progreso guardado.\n" +
                    "escribe retroceder para devolverte a una pregunta resuelta.\n" +
                    "escribe avanzar para avanzar a una pregunta resuelta.\n");
            Game game = new Game();
            game.start();
        });
    }

}
