package save;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.ArrayList;
import java.util.Collections;

public class HighScoreManager {
    private static final String FILE_NAME = "save/highscores.txt";

    /**
     * Prevents helper class from being constructed.
     */
    private HighScoreManager() { }

    /**
     * Returns all scores sorted highest.
     *
     * @return a sorted array list of scores read from the file
     */
    public static ArrayList<Integer> loadScores() {
        FileHandle file = Gdx.files.local(FILE_NAME);
        ArrayList<Integer> scores = new ArrayList<>();

        if (!file.exists()) {
            return scores;
        }

        String[] lines = file.readString().split("\\R");

        for (String line : lines) {
            if (!line.isEmpty()) {
                scores.add(Integer.parseInt(line.trim()));
            }
        }

        scores.sort(Collections.reverseOrder());
        return scores;
    }

    /**
     * Adds a new score and saves file.
     *
     * @param score the score to be added
     */
    public static void addScore(final int score) {
        ArrayList<Integer> scores = loadScores();
        scores.add(score);
        scores.sort(Collections.reverseOrder());

        if (scores.size() > 5) {
            scores = new ArrayList<>(scores.subList(0, 5));
        }

        saveScores(scores);
    }

    /**
     * Writes scores to .txt file.
     *
     * @param scores the array list of scores to be written
     */
    private static void saveScores(final ArrayList<Integer> scores) {
        StringBuilder sb = new StringBuilder();

        for (int score : scores) {
            sb.append(score).append("\n");
        }

        FileHandle file = Gdx.files.local(FILE_NAME);
        file.writeString(sb.toString(), false);
    }
}
