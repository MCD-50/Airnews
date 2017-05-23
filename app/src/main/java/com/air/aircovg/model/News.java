package com.air.aircovg.model;

import android.graphics.Bitmap;
import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by ayush AS on 27/12/16.
 */

public class News implements Serializable {



    private String mTitle;
    private String mUrl;
    private String mPublishedAt;
    private String mDescription;
    private String mImageUrl;
    private Bitmap mImage;

    public News(String mTitle, String mDescription, String mUrl, String mPublishedAt) {
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mUrl = mUrl;
        this.mPublishedAt = mPublishedAt;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public Bitmap getmImage() {
        return mImage;
    }

    public void setmImage(Bitmap mImage) {
        this.mImage = mImage;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public String getmPublishedAt() {
        return mPublishedAt;
    }

    public void setmPublishedAt(String mPublishedAt) {
        this.mPublishedAt = mPublishedAt;
    }


    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }


    public Bitmap getmLocalImage() {
        return mImage;
    }

    public void setmLocalImage(Bitmap mLocalImage) {
        this.mImage = mLocalImage;
    }

}
