package com.example.nazmul.popularmovies_udacityproject;

/**
 * Created by Ram Rooter on 10/22/2016.
 */

public class Movie {
    private String mCaption;
    private String mTitle; // Testing title
    private String mId; // Testing id;
    private String mUrl; // testing url;

    public String getCaption() {
        return mCaption;
    }

    public void setCaption(String mCaption) {
        this.mCaption = mCaption;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public String toString(){
        return mCaption;
    }
}
