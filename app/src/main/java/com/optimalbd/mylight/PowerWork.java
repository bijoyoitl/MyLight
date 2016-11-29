package com.optimalbd.mylight;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.MediaPlayer;

public class PowerWork {

    private Camera camera;
    private boolean isFalshOn;
    private MediaPlayer mp;

    public boolean getIsFlashOn() {
        return this.isFalshOn;
    }

    public PowerWork() {
        this.isFalshOn = false;
        this.camera = Camera.open();
    }

    public void turnOnFlash() {
        if (!this.isFalshOn) {
            this.isFalshOn = true;
            try {
                Parameters mParameters = this.camera.getParameters();
                mParameters.setFlashMode("torch");
                this.camera.setParameters(mParameters);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void turnOffFlash() {
        if (this.isFalshOn) {
            this.isFalshOn = false;
            try {
                Parameters mParameters = this.camera.getParameters();
                mParameters.setFlashMode("off");
                this.camera.setParameters(mParameters);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void Destroy() {
        this.camera.release();
    }

    public void playSound(Context context) {
        if (isFalshOn) {
            mp = MediaPlayer.create(context, R.raw.turnon);
        } else {
            mp = MediaPlayer.create(context, R.raw.turnon);
        }
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.release();
            }
        });
        mp.start();
    }
}
