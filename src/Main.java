public class Main {
    public static void main(String[] args) {
        Player player = new Player();
        Game game = new Game(player);

        game.play(); // Start game loop

        // Allow player to undo, redo, save, or load progress
        // Example usage:
        // game.undo();
        // game.saveProgress();
    }
}
