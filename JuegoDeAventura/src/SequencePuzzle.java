
import java.util.Queue;
import java.util.LinkedList;



class SequencePuzzle extends Puzzle {
    private final Queue<String> correctSequence;  // Secuencia correcta
    private Queue<String> remainingSequence; // Copia de la secuencia correcta para ir verificando
    private final Queue<String> playerSequence;    // Secuencia que introduce el jugador

    public SequencePuzzle(Queue<String> correctSequence) {
        this.correctSequence = new LinkedList<>(correctSequence);  // Secuencia original
        this.remainingSequence = new LinkedList<>(correctSequence); // Secuencia que se irá verificando
        this.playerSequence = new LinkedList<>();  // Secuencia ingresada por el jugador
        this.description = """
                responde en orden cada uno de estos
                ¿Qué respiras cada día?
                ¿Qué se mueve, pero no puedes ver?
                ¿Qué aparece en el cielo y hace las plantas crecer?""";
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
