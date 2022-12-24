package com.example.class23a_and_hw1.Managers;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.SharedPreferences;

import com.example.class23a_and_hw1.HighScore;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Random;

public abstract class HighScoreManager {

    private static ArrayList<HighScore> highScores;
    private static Activity mainActivity;

    public static ArrayList<HighScore> generateRandomHighScores() {
        highScores = new ArrayList<>();
        Random rand = new Random();

        // Generate 10 random high-scorers
        highScores.add(new HighScore().setName("MasterYoda3000").setScore(rand.nextInt(1000) + 1)
                .setPosition(new LatLng(rand.nextInt(50) - 50, rand.nextInt(120) - 100)));
        highScores.add(new HighScore().setName("Bob").setScore(rand.nextInt(1000) + 1)
                .setPosition(new LatLng(rand.nextInt(50) - 50, rand.nextInt(120) - 100)));
        highScores.add(new HighScore().setName("St0rX").setScore(rand.nextInt(1000) + 1)
                .setPosition(new LatLng(rand.nextInt(50) - 50, rand.nextInt(120) - 100)));
        highScores.add(new HighScore().setName("Dima").setScore(rand.nextInt(1000) + 1)
                .setPosition(new LatLng(rand.nextInt(50) - 50, rand.nextInt(120) - 100)));
        highScores.add(new HighScore().setName("Barbi").setScore(rand.nextInt(1000) + 1)
                .setPosition(new LatLng(rand.nextInt(50) - 50, rand.nextInt(120) - 100)));
        highScores.add(new HighScore().setName("Ilan").setScore(rand.nextInt(1000) + 1)
                .setPosition(new LatLng(rand.nextInt(50) - 50, rand.nextInt(120) - 100)));
        highScores.add(new HighScore().setName("M3gaM4n").setScore(rand.nextInt(1000) + 1)
                .setPosition(new LatLng(rand.nextInt(50) - 50, rand.nextInt(120) - 100)));
        highScores.add(new HighScore().setName("Digimon123").setScore(rand.nextInt(1000) + 1)
                .setPosition(new LatLng(rand.nextInt(50) - 50, rand.nextInt(120) - 100)));
        highScores.add(new HighScore().setName("Drakus26").setScore(rand.nextInt(1000) + 1)
                .setPosition(new LatLng(rand.nextInt(50) - 50, rand.nextInt(120) - 100)));
        highScores.add(new HighScore().setName("AC2135").setScore(rand.nextInt(1000) + 1)
                .setPosition(new LatLng(rand.nextInt(50) - 50, rand.nextInt(120) - 100)));
        highScores.sort((c1, c2) -> c2.score - c1.score);

        for (int i = 0; i < highScores.size(); i++) {
            highScores.get(i).setPlace(i + 1);
        }
        return highScores;
    }

    public static ArrayList<HighScore> getHighScores() {
        return highScores;
    }

    public static void saveHighScores() {
        // Create a new SharedPreferences object
        SharedPreferences sharedPreferences = mainActivity.getSharedPreferences("highScores", MODE_PRIVATE);

        // Get a SharedPreferences.Editor object to make changes to the shared preferences
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Clear the shared preferences of any existing data
        editor.clear();

        // Save the size of the ArrayList as an int
        editor.putInt("size", highScores.size());

        // Save each HighScore object in the ArrayList to the shared preferences
        for (int i = 0; i < highScores.size(); i++) {
            editor.putInt("score" + i, highScores.get(i).getScore());
            editor.putString("name" + i, highScores.get(i).getName());
            editor.putInt("place" + i, highScores.get(i).getPlace());
            editor.putString("position" + i, highScores.get(i).getPosition().latitude + "/" + highScores.get(i).getPosition().longitude);
        }

        // Commit the changes to the shared preferences
        editor.apply();
    }

    public static ArrayList<HighScore> loadHighScores(Activity activity) {
        mainActivity = activity;

        // Create a new ArrayList to store the high scores
        ArrayList<HighScore> highScores = new ArrayList<>();

        // Get the SharedPreferences object
        SharedPreferences sharedPreferences = mainActivity.getSharedPreferences("highScores", MODE_PRIVATE);

        if (!sharedPreferences.contains("highScores")) {
            return generateRandomHighScores();
        }

        // Get the size of the saved high scores
        int size = sharedPreferences.getInt("size", 0);

        // Retrieve the saved high scores and add them to the ArrayList
        for (int i = 0; i < size; i++) {
            int score = sharedPreferences.getInt("score" + i, 0);
            String name = sharedPreferences.getString("name" + i, "");
            int place = sharedPreferences.getInt("place" + i, 0);
            String positionString = sharedPreferences.getString("position" + i, "");
            LatLng position = new LatLng(Float.parseFloat(positionString.split("/")[0]), Float.parseFloat(positionString.split("/")[1]));

            highScores.add(new HighScore(score, name, place, position));
        }

        return highScores;
    }
}
