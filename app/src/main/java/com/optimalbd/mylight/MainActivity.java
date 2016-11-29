package com.optimalbd.mylight;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ImageView fLightImageView;
    PowerWork powerWork;

    private TextView batteryTextView;
    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver(){
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
        powerWork = new PowerWork();

        batteryTextView = (TextView) this.findViewById(R.id.batteryTextView);
        this.registerReceiver(this.mBatInfoReceiver,
                new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        fLightImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (powerWork.getIsFlashOn()){
                    powerWork.turnOffFlash();
                    powerWork.playSound(MainActivity.this);
                    fLightImageView.setImageResource(R.drawable.poweroff);
                }else {
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
    }
}
