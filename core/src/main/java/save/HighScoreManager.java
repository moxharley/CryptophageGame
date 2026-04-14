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
    public static ArrayList<ScoreEntry> loadScores() {
        FileHandle file = Gdx.files.local(FILE_NAME);
        ArrayList<ScoreEntry> scores = new ArrayList<>();

        if (!file.exists()) {
            return scores;
        }

        String[] lines = file.readString().split("\\R");

        for (String line : lines) {
            if (!line.isEmpty()) {
                String[] elements = line.split(",");
                String name = elements[0];
                int score = Integer.parseInt(elements[1]);
                scores.add(new ScoreEntry(score, name));
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
    public static void addScore(final ScoreEntry score) {
        ArrayList<ScoreEntry> scores = loadScores();
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
    private static void saveScores(final ArrayList<ScoreEntry> scores) {
        StringBuilder sb = new StringBuilder();

        for (ScoreEntry score : scores) {
            sb.append(score.getName());
            sb.append(",");
            sb.append(score.getScore());
            sb.append("\n");
        }

        FileHandle file = Gdx.files.local(FILE_NAME);
        file.writeString(sb.toString(), false);
    }
}
