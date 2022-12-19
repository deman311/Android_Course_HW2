package com.example.class23a_and_hw1.Managers;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.class23a_and_hw1.HighScore;
import com.example.class23a_and_hw1.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public abstract class GameManager {

    public static final int MODE_ARROWS_SLOW = 1, MODE_ARROWS_FAST = 2, MODE_SENSORS = 3;

    // Because only the MainActivity can call some crucial functions that I want to use such as 'findViewById'
    // I will save the main activity and use them through it.
    private static Activity gameActivity;

    private static int SPEED = 1000; // in milliseconds (default)
    private static Timer mainTimer;
    private static Handler explosionHandler;
    private static Runnable explosionRunnable;

    private static int aliens = 0;
    private static int odometer = 0;

    public static SensorControl sensorControl;

    public static void begin(Activity activity, int mode) {
        gameActivity = activity;
        resetView();

        switch (mode) {
            case 2: SPEED = 500;    // change only the speed for case 2 and proceed to case 1
            case 1: defineArrowControls(); break;
            case 3: defineSensorControls(); break;
        }
        startTimer();
    }

    private static void defineArrowControls() {
        ExtendedFloatingActionButton rBtn = gameActivity.findViewById(R.id.main_BTN_right);
        ExtendedFloatingActionButton lBtn = gameActivity.findViewById(R.id.main_BTN_left);
        rBtn.setOnClickListener(ae -> moveRight());
        lBtn.setOnClickListener(ae -> moveLeft());
    }

    public static void defineSensorControls() {
        // hide control buttons
        ExtendedFloatingActionButton rBtn = gameActivity.findViewById(R.id.main_BTN_right);
        ExtendedFloatingActionButton lBtn = gameActivity.findViewById(R.id.main_BTN_left);
        rBtn.setVisibility(View.INVISIBLE);
        lBtn.setVisibility(View.INVISIBLE);

         sensorControl = new SensorControl(gameActivity, new SensorControl.Callback_Controls() {
            @Override
            public void onLeft() {
                moveLeft();
            }

            @Override
            public void onRight() {
                moveRight();
            }
        });
    }

    private static void moveRight() {
        for (int i = 0; i < ImageManager.COLS - 1; i++) {
            ImageView iv = ImageManager.imageGrid[ImageManager.ROWS][i];
            if (iv.getVisibility() == View.VISIBLE) {
                iv.setVisibility(View.INVISIBLE);
                ImageManager.imageGrid[ImageManager.ROWS][i+1].setVisibility(View.VISIBLE);
                checkHit();
                break;
            }
        }
    }

    private static void moveLeft() {
        for (int i = 1; i < ImageManager.COLS; i++) {
            ImageView iv = ImageManager.imageGrid[ImageManager.ROWS][i];
            if (iv.getVisibility() == View.VISIBLE) {
                iv.setVisibility(View.INVISIBLE);
                ImageManager.imageGrid[ImageManager.ROWS][i-1].setVisibility(View.VISIBLE);
                checkHit();
                break;
            }
        }
    }

    private static void checkHit() {
        for (int j = 0; j < ImageManager.COLS; j++)
            if (ImageManager.imageGrid[ImageManager.ROWS - 1][j].getVisibility() == View.VISIBLE
                && ImageManager.imageGrid[ImageManager.ROWS][j].getVisibility() == View.VISIBLE)

                if (ImageManager.imageGrid[ImageManager.ROWS - 1][j].getTag().equals("ASTEROID"))
                    hit(j, ImageManager.ROWS - 1);
                else { // coin
                    MediaPlayer.create(gameActivity, R.raw.collect).start();
                    ((TextView)gameActivity.findViewById(R.id.main_TXT_aliens)).setText(""+ aliens++);
                    ImageManager.imageGrid[ImageManager.ROWS - 1][j].setVisibility(View.INVISIBLE);
                }
    }

    private static void updateGame() {
        updateMoves();
        spawnAsteroidsAndAliens();
        ((TextView)gameActivity.findViewById(R.id.main_TXT_odometer)).setText(""+odometer++);
    }

    private static void spawnAsteroidsAndAliens() {
        Random rand = new Random();
        for (int i = 0; i < ImageManager.COLS; i++) {
            if (rand.nextInt(5) == 0) { // 20% to spawn
                ImageManager.imageGrid[0][i].setVisibility(View.VISIBLE);
                if (rand.nextInt(4) == 0) { // 25% to be alien
                    ImageManager.imageGrid[0][i].setImageDrawable(gameActivity.getDrawable(R.drawable.ic_main_alien));
                    ImageManager.imageGrid[0][i].setTag("ALIEN");   // used for comparison later
                }
                else {
                    ImageManager.imageGrid[0][i].setImageDrawable(gameActivity.getDrawable(R.drawable.ic_main_asteroid));
                    ImageManager.imageGrid[0][i].setTag("ASTEROID");
                }
            }
        }
    }

    /***
     * The amount of rows int the imageview matrix is always ROWS + 1, because we use an extra row
     * to represent the player ship row.
     */
    private static void updateMoves() {
        for (int i = ImageManager.ROWS - 1; i >= 0; i--)    // upside down iteration so we don't drop all rows at once.
            for (int j = 0; j < ImageManager.COLS; j++) {
                ImageView iv = ImageManager.imageGrid[i][j];
                if (iv.getVisibility() == View.VISIBLE && i < ImageManager.ROWS - 1) {
                    iv.setVisibility(View.INVISIBLE);
                    ImageManager.imageGrid[i + 1][j].setImageDrawable(iv.getDrawable());    // keep drawable
                    ImageManager.imageGrid[i + 1][j].setTag(iv.getTag());
                    ImageManager.imageGrid[i + 1][j].setVisibility(View.VISIBLE);
                }
                else if (i == ImageManager.ROWS - 1 && iv.getVisibility() == View.VISIBLE)
                    iv.setVisibility(View.INVISIBLE);

                checkHit();
            }
    }

    private static void hit(int x, int y) {
        // Play boom
        MediaPlayer.create(gameActivity, R.raw.boom).start();

        // subtract battery
        ImageView bat = gameActivity.findViewById(R.id.main_IMG_battery1);
        if (bat.getVisibility() == View.VISIBLE)
            bat.setVisibility(View.INVISIBLE);
        else if ((bat = gameActivity.findViewById(R.id.main_IMG_battery2)).getVisibility() == View.VISIBLE)
            bat.setVisibility(View.INVISIBLE);
        else {
            bat = gameActivity.findViewById(R.id.main_IMG_battery3);
            bat.setVisibility(View.INVISIBLE);
            Toast.makeText(gameActivity, "GAME OVER!", Toast.LENGTH_SHORT).show();
            checkScore();
            resetView();
        }

        // vibrate
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ((Vibrator)gameActivity.getSystemService(Context.VIBRATOR_SERVICE))
                    .vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        }

        // destroy the hit asteroid
        ImageManager.imageGrid[y][x].setVisibility(View.INVISIBLE);

        // replace ship with explosion
        ImageManager.swapPlayerRowImage(R.drawable.ic_main_explosion, gameActivity);
        if (explosionHandler != null)
            explosionHandler.removeCallbacks(explosionRunnable);
        else
            explosionHandler = new Handler();
        explosionRunnable = () -> ImageManager.swapPlayerRowImage(R.drawable.ic_main_spaceship, gameActivity);
        explosionHandler.postDelayed(explosionRunnable, 1000);
    }

    private static void checkScore() {
        ArrayList<HighScore> scores = HighScoreManager.getHighScores();
        int my_place = -1;
        for (int i = 0; i < scores.size(); i++) {
            if (scores.get(i).score <= GameManager.odometer)
                my_place = scores.get(i).place;
        }

        if (my_place > -1) {
            scores.remove(my_place);
            scores.add(new HighScore().setScore(GameManager.odometer).setPosition(new LatLng(0, 0)).setName("NOOBIE"));
        }
    }

    /***
     * Restart the view into the beginning on the game.
     */
    private static void resetView() {
        for (int i = 0; i < ImageManager.ROWS + 1; i++)
            for (int j = 0; j < ImageManager.COLS; j++)
                ImageManager.imageGrid[i][j].setVisibility(View.INVISIBLE);

        ImageManager.imageGrid[ImageManager.ROWS][ImageManager.COLS / 2].setVisibility(View.VISIBLE); // start position of spaceship
        gameActivity.findViewById(R.id.main_IMG_battery1).setVisibility(View.VISIBLE);
        gameActivity.findViewById(R.id.main_IMG_battery2).setVisibility(View.VISIBLE);
        gameActivity.findViewById(R.id.main_IMG_battery3).setVisibility(View.VISIBLE);
        odometer = 0;
        aliens = 0;
        ((TextView)gameActivity.findViewById(R.id.main_TXT_odometer)).setText(""+odometer);
        ((TextView)gameActivity.findViewById(R.id.main_TXT_aliens)).setText(""+ aliens);
    }

    // In this timer function I am using a regular schedule and not 'AtFixedRate' because I want
    // the timer to update in real time by the SPEED parameter.
    public static void startTimer() {
        mainTimer = new Timer();
        mainTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameActivity.runOnUiThread(() -> updateGame());
                startTimer();
            }
        }, SPEED);

        if (sensorControl != null)
            sensorControl.start();
    }

    public static void stopTimer() {
        mainTimer.cancel();

        if (sensorControl != null)
            sensorControl.stop();
    }

    public static void setSpeed(int value) {
        SPEED = value;
    }
}
