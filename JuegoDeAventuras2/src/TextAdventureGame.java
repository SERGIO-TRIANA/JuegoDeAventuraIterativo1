import java.io.*;
import java.util.*;

// Clase abstracta base para los acertijos
abstract class Puzzle {
    protected String description; // Descripción del acertijo

    // Método abstracto para que cada tipo de acertijo implemente su propia lógica de solución
    public abstract boolean solve(String solution);

    public String getDescription() {
        return description;
    }
}

// Acertijo numérico, donde se espera que el jugador ingrese un número correcto
class NumericPuzzle extends Puzzle {
    private int correctAnswer; // La respuesta correcta numérica

    public NumericPuzzle(int correctAnswer) {
        this.correctAnswer = correctAnswer;
        this.description = "Resuelve el acertijo numérico: ¿Cuál es el número correcto?";
    }

    @Override
    public boolean solve(String solution) {
        try {
            int userAnswer = Integer.parseInt(solution); // Convertir la entrada en un número
            return userAnswer == correctAnswer; // Comparar con la respuesta correcta
        } catch (NumberFormatException e) {
            return false; // Si la entrada no es un número válido
        }
    }
}

// Acertijo textual, donde el jugador debe introducir una palabra o frase
class TextPuzzle extends Puzzle {
    private String correctAnswer; // La respuesta correcta en texto

    public TextPuzzle(String correctAnswer) {
        this.correctAnswer = correctAnswer.toLowerCase(); // Convertir la respuesta a minúsculas para facilitar la comparación
        this.description = "Resuelve el acertijo textual: Introduce la palabra correcta.";
    }

    @Override
    public boolean solve(String solution) {
        return correctAnswer.equals(solution.toLowerCase()); // Ignorar mayúsculas/minúsculas al comparar
    }
}

// Acertijo de secuencia, donde el jugador debe seguir una serie de pasos en orden correcto
class SequencePuzzle extends Puzzle {
    private Queue<String> correctSequence; // Secuencia correcta de pasos
    private Queue<String> remainingSequence; // Secuencia que se irá verificando paso por paso

    public SequencePuzzle(Queue<String> correctSequence) {
        this.correctSequence = new LinkedList<>(correctSequence); // Crear una copia de la secuencia correcta
        this.remainingSequence = new LinkedList<>(correctSequence); // Inicialmente, toda la secuencia está sin resolver
        this.description = "Resuelve el acertijo de secuencia: Introduce los pasos correctos en orden.";
    }

    @Override
    public boolean solve(String solution) {
        if (remainingSequence.isEmpty()) {
            return false; // Si no quedan pasos por resolver, no es necesario más entradas
        }

        String expectedStep = remainingSequence.peek(); // Tomar el siguiente paso esperado
        if (solution.equals(expectedStep)) {
            remainingSequence.poll(); // Eliminar el paso de la secuencia
            return remainingSequence.isEmpty(); // Si ya no quedan más pasos, se ha resuelto el acertijo
        } else {
            System.out.println("Incorrecto, intenta de nuevo desde el primer paso.");
            resetPuzzle(); // Reiniciar si el jugador falla
            return false;
        }
    }

    private void resetPuzzle() {
        remainingSequence = new LinkedList<>(correctSequence); // Reiniciar la secuencia para intentarlo de nuevo
    }
}

// Clase que gestiona el estado del juego, incluyendo la funcionalidad de guardar, cargar, retroceder y avanzar
class GameState {
    private Stack<Integer> previousPuzzleIndices;  // Pila para almacenar los acertijos anteriores (retroceder)
    private Stack<Integer> nextPuzzleIndices;      // Pila para almacenar los acertijos a los que se puede avanzar
    private int currentPuzzleIndex;                // Índice del acertijo actual
    private List<Boolean> puzzleSolvedStatus;      // Lista que indica si los acertijos han sido resueltos

