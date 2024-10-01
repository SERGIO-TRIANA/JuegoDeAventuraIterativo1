class NumericPuzzle1 extends Puzzle {
    private final int correctAnswer;

    public NumericPuzzle1(int correctAnswer) {
        this.correctAnswer = correctAnswer;
        this.description = "1. Piensa en un número del 1 al 5. 2. Multiplicalo por 2. 3. Suma 8 unidades. 4. Divide por 2. ¿Cuál es el resultado?";
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
