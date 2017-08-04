package searchapi.android.example.com.searchapi.pojo;

import com.google.gson.annotations.Expose;

public class ThumbnailObject {

    @Expose
    String width;

    @Expose
    String height;

    @Expose
    String src;

    public String getHeight() {
        return height;
    }

    public String getSrc() {
        return src;
    }

    public String getWidth() {
        return width;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public void setWidth(String width) {
        this.width = width;
    }
}
