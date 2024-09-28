import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Arrays;

class NumericPuzzle extends Puzzle {
    private final int correctAnswer;

    public NumericPuzzle(int correctAnswer) {
        this.correctAnswer = correctAnswer;
        this.description = "Resuelve este acertijo numérico: ¿Cuál es la respuesta? (Tip: Es un número)";
    }
//current
    @Override
    public boolean solve(String solution) {
        try {
            int answer = Integer.parseInt(solution);
            return answer == correctAnswer;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
