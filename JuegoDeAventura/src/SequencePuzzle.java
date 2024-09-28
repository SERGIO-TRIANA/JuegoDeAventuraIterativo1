import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Arrays;


class SequencePuzzle extends Puzzle {
    private Queue<String> correctSequence;  // Secuencia correcta
    private Queue<String> remainingSequence; // Copia de la secuencia correcta para ir verificando
    private Queue<String> playerSequence;    // Secuencia que introduce el jugador

    public SequencePuzzle(Queue<String> correctSequence) {
        this.correctSequence = new LinkedList<>(correctSequence);  // Secuencia original
        this.remainingSequence = new LinkedList<>(correctSequence); // Secuencia que se irá verificando
        this.playerSequence = new LinkedList<>();  // Secuencia ingresada por el jugador
        this.description = "Resuelve este acertijo de secuencia: Introduce los pasos correctos en orden.";
    }

    @Override
    public boolean solve(String solution) {
        if (remainingSequence.isEmpty()) {
            return false; // Ya no hay más pasos por verificar, no necesita más soluciones.
        }

        String expectedStep = remainingSequence.peek(); // Tomar el siguiente paso esperado (sin eliminarlo aún)
        if (solution.equals(expectedStep)) {
            playerSequence.add(solution);    // Agregar el paso correcto a la secuencia del jugador
            remainingSequence.poll();        // Eliminar este paso de la secuencia esperada
            if (remainingSequence.isEmpty()) {
                return true;  // Si la secuencia se ha completado correctamente, el acertijo está resuelto
            }
            System.out.println("Correcto, sigue con el siguiente paso.");
            return false;  // Aún hay más pasos por resolver
        } else {
            System.out.println("Incorrecto, intenta de nuevo desde el primer paso.");
            resetPuzzle();  // Reiniciar el acertijo si el jugador falla
            return false;
        }
    }

    private void resetPuzzle() {
        playerSequence.clear();              // Limpiar los pasos ingresados por el jugador
        remainingSequence = new LinkedList<>(correctSequence); // Reiniciar la secuencia esperada
    }
}
