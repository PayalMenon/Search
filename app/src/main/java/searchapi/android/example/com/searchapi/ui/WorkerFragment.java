package searchapi.android.example.com.searchapi.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;

import searchapi.android.example.com.searchapi.service.BackgroundMusicService;

public class WorkerFragment extends Fragment implements MainActivity.WorkerFragmentListener {

    private boolean bound;
    private BackgroundMusicService musicService;
    private boolean speakerOn;

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder binder) {

            musicService = ((BackgroundMusicService.ServiceBinder)binder).getService();
            bound = true;
            if(speakerOn) {
                musicService.resumeMusic();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            if(!speakerOn) {
                musicService.stopMusic();
                musicService = null;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        this.speakerOn = true;
    }

    @Override
    public void onStart() {

        super.onStart();
        Intent intent = new Intent(getActivity(), BackgroundMusicService.class);
        getActivity().bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        ((MainActivity) getActivity()).setWorkerFragmentListener(this);
    }

    @Override
    public void onDestroy() {

        super.onDestroy();

        if(bound) {

            musicService.stopMusic();
            musicService = null;
            getActivity().unbindService(connection);
            bound = false;
        }
    }

    @Override
    public void onSpeakerClicked(boolean speakerOn) {

        if(speakerOn == false) {
            musicService.pauseMusic();
            this.speakerOn = false;
        }
        else {
            musicService.resumeMusic();
            this.speakerOn = true;
        }
    }

    @Override
    public boolean getSpeakerState() {
        return this.speakerOn;
    }
}
