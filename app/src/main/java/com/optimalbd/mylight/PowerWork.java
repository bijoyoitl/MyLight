package com.optimalbd.mylight;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

public class PowerWork {

    private Context context;
    private Camera camera;
    private boolean isFalshOn;
    private MediaPlayer mp;
    public static int STORAGE_PERMISSION_CODE = 23;

    public boolean getIsFlashOn() {
        return this.isFalshOn;
    }

    public PowerWork() {
        this.isFalshOn = false;
        this.camera = Camera.open();
    }

    public PowerWork(Context context) {
        this.context = context;
        this.isFalshOn = false;
        if (isReadStorageAllowed()){
            this.camera = Camera.open();
        }
        requestStoragePermission(context);

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
        if (camera!=null){
            this.camera.release();
        }

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

    private boolean isReadStorageAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }


    //Requesting permission
    private void requestStoragePermission(Context context){

        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,Manifest.permission.CAMERA)){
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions((Activity) context,new String[]{Manifest.permission.CAMERA},STORAGE_PERMISSION_CODE);
    }


}
