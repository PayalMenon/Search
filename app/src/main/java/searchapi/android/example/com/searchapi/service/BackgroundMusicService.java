package searchapi.android.example.com.searchapi.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import javax.inject.Inject;

import searchapi.android.example.com.searchapi.Application;
import searchapi.android.example.com.searchapi.R;

public class BackgroundMusicService extends Service implements MediaPlayer.OnErrorListener{

    private MediaPlayer player;
    private final IBinder binder = new ServiceBinder();
    private AudioManager audio;

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {

        if(player != null) {
            player.stop();
            player.release();
            player = null;
        }
        return false;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if(player == null) {
            player = MediaPlayer.create(this, R.raw.background);
        }

        audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audio.setMode(AudioManager.STREAM_MUSIC);
        audio.setSpeakerphoneOn(true);

        if(player != null && player.isPlaying() == false) {

            player.setLooping(true);
            player.setVolume(0, 50);
        }

        player.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                return true;
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        player.start();
        return START_STICKY;
    }

    public void pauseMusic() {

        if(player.isPlaying()) {
            player.pause();
            audio.setSpeakerphoneOn(false);
        }
    }

    public void resumeMusic() {

        if(player.isPlaying() == false) {
            player.start();
            audio.setSpeakerphoneOn(true);
        }
    }

    public void stopMusic() {

        player.stop();
        audio.setSpeakerphoneOn(false);
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        if(player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }

    public class ServiceBinder extends Binder {

        public BackgroundMusicService getService() {

            return BackgroundMusicService.this;
        }
    }
}
