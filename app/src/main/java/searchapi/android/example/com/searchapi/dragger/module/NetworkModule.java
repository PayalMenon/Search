package searchapi.android.example.com.searchapi.dragger.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import searchapi.android.example.com.searchapi.service.SearchService;

@Module
public class NetworkModule {

    String baseUrl;

    public NetworkModule(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }

    @Provides
    @Singleton
    SearchService provideRetrofitService(Retrofit retrofit) {
        return retrofit.create(SearchService.class);
    }
}
