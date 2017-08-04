package searchapi.android.example.com.searchapi.pojo;

import com.google.gson.annotations.Expose;

import java.util.List;

public class SearchInfo {

    private SearchInfoObject searchInfo;

    @Expose
    private List<SearchItemInfo> items;

    public void setItems(List<SearchItemInfo> items) {
        this.items = items;
    }

    public void setSearchInfo(SearchInfoObject searchInfo) {
        this.searchInfo = searchInfo;
    }

    public List<SearchItemInfo> getItems() {
        return items;
    }

    public SearchInfoObject getSearchInfo() {
        return searchInfo;
    }

}
