package com.example.chrome.aircovg.model;

/**
 * Created by ayush AS on 27/12/16.
 */

public class Base {

    private int mId;
    private String mImageUrl;
    private String mUrl;

    public Base(int mId, String mImageUrl, String mUrl) {
        this.mId = mId;
        this.mImageUrl = mImageUrl;
        this.mUrl = mUrl;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }
}
