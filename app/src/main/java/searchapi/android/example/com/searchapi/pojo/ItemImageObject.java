package searchapi.android.example.com.searchapi.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemImageObject {

    @SerializedName("cse_thumbnail")
    @Expose
    List<ThumbnailObject> thumbnailObject;

    public void setThumbnailObject(List<ThumbnailObject> thumbnailObject) {
        this.thumbnailObject = thumbnailObject;
    }

    public List<ThumbnailObject> getThumbnailObject() {
        return thumbnailObject;
    }
}
