package com.example.class23a_and_hw1.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.class23a_and_hw1.Managers.GameManager;
import com.example.class23a_and_hw1.Managers.HighScoreManager;
import com.example.class23a_and_hw1.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HighScoreManager.loadHighScores(this);

        findViewById(R.id.menu_BTN_play_arrow_slow).setOnClickListener(oc -> {
            Intent intent = new Intent(getApplicationContext(), GameActivity.class);
            intent.putExtra("mode", GameManager.MODE_ARROWS_SLOW);
            startActivity(intent);
        });
        findViewById(R.id.menu_BTN_play_arrow_fast).setOnClickListener(oc -> {
            Intent intent = new Intent(getApplicationContext(), GameActivity.class);
            intent.putExtra("mode", GameManager.MODE_ARROWS_FAST);
            startActivity(intent);
        });
        findViewById(R.id.menu_BTN_play_sensor).setOnClickListener(oc -> {
            Intent intent = new Intent(getApplicationContext(), GameActivity.class);
            intent.putExtra("mode", GameManager.MODE_SENSORS);
            startActivity(intent);
        });
        findViewById(R.id.menu_BTN_play_scoreboard).setOnClickListener(oc -> {
            Intent intent = new Intent(getApplicationContext(), ScoreActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HighScoreManager.saveHighScores();
    }
}