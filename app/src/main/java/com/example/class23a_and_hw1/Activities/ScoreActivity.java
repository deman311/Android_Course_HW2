package com.example.class23a_and_hw1.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.class23a_and_hw1.Adapter_Scoreboard;
import com.example.class23a_and_hw1.Managers.HighScoreManager;
import com.example.class23a_and_hw1.R;

public class ScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        RecyclerView highScores = findViewById(R.id.score_FRG_highscores);
        highScores.setLayoutManager(new LinearLayoutManager(this));
        highScores.setAdapter(new Adapter_Scoreboard(HighScoreManager.getHighScores()));
    }
}