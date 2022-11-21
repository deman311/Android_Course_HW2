package com.example.class23a_and_hw1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private int isCreated = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageManager.generateImages(this);
        GameManager.begin(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        GameManager.stopTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // avoid calling timer twice after onCreate
        if (isCreated == 0)
            isCreated++;
        else
            GameManager.startTimer(this);
    }
}