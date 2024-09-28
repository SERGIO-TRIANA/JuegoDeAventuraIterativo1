import java.util.*;
import java.io.*;

public class Game {
    private List<Puzzle> puzzles = new ArrayList<>();
    private Stack<String> undoStack = new Stack<>();
    private Stack<String> redoStack = new Stack<>();
    private Player player;
    private int currentPuzzleIndex = 0;
    private final int MAX_STACK_SIZE = 5; // Limit undo/redo stack size

    public Game(Player player) {
        this.player = player;
        initializePuzzles();
    }

    private void initializePuzzles() {
        puzzles.add(new Puzzle("What is 2 + 2?", "numeric", "4"));
        puzzles.add(new Puzzle("What is the capital of France?", "text", "Paris"));
        puzzles.add(new Puzzle("Arrange these steps: Go North, Go South", List.of("Go North", "Go South")));
        // Add more puzzles
    }

    public void play() {
        while (currentPuzzleIndex < puzzles.size()) {
            Puzzle currentPuzzle = puzzles.get(currentPuzzleIndex);
            System.out.println(currentPuzzle.getQuestion());

            Scanner sc = new Scanner(System.in);
            String playerAnswer = sc.nextLine();

            if (currentPuzzle.checkAnswer(playerAnswer)) {
                System.out.println("Correct!");
                currentPuzzle.markAsSolved();
                recordAction(playerAnswer);
                currentPuzzleIndex++;
            } else {
                System.out.println("Incorrect. Try again.");
            }
        }
        System.out.println("Congratulations! You've solved all the puzzles.");
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            String lastAction = undoStack.pop();
            redoStack.push(lastAction);
            currentPuzzleIndex--;
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            String lastAction = redoStack.pop();
            undoStack.push(lastAction);
            currentPuzzleIndex++;
        }
    }

    public void recordAction(String action) {
        if (undoStack.size() >= MAX_STACK_SIZE) {
            undoStack.remove(0); // Maintain stack limit
        }
        undoStack.push(action);
        redoStack.clear(); // Clear redo stack when a new action is taken
    }

    public void saveProgress() throws IOException {
        try (FileWriter writer = new FileWriter("saves/savegame.txt")) {
            writer.write("Current Puzzle Index: " + currentPuzzleIndex + "\n");
            writer.write("Player Actions: " + undoStack + "\n");
        }
    }

    public void loadProgress() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("saves/savegame.txt"))) {
            String line = reader.readLine();
            this.currentPuzzleIndex = Integer.parseInt(line.split(": ")[1]);
            // Load other saved data if needed
        }
    }
}

