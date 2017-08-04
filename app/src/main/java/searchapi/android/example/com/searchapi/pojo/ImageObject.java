package searchapi.android.example.com.searchapi.pojo;

import com.google.gson.annotations.Expose;

public class ImageObject {

    @Expose
    String thumbnailLink;

    public String getThumbnailLink() {
        return thumbnailLink;
    }

    public void setThumbnailLink(String thumbnailLink) {
        this.thumbnailLink = thumbnailLink;
    }
}

