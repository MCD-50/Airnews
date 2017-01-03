package com.example.chrome.aircovg.model;

import android.graphics.Bitmap;
import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by ayush AS on 27/12/16.
 */

public class News implements Serializable {


    private String mAuthor;
    private String mTitle;
    private String mDescription;
    private String mUrl;
    private String mImageUrl;
    private String mPublishedAt;
    private String mSource;
    private int mId;
    private Bitmap mLocalImage;

    public News(String mAuthor, String mTitle, String mDescription, String mUrl, String mPublishedAt,String mSource) {
        this.mAuthor = mAuthor;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mUrl = mUrl;
        this.mPublishedAt = mPublishedAt;
        this.mSource = mSource;
    }


    public int getmId(){
        return mId;
    }

    public void setmId(int mId){
        this.mId = mId;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
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

    public String getmPublishedAt() {
        return mPublishedAt;
    }

    public void setmPublishedAt(String mPublishedAt) {
        this.mPublishedAt = mPublishedAt;
    }

    public Bitmap getmLocalImage() {
        return mLocalImage;
    }

    public void setmLocalImage(Bitmap mLocalImage) {
        this.mLocalImage = mLocalImage;
    }


    public String getmSource() {
        return mSource;
    }

    public void setmSource(String mSource) {
        this.mSource = mSource;
    }
}
