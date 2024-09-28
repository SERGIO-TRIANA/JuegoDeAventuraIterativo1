abstract class Puzzle {
    protected String description;

    public String getDescription() {
        return description;
    }

    public abstract boolean solve(String solution);
}

