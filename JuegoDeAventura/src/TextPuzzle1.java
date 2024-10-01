class TextPuzzle1 extends Puzzle {
    private final String correctAnswer;

    public TextPuzzle1(String correctAnswer) {
        this.correctAnswer = correctAnswer.toLowerCase();
        this.description = "¿Qué palabra se escribe incorrectamente en todos los diccionarios?";
    }

    @Override
    public boolean solve(String solution) {
        return correctAnswer.equals(solution.toLowerCase());
    }
}
