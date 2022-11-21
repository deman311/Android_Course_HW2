package com.example.class23a_and_hw1;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public abstract class GameManager {

    // Because only the MainActivity can call some crucial functions that I want to use such as
    // 'findViewById' - I passed it to most functions where it is needed.

    private static Timer mainTimer;
    private static Handler explosionHandler;
    private static Runnable explosionRunnable;

    public static void begin(Activity activity) {
        resetView(activity);
        defineControls(activity);
        startTimer(activity);
    }

    private static void defineControls(Activity activity) {
        ExtendedFloatingActionButton rBtn = activity.findViewById(R.id.main_BTN_right);
        ExtendedFloatingActionButton lBtn = activity.findViewById(R.id.main_BTN_left);
        rBtn.setOnClickListener(ae -> {
            for (int i = 0; i < ImageManager.COLS - 1; i++) {
                ImageView iv = ImageManager.imageGrid[ImageManager.ROWS][i];
                if (iv.getVisibility() == View.VISIBLE) {
                    iv.setVisibility(View.INVISIBLE);
                    ImageManager.imageGrid[ImageManager.ROWS][i+1].setVisibility(View.VISIBLE);
                    checkHit(activity);
                    break;
                }
            }
        });
        lBtn.setOnClickListener(ae -> {
            for (int i = 1; i < ImageManager.COLS; i++) {
                ImageView iv = ImageManager.imageGrid[ImageManager.ROWS][i];
                if (iv.getVisibility() == View.VISIBLE) {
                    iv.setVisibility(View.INVISIBLE);
                    ImageManager.imageGrid[ImageManager.ROWS][i-1].setVisibility(View.VISIBLE);
                    checkHit(activity);
                    break;
                }
            }
        });
    }

    private static void checkHit(Activity activity) {
        for (int j = 0; j < ImageManager.COLS; j++)
            if (ImageManager.imageGrid[ImageManager.ROWS - 1][j].getVisibility() == View.VISIBLE
                && ImageManager.imageGrid[ImageManager.ROWS][j].getVisibility() == View.VISIBLE)
                hit(j, ImageManager.ROWS - 1, activity);
    }

    private static void updateGame(Activity activity) {
        updateAsteroidsMove(activity);
        spawnAsteroids();
    }

    private static void spawnAsteroids() {
        Random rand = new Random();
        for (int i = 0; i < ImageManager.COLS; i++) {
            if (rand.nextInt(5) == 0) // 20% to spawn
                ImageManager.imageGrid[0][i].setVisibility(View.VISIBLE);
        }
    }

    /***
     * The amount of rows int the imageview matrix is always ROWS + 1, because we use an extra row
     * to represent the player ship row.
     */
    private static void updateAsteroidsMove(Activity activity) {
        for (int i = ImageManager.ROWS - 1; i >= 0; i--)    // upside down iteration so we don't drop all rows at once.
            for (int j = 0; j < ImageManager.COLS; j++) {
                ImageView iv = ImageManager.imageGrid[i][j];
                if (iv.getVisibility() == View.VISIBLE && i < ImageManager.ROWS - 1) {
                    iv.setVisibility(View.INVISIBLE);
                    ImageManager.imageGrid[i + 1][j].setVisibility(View.VISIBLE);
                }
                else if (i == ImageManager.ROWS - 1 && iv.getVisibility() == View.VISIBLE)
                    iv.setVisibility(View.INVISIBLE);

                checkHit(activity);
            }
    }

    private static void hit(int x, int y, Activity activity) {
        // subtract battery
        ImageView bat = activity.findViewById(R.id.main_IMG_battery1);
        if (bat.getVisibility() == View.VISIBLE)
            bat.setVisibility(View.INVISIBLE);
        else if ((bat = activity.findViewById(R.id.main_IMG_battery2)).getVisibility() == View.VISIBLE)
            bat.setVisibility(View.INVISIBLE);
        else {
            bat = activity.findViewById(R.id.main_IMG_battery3);
            bat.setVisibility(View.INVISIBLE);
            Toast.makeText(activity, "GAME OVER!", Toast.LENGTH_SHORT).show();
            resetView(activity);
        }

        // vibrate
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ((Vibrator)activity.getSystemService(Context.VIBRATOR_SERVICE))
                    .vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        }

        // destroy the hit asteroid
        ImageManager.imageGrid[y][x].setVisibility(View.INVISIBLE);

        // replace ship with explosion
        ImageManager.swapPlayerRowImage(R.drawable.ic_main_explosion, activity);
        if (explosionHandler != null)
            explosionHandler.removeCallbacks(explosionRunnable);
        else
            explosionHandler = new Handler();
        explosionRunnable = () -> ImageManager.swapPlayerRowImage(R.drawable.ic_main_spaceship, activity);
        explosionHandler.postDelayed(explosionRunnable, 1000);
    }

    /***
     * Restart the view into the beginning on the game.
     */
    private static void resetView(Activity activity) {
        for (int i = 0; i < ImageManager.ROWS + 1; i++)
            for (int j = 0; j < ImageManager.COLS; j++)
                ImageManager.imageGrid[i][j].setVisibility(View.INVISIBLE);

        ImageManager.imageGrid[ImageManager.ROWS][ImageManager.COLS / 2].setVisibility(View.VISIBLE); // start position of spaceship
        activity.findViewById(R.id.main_IMG_battery1).setVisibility(View.VISIBLE);
        activity.findViewById(R.id.main_IMG_battery2).setVisibility(View.VISIBLE);
        activity.findViewById(R.id.main_IMG_battery3).setVisibility(View.VISIBLE);
    }

    public static void startTimer(Activity activity) {
        final int interval = 1000; // 1 Second
        mainTimer = new Timer();
        mainTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                activity.runOnUiThread(() -> updateGame(activity));
            }
        }, interval, interval);
    }

    public static void stopTimer() {
        mainTimer.cancel();
    }
}
