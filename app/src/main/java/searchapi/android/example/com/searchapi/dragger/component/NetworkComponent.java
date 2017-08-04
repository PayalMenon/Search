package searchapi.android.example.com.searchapi.dragger.component;

import javax.inject.Singleton;

import dagger.Component;
import searchapi.android.example.com.searchapi.dragger.module.ApplicationModule;
import searchapi.android.example.com.searchapi.dragger.module.NetworkModule;
import searchapi.android.example.com.searchapi.ui.ListFragment;
import searchapi.android.example.com.searchapi.ui.MainActivity;

@Singleton
@Component (modules = {ApplicationModule.class, NetworkModule.class})
public interface NetworkComponent {

    void inject(MainActivity activity);
    void inject(ListFragment fragment);
}
