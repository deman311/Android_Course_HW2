package com.example.class23a_and_hw1.Managers;

import android.app.Activity;
import android.graphics.Color;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.class23a_and_hw1.R;

import java.util.Arrays;

public abstract class ImageManager {

    public static int ROWS = 10, COLS = 5;
    public static ImageView[][] imageGrid;

    public static int[] getAllImages() {
        return new int[] {
                R.id.main_IMG_0,
                R.id.main_IMG_1,
                R.id.main_IMG_2,
                R.id.main_IMG_3,
                R.id.main_IMG_4,
                R.id.main_IMG_5,
                R.id.main_IMG_6,
                R.id.main_IMG_7,
                R.id.main_IMG_8,
                R.id.main_IMG_9,
                R.id.main_IMG_10,
                R.id.main_IMG_11,
                R.id.main_IMG_12,
                R.id.main_IMG_13,
                R.id.main_IMG_14,
                R.id.main_IMG_15,
                R.id.main_IMG_16,
                R.id.main_IMG_17,
                R.id.main_IMG_18,
                R.id.main_IMG_19,
                R.id.main_IMG_20,
                R.id.main_IMG_21,
                R.id.main_IMG_22,
                R.id.main_IMG_23,
                R.id.main_IMG_24,
                R.id.main_IMG_25,
                R.id.main_IMG_26,
                R.id.main_IMG_27,
                R.id.main_IMG_28,
                R.id.main_IMG_29,
                R.id.main_IMG_30,
                R.id.main_IMG_31,
                R.id.main_IMG_32,
                R.id.main_IMG_33,
                R.id.main_IMG_34,
                R.id.main_IMG_35,
                R.id.main_IMG_36,
                R.id.main_IMG_37,
                R.id.main_IMG_38,
                R.id.main_IMG_39,
                R.id.main_IMG_40,
                R.id.main_IMG_41,
                R.id.main_IMG_42,
                R.id.main_IMG_43,
                R.id.main_IMG_44,
                R.id.main_IMG_45,
                R.id.main_IMG_46,
                R.id.main_IMG_47,
                R.id.main_IMG_48,
                R.id.main_IMG_49,
                R.id.main_IMG_p1,
                R.id.main_IMG_p2,
                R.id.main_IMG_p3,
                R.id.main_IMG_p4,
                R.id.main_IMG_p5
        };
    }

    /***
     * Replace all of the images in the player's row with the gien image.
     */
    public static void swapPlayerRowImage(int id, Activity activity) {
        for (int i = 0; i < COLS; i++)
        Glide.with(activity).load(id).into(ImageManager.imageGrid[ROWS][i]);
    }

    /***
     * Generate a Matrix of all ImageViews on the game screen.
     * @return a double array of type ImageView[][]
     */
    private static ImageView[][] getImageViewMatrix(Activity activity) {
        int[] imageIds = getAllImages();
        ImageView[] imageViews = new ImageView[(ROWS + 1) * COLS];
        for (int i = 0; i < (ROWS + 1) * COLS; i++)
            imageViews[i] = activity.findViewById(imageIds[i]);

        ImageView[][] viewMatrix = new ImageView[ROWS + 1][COLS];
        for (int i = 0; i < ROWS + 1; i++) {
            viewMatrix[i] = Arrays.copyOfRange(imageViews, COLS*i, COLS*(i+1));
        }
        return viewMatrix;
    }

    /***
     * Note that I use Glide library to load all of the images because Guy said it does so in an
     * optimized way and saves us memory on the device.
     */
    public static void generateImages(Activity activity) {
        imageGrid = getImageViewMatrix(activity);
        // load the asteroids
        for (int i = 0; i < ROWS; i++)
            for (int j = 0; j < COLS; j++) {
                Glide.with(activity).load(R.drawable.ic_main_asteroid).into(imageGrid[i][j]);
                imageGrid[i][j].setColorFilter(Color.WHITE);
            }

        // load the spaceships
        for (int i = 0; i < COLS; i++) {
            Glide.with(activity).load(R.drawable.ic_main_spaceship).into(imageGrid[ROWS][i]);
            imageGrid[ROWS][i].setColorFilter(Color.WHITE);
        }

        // load batteries
        ImageView bat1 = activity.findViewById(R.id.main_IMG_battery1);
        ImageView bat2 = activity.findViewById(R.id.main_IMG_battery2);
        ImageView bat3 = activity.findViewById(R.id.main_IMG_battery3);
        bat1.setColorFilter(Color.WHITE);
        bat2.setColorFilter(Color.WHITE);
        bat3.setColorFilter(Color.WHITE);
        Glide.with(activity).load(R.drawable.ic_main_battery).into(bat1);
        Glide.with(activity).load(R.drawable.ic_main_battery).into(bat2);
        Glide.with(activity).load(R.drawable.ic_main_battery).into(bat3);
    }
}
