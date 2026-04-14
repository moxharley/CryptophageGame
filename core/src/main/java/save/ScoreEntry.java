package save;

public class ScoreEntry implements Comparable<ScoreEntry>{
    private int score;
    private String name;

    /**
     * Instantiates a score entry object.
     *
     * @param score the integer score
     * @param name the username
     */
    public ScoreEntry(final int score, final String name) {
        this.score = score;
        this.name = name;
    }

    /**
     * Returns a string representation of this player score.
     *
     * @return a formatted string showing the name and score
     */
    @Override
    public String toString() {
        return (this.name + " : " + this.score);
    }

    /**
     * Compares the score entries by score.
     *
     * @param that the object to be compared
     * @return a positive number if greater, negative if lesser
     */
    @Override
    public int compareTo(final ScoreEntry that) {
        return Integer.compare(this.score, that.score);
    }

    /**
     * Returns the score of this score entry.
     *
     * @return the integer score
     */
    public int getScore() {
        return score;
    }

    /**
     * Returns the name associated to this score entry.
     *
     * @return the string name
     */
    public String getName() {
        return name;
    }
}
