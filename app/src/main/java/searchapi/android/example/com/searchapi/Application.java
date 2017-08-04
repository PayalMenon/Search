package searchapi.android.example.com.searchapi;

import searchapi.android.example.com.searchapi.dragger.component.DaggerNetworkComponent;
import searchapi.android.example.com.searchapi.dragger.component.NetworkComponent;
import searchapi.android.example.com.searchapi.dragger.module.ApplicationModule;
import searchapi.android.example.com.searchapi.dragger.module.NetworkModule;

public class Application extends android.app.Application {

    private NetworkComponent networkComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        networkComponent = DaggerNetworkComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule("https://www.googleapis.com/customsearch/"))
                .build();
    }

    public NetworkComponent getNetworkComponent() {
        return networkComponent;
    }
}
