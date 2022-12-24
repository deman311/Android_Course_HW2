package com.example.class23a_and_hw1;

import com.google.android.gms.maps.model.LatLng;

public class HighScore {
    String name;
    public int score, place;
    LatLng position;

    public HighScore(int score, String name, int place, LatLng position) {
        this.score = score;
        this.name = name;
        this.place = place;
        this.position = position;
    }

    public LatLng getPosition() {
        return position;
    }

    public HighScore setPosition(LatLng position) {
        this.position = position;
        return this;
    }

    public HighScore() {
    }

    public String getName() {
        return name;
    }

    public HighScore setName(String name) {
        this.name = name;
        return this;
    }

    public int getScore() {
        return score;
    }

    public HighScore setScore(int score) {
        this.score = score;
        return this;
    }

    public int getPlace() {
        return place;
    }

    public HighScore setPlace(int place) {
        this.place = place;
        return this;
    }
}
