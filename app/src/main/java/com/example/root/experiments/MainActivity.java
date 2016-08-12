package com.example.root.experiments;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.root.experiments.services.MusicService;
import com.example.root.experiments.services.SecondScreen;

public class MainActivity extends AppCompatActivity {


    MusicService musicService;
    boolean isServiceBounded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, MusicService.class);

        startService(intent);

        bindService(intent,mServiceConnection,BIND_AUTO_CREATE);

        Button button = (Button)findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(isServiceBounded)
                    musicService.next();


            }
        });

        Button button2 = (Button)findViewById(R.id.btnStop);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(isServiceBounded)
                    musicService.stop();

            }
        });


        TextView tvPlay = (TextView)findViewById(R.id.tvPlay);
        tvPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isServiceBounded)
                    musicService.play();

            }
        });


        TextView tvPause = (TextView)findViewById(R.id.tvPause);
        tvPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isServiceBounded)
                    musicService.pause();

            }
        });



    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {

            isServiceBounded = false;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            MusicService.MyBinder myBinder = (MusicService.MyBinder) service;
            musicService = myBinder.getService();
            isServiceBounded = true;

        }
    };


}