    public GameState() {
        previousPuzzleIndices = new Stack<>();
        nextPuzzleIndices = new Stack<>();
        puzzleSolvedStatus = new ArrayList<>();
    }

    public int getCurrentPuzzleIndex() {
        return currentPuzzleIndex;
    }

    // Actualiza el estado del juego para avanzar al siguiente acertijo
    public void updateState(int currentPuzzleIndex) {
        previousPuzzleIndices.push(this.currentPuzzleIndex);  // Guardar el acertijo actual antes de avanzar
        this.currentPuzzleIndex = currentPuzzleIndex;          // Actualizar el índice del acertijo actual
        if (currentPuzzleIndex >= puzzleSolvedStatus.size()) {
            puzzleSolvedStatus.add(true);  // Marcar como resuelto si es un nuevo acertijo
        }
    }

    // Retrocede al acertijo anterior si es posible
    public boolean undo() {
        if (!previousPuzzleIndices.isEmpty()) {
            nextPuzzleIndices.push(currentPuzzleIndex);  // Guardar el índice actual para poder avanzar después
            currentPuzzleIndex = previousPuzzleIndices.pop();  // Retroceder
            return true;
        }
        return false;
    }

    // Avanza al siguiente acertijo si es posible
    public boolean redo() {
        if (!nextPuzzleIndices.isEmpty()) {
            previousPuzzleIndices.push(currentPuzzleIndex);  // Guardar el índice actual para poder retroceder
            currentPuzzleIndex = nextPuzzleIndices.pop();    // Avanzar
            return true;
        }
        return false;
    }

    // Guardar el estado del juego en un archivo
    public void saveToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(currentPuzzleIndex + "\n");  // Guardar el índice del acertijo actual
            for (Boolean solved : puzzleSolvedStatus) {
                writer.write(solved ? "1" : "0");  // Guardar si los acertijos han sido resueltos
            }
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Cargar el estado del juego desde un archivo
    public static GameState loadFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            int puzzleIndex = Integer.parseInt(reader.readLine());  // Leer el índice del acertijo actual
            List<Boolean> solvedStatus = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                for (char c : line.toCharArray()) {
                    solvedStatus.add(c == '1');  // Cargar el estado de los acertijos resueltos
                }
            }
            GameState gameState = new GameState();
            gameState.currentPuzzleIndex = puzzleIndex;
            gameState.puzzleSolvedStatus = solvedStatus;
            return gameState;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

// Clase principal que ejecuta el juego
public class TextAdventureGame {
    public static void main(String[] args) {
        List<Puzzle> puzzles = new ArrayList<>();  // Lista para almacenar los acertijos del juego

        // Crear y añadir los acertijos a la lista
        puzzles.add(new NumericPuzzle(42));  // Acertijo numérico
        puzzles.add(new TextPuzzle("java"));  // Acertijo textual

        // Crear un acertijo de secuencia
        Queue<String> sequence = new LinkedList<>(Arrays.asList("paso1", "paso2", "paso3"));
        puzzles.add(new SequencePuzzle(sequence));  // Acertijo de secuencia

        GameState gameState = new GameState();  // Estado del juego

        Scanner scanner = new Scanner(System.in);

        // Bucle para interactuar con los acertijos
        while (gameState.getCurrentPuzzleIndex() < puzzles.size()) {
            Puzzle currentPuzzle = puzzles.get(gameState.getCurrentPuzzleIndex());  // Obtener el acertijo actual
            System.out.println(currentPuzzle.getDescription());  // Mostrar la descripción del acertijo
            String solution = scanner.nextLine();  // Leer la solución introducida por el jugador

            if (currentPuzzle.solve(solution)) {
                System.out.println("¡Correcto!");
                gameState.updateState(gameState.getCurrentPuzzleIndex() + 1);  // Avanzar al siguiente acertijo
            } else {
                System.out.println("Incorrecto, intenta de nuevo.");
            }
        }

        System.out.println("¡Has completado todos los acertijos!");  // Fin del juego
    }
}
