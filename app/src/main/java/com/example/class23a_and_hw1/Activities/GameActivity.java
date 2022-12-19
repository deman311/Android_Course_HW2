package com.example.class23a_and_hw1.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.class23a_and_hw1.Managers.GameManager;
import com.example.class23a_and_hw1.Managers.ImageManager;
import com.example.class23a_and_hw1.R;

public class GameActivity extends AppCompatActivity {

    private int isCreated = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        int mode = intent.getIntExtra("mode", -1);

        ImageManager.generateImages(this);
        GameManager.begin(this, mode);
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
            GameManager.startTimer();
    }
}