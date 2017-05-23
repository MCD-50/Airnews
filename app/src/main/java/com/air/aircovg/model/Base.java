package com.air.aircovg.model;

/**
 * Created by ayush AS on 20/5/17.
 */

public class Base {

    private String mUrl;
    private String mImageUrl;
    public Base(String mUrl, String mImageUrl) {
        this.mUrl = mUrl;
        this.mImageUrl = mImageUrl;
    }


    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }
}

