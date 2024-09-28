class TextPuzzle extends Puzzle {
    private final String correctAnswer;

    public TextPuzzle(String correctAnswer) {
        this.correctAnswer = correctAnswer.toLowerCase();
        this.description = "Resuelve este acertijo textual: Escribe la palabra correcta.";
    }

    @Override
    public boolean solve(String solution) {
        return correctAnswer.equals(solution.toLowerCase());
    }
}

