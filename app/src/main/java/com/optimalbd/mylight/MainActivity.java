package com.optimalbd.mylight;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageView fLightImageView;
    PowerWork powerWork;

    private TextView batteryTextView;
    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent intent) {
            // TODO Auto-generated method stub
            int level = intent.getIntExtra("level", 0);
            batteryTextView.setText(String.valueOf(level) + "%");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fLightImageView = (ImageView) findViewById(R.id.fLightImageView);
        powerWork = new PowerWork(MainActivity.this);

        batteryTextView = (TextView) this.findViewById(R.id.batteryTextView);
        this.registerReceiver(this.mBatInfoReceiver,
                new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        fLightImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (powerWork.getIsFlashOn()) {
                    powerWork.turnOffFlash();
                    powerWork.playSound(MainActivity.this);
                    fLightImageView.setImageResource(R.drawable.poweroff);
                } else {
                    powerWork.turnOnFlash();
                    powerWork.playSound(MainActivity.this);
                    fLightImageView.setImageResource(R.drawable.poweron);
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        powerWork.Destroy();
        if (mBatInfoReceiver != null) {
            unregisterReceiver(mBatInfoReceiver);
        }
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == PowerWork.STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(MainActivity.this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }
}
