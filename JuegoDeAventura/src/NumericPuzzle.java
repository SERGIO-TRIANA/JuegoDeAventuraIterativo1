

class NumericPuzzle extends Puzzle {
    private final int correctAnswer;

    public NumericPuzzle(int correctAnswer) {
        this.correctAnswer = correctAnswer;
        this.description = "¿Qué número es mayor que 3 y menor que 7 pero es numero primo?";
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

