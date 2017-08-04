package searchapi.android.example.com.searchapi.pojo;

import com.google.gson.annotations.Expose;

public class SearchItemInfo {

    @Expose
    String link;

    @Expose
    ImageObject image;

    public ImageObject getImage() {
        return image;
    }

    public String getLink() {
        return link;
    }

    public void setImage(ImageObject image) {
        this.image = image;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
