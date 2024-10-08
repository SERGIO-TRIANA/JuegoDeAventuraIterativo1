class TextPuzzle extends Puzzle {
    private final String correctAnswer;

    public TextPuzzle(String correctAnswer) {
        this.correctAnswer = correctAnswer.toLowerCase();
        this.description = "Tengo ciudades, pero no casas. Tengo montañas, pero no árboles. ¿Qué soy?";
    }

    @Override
    public boolean solve(String solution) {
        return correctAnswer.equals(solution.toLowerCase());
    }
}

