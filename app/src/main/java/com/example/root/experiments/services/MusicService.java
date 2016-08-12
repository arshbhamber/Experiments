package com.example.root.experiments.services;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.example.root.experiments.R;

import java.io.FileDescriptor;

/**
 * Created by root on 1/8/16.
 */
public class MusicService extends Service {

    MyHandler handler;

    MediaPlayer player;

    int[] songId = {

            R.raw.theme,
            R.raw.cod,
            R.raw.goonies,
            R.raw.mario,
            R.raw.mario3,
            R.raw.mario_water,
            R.raw.metal_gear,

    };

    int currentSong = 0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();

                Looper.prepare();
                handler = new MyHandler(Looper.myLooper());


            }
        };


    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Message msg = handler.obtainMessage();
        msg.arg1 = startId;
        player.start();
        return START_REDELIVER_INTENT;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        player.stop();
        player.release();
    }


    public void play(){

        player.start();

    }
    public void stop(){

        player.stop();

    }
    public void pause(){

        player.pause();

    }
    public void next(){

        if(currentSong < songId.length-1)
            currentSong++;
        else currentSong = 0;
        player.reset();
        player = MediaPlayer.create(this,songId[currentSong]);
        player.start();

    }


    public class MyBinder extends Binder{

        public MusicService getService(){

            return MusicService.this;

        }


    }


    public class MyHandler extends Handler{

        public MyHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);



        }
    }
}
