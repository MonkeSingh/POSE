package game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Highscores {
    private String FILE_NAME = "highscores.txt";
    private List<Score> scores = new ArrayList<>();
    private List<Score> highScore = new ArrayList<>();

    public Highscores() {
        readScoresFromFile();
    }

    public void addScore(String name, double seconds) {
        scores.add(new Score(name, (double) Math.round(seconds * 100) / 100));
        sortScores();
        saveScoresToFile();
    }

    public List<Score> getScores() {
        return Collections.unmodifiableList(scores);
    }

    private void readScoresFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            int lines = 0;
            while ((line = br.readLine()) != null) {
                lines++;
                if(lines <= 10){
                    String[] parts = line.split(":");
                    if (parts.length == 2) {
                        String name = parts[0];
                        double seconds = Double.parseDouble(parts[1]);
                        scores.add(new Score(name, seconds));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading scores from file: " + e.getMessage());
        }

        sortScores();
    }

    private void saveScoresToFile() {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            for (Score score : scores) {
                writer.write(score.getName() + ":" + score.getSeconds() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing scores to file: " + e.getMessage());
        }
    }

    private void sortScores() {
        Collections.sort(scores, Comparator.comparingDouble(Score::getSeconds));
    }

    public List<Score> getHighScores() {
        return getScores();
    }


static class Score {
    private String name;
    private double seconds;

    public Score(String name, double seconds) {
        this.name = name;
        this.seconds = seconds;
    }

    public String getName() {
        return name;
    }

    public double getSeconds() {
        return seconds;
    }
}

//    public static void main(String[] args){
//        Highscores highscores = new Highscores();
//
//        highscores.addScore("Player", 100);
//
//        List<Highscores.Score> scores = highscores.getScores();
//        for (Highscores.Score score : scores) {
//            System.out.println(score.getName() + ": " + score.getSeconds());
//        }
//
//
//    }

}