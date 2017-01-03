package com.air.aircovg.model;

/**
 * Created by ayush AS on 27/12/16.
 */

public class Category {
    private String mName;
    private int mImage;
    private int mChannelCount;
    public Category(String mName, int mImage, int mChannelCount) {
        this.mName = mName;
        this.mImage = mImage;
        this.mChannelCount = mChannelCount;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmImage() {
        return mImage;
    }

    public void setmImage(int mImage) {
        this.mImage = mImage;
    }

    public int getmChannelCount() {
        return mChannelCount;
    }

    public void setmChannelCount(int mChannelCount) {
        this.mChannelCount = mChannelCount;
    }
}
