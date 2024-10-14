import java.io.*;
import java.util.List;
import java.util.ArrayList;

class GameState {
    private int currentPuzzleIndex;
    private List<Boolean> puzzleSolvedStatus;

    public GameState() {
        puzzleSolvedStatus = new ArrayList<>();
    }

    public GameState(int index, List<Boolean> status) {
        this.currentPuzzleIndex = index;
        this.puzzleSolvedStatus = status;
    }

    public int getCurrentPuzzleIndex() {
        return currentPuzzleIndex;
    }

    public List<Boolean> getPuzzleSolvedStatus() {
        return puzzleSolvedStatus;
    }

    public void updateState(int currentPuzzleIndex) {
        this.currentPuzzleIndex = currentPuzzleIndex;
        if (currentPuzzleIndex >= puzzleSolvedStatus.size()) {
            puzzleSolvedStatus.add(true);
        } else {
            puzzleSolvedStatus.set(currentPuzzleIndex, true);
        }
    }

    public void saveToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Guardar el índice actual
            writer.write(currentPuzzleIndex + "\n");
            // Guardar el estado de los acertijos resueltos
            for (Boolean solved : puzzleSolvedStatus) {
                writer.write(solved ? "1" : "0");
            }
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameState loadFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            // Leer el índice del acertijo actual
            int puzzleIndex = Integer.parseInt(reader.readLine());
            List<Boolean> solvedStatus = new ArrayList<>();
            // Leer la línea con el estado de los acertijos
            String statusLine = reader.readLine();
            if (statusLine != null) {
                for (char c : statusLine.toCharArray()) {
                    solvedStatus.add(c == '1');
                }
            }
            return new GameState(puzzleIndex, solvedStatus);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
