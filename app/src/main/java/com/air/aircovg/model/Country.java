package com.air.aircovg.model;

/**
 * Created by ayush AS on 20/5/17.
 */

public class Country {
    private String mTitle;
    private String mCode;

    public Country(String mCode, String mTitle) {
        this.mCode = mCode;
        this.mTitle = mTitle;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmCode() {
        return mCode;
    }

    public void setmCode(String mCode) {
        this.mCode = mCode;
    }
}
