package com.air.aircovg.model;

import android.graphics.Bitmap;
import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by ayush AS on 27/12/16.
 */

public class News implements Serializable {

    private String mAuthor;
    private String mTitle;
    private String mUrl;
    private String mPublishedAt;
    private String mDescription;

    public News(String mAuthor, String mTitle, String mDescription, String mUrl, String mPublishedAt) {
        this.mAuthor = mAuthor;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mUrl = mUrl;
        this.mPublishedAt = mPublishedAt;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public String getmPublishedAt() {
        return mPublishedAt;
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public void setmPublishedAt(String mPublishedAt) {
        this.mPublishedAt = mPublishedAt;
    }

    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }
}
