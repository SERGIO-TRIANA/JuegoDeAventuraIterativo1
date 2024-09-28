import java.util.List;

public class Puzzle {
    private String question;
    private String answerType; // "numeric", "text", or "sequence"
    private String correctAnswer;
    private List<String> steps; // For sequence puzzles
    private boolean isSolved = false;

    // Constructor for numeric or text puzzles
    public Puzzle(String question, String answerType, String correctAnswer) {
        this.question = question;
        this.answerType = answerType;
        this.correctAnswer = correctAnswer;
    }

    // Constructor for sequence puzzles
    public Puzzle(String question, List<String> steps) {
        this.question = question;
        this.answerType = "sequence";
        this.steps = steps;
    }

    public boolean checkAnswer(String playerAnswer) {
        if (answerType.equals("numeric") || answerType.equals("text")) {
            return correctAnswer.equalsIgnoreCase(playerAnswer);
        } else if (answerType.equals("sequence")) {
            return steps.equals(List.of(playerAnswer.split(" "))); // Compare sequence
        }
        return false;
    }

    public boolean isSolved() {
        return this.isSolved;
    }

    public void markAsSolved() {
        this.isSolved = true;
    }

    public String getQuestion() {
        return question;
    }


}
