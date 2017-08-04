package searchapi.android.example.com.searchapi.ui;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import javax.inject.Inject;

import searchapi.android.example.com.searchapi.Application;
import searchapi.android.example.com.searchapi.R;
import searchapi.android.example.com.searchapi.service.BackgroundMusicService;
import searchapi.android.example.com.searchapi.service.SearchService;

public class MainActivity extends AppCompatActivity {

    private static final String LIST_FRAGMENT = "list_fragment";
    private static final String WORKER_FRAGMENT = "worker_fragment";
    private static final String DEFAULT_QUERY = "waterfalls";

    @Inject
    SearchService service;

    private FragmentListener fragmentListener;
    private WorkerFragmentListener workerListener;
    private String queryString;
    private boolean speakerOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar actionBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(actionBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ((Application) getApplication()).getNetworkComponent().inject(this);

        if (savedInstanceState == null) {
            queryString = DEFAULT_QUERY;
        }

        addWorkerFragment();
        addListFragment();

        speakerOn = true;
    }

    private void addWorkerFragment() {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        WorkerFragment fragment = (WorkerFragment) manager.findFragmentByTag(WORKER_FRAGMENT);

        if (fragment == null) {

            fragment = new WorkerFragment();
            transaction.add(fragment, WORKER_FRAGMENT);
            transaction.commit();
        }
    }

    private void addListFragment() {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        ListFragment fragment = (ListFragment) manager.findFragmentByTag(LIST_FRAGMENT);

        if (fragment == null) {

            fragment = new ListFragment();
            transaction.add(R.id.fragment_container, fragment, LIST_FRAGMENT);
            transaction.commit();
        }
    }

    @Override
    protected void onStart() {

        super.onStart();
        if (DEFAULT_QUERY.equals(queryString)) {
            this.fragmentListener.updateFragmentList(queryString);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (isFinishing()) {

            FragmentManager manager = getSupportFragmentManager();

            ListFragment fragment = (ListFragment) manager.findFragmentByTag(LIST_FRAGMENT);
            WorkerFragment workerFragment = (WorkerFragment) manager.findFragmentByTag(WORKER_FRAGMENT);

            manager.beginTransaction().remove(fragment).commit();
            manager.beginTransaction().remove(workerFragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);

        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();

        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint(getResources().getString(R.string.search_text));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                MainActivity.this.fragmentListener.updateFragmentList(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        MenuItem speakerItem = menu.findItem(R.id.action_speaker);
        if(workerListener.getSpeakerState()) {
            speakerItem.setIcon(R.drawable.speaker);
        } else {
            speakerItem.setIcon(R.drawable.speaker_off);
        }

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_speaker) {
            if (speakerOn == false) {

                speakerOn = true;
                item.setIcon(R.drawable.speaker);
                workerListener.onSpeakerClicked(speakerOn);
            } else {

                speakerOn = false;
                item.setIcon(R.drawable.speaker_off);
                workerListener.onSpeakerClicked(speakerOn);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setFragmentListener(FragmentListener listener) {

        this.fragmentListener = listener;
    }

    public void setWorkerFragmentListener(WorkerFragmentListener listener) {

        this.workerListener = listener;
    }

    public interface FragmentListener {

        void updateFragmentList(String query);

        void onItemClicked(int position, ImageView imageView);
    }

    public interface WorkerFragmentListener {

        void onSpeakerClicked(boolean speakerOn);
        boolean getSpeakerState();
    }
}
