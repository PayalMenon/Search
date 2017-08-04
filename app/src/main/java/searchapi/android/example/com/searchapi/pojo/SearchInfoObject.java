package searchapi.android.example.com.searchapi.pojo;

import com.google.gson.annotations.Expose;

public class SearchInfoObject {

    @Expose
    private String searchTime;

    @Expose
    private String totalResults;

    public void setSearchTime(String searchTime) {
        this.searchTime = searchTime;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

   public String getSearchTime() {
        return searchTime;
    }

    public String getTotalResults() {
        return totalResults;
    }
}
