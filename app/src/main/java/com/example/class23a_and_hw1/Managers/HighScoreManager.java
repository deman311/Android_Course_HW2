package com.example.class23a_and_hw1.Managers;

import com.example.class23a_and_hw1.HighScore;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Random;

public abstract class HighScoreManager {

    private static ArrayList<HighScore> scores;

    public static ArrayList<HighScore> generateRandomHighScores() {
        scores = new ArrayList<>();
        Random rand = new Random();

        // Generate 10 random high-scorers
        scores.add(new HighScore().setName("MasterYoda3000").setScore(rand.nextInt(1000) + 1)
                .setPosition(new LatLng(rand.nextInt(50) - 50, rand.nextInt(120) - 100)));
        scores.add(new HighScore().setName("Bob").setScore(rand.nextInt(1000) + 1)
                .setPosition(new LatLng(rand.nextInt(50) - 50, rand.nextInt(120) - 100)));
        scores.add(new HighScore().setName("St0rX").setScore(rand.nextInt(1000) + 1)
                .setPosition(new LatLng(rand.nextInt(50) - 50, rand.nextInt(120) - 100)));
        scores.add(new HighScore().setName("Dima").setScore(rand.nextInt(1000) + 1)
                .setPosition(new LatLng(rand.nextInt(50) - 50, rand.nextInt(120) - 100)));
        scores.add(new HighScore().setName("Barbi").setScore(rand.nextInt(1000) + 1)
                .setPosition(new LatLng(rand.nextInt(50) - 50, rand.nextInt(120) - 100)));
        scores.add(new HighScore().setName("Ilan").setScore(rand.nextInt(1000) + 1)
                .setPosition(new LatLng(rand.nextInt(50) - 50, rand.nextInt(120) - 100)));
        scores.add(new HighScore().setName("M3gaM4n").setScore(rand.nextInt(1000) + 1)
                .setPosition(new LatLng(rand.nextInt(50) - 50, rand.nextInt(120) - 100)));
        scores.add(new HighScore().setName("Digimon123").setScore(rand.nextInt(1000) + 1)
                .setPosition(new LatLng(rand.nextInt(50) - 50, rand.nextInt(120) - 100)));
        scores.add(new HighScore().setName("Drakus26").setScore(rand.nextInt(1000) + 1)
                .setPosition(new LatLng(rand.nextInt(50) - 50, rand.nextInt(120) - 100)));
        scores.add(new HighScore().setName("AC2135").setScore(rand.nextInt(1000) + 1)
                .setPosition(new LatLng(rand.nextInt(50) - 50, rand.nextInt(120) - 100)));
        scores.sort((c1, c2) -> c2.score - c1.score);

        for (int i = 0; i < scores.size(); i++) {
            scores.get(i).setPlace(i + 1);
        }
        return scores;
    }

    public static ArrayList<HighScore> getHighScores() {
        return scores;
    }
}
