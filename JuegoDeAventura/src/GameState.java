import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Arrays;


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

    public void updateState(int currentPuzzleIndex) {
        this.currentPuzzleIndex = currentPuzzleIndex;
        if (currentPuzzleIndex >= puzzleSolvedStatus.size()) {
            puzzleSolvedStatus.add(true);
        }
    }

    public void saveToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(currentPuzzleIndex + "\n");
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
            int puzzleIndex = Integer.parseInt(reader.readLine());
            List<Boolean> solvedStatus = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                solvedStatus.add(line.equals("1"));
            }
            return new GameState(puzzleIndex, solvedStatus);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
