package com.example.class23a_and_hw1.Managers;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import com.example.class23a_and_hw1.Managers.GameManager;

public class SensorControl {

    public interface Callback_Controls {
        void onLeft();
        void onRight();
    }

    private android.hardware.SensorManager mSensorManager;
    private Sensor sensor;
    private Callback_Controls cb;

    private long timeStamp = 0;

    public SensorControl(Context context, Callback_Controls cb) {
        mSensorManager = (android.hardware.SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.cb = cb;
    }

    public void start() {
        mSensorManager.registerListener(sel, sensor, android.hardware.SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stop() {
        mSensorManager.unregisterListener(sel);
    }

    private SensorEventListener sel = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.values[1] < -3 && System.currentTimeMillis() - timeStamp > 200) {
                // set fast speed
                GameManager.setSpeed(300);
            }
            else if (event.values[1] > 3 && System.currentTimeMillis() - timeStamp > 200) {
                // set slow speed
                GameManager.setSpeed(900);
            }
            else if (System.currentTimeMillis() - timeStamp > 200) {
                // set regular speed
                GameManager.setSpeed(600);
            }

            if (event.values[0] > 3 && System.currentTimeMillis() - timeStamp > 200) {
                timeStamp = System.currentTimeMillis();
                cb.onLeft();
            }
            else if (event.values[0] < -3 && System.currentTimeMillis() - timeStamp > 200) {
                timeStamp = System.currentTimeMillis();
                cb.onRight();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}
