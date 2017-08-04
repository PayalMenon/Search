package searchapi.android.example.com.searchapi.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;
import searchapi.android.example.com.searchapi.pojo.SearchInfo;

public interface SearchService {

    @GET("v1?key=AIzaSyC8SP2EDkTkAqZ35yhmszcKUOQT9BtyOig&cx=007763906817198665592:prtxyjoa87e&searchType=image")
    Call<SearchInfo> getSearchImages(@Query("q") String query,
                                     @Query("exactTerms") String terms,
                                     @Query("start") String startIndex);

}
