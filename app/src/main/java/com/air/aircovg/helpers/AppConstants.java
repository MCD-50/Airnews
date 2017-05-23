package com.air.aircovg.helpers;

import com.air.aircovg.model.Country;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ayush AS on 27/12/16.
 */

public class AppConstants {

    //https://news.google.com/news/feeds?cf=all&ned=us&q=education&output=rss
    public static final String BASE = "https://news.google.com/news/feeds?cf=all";
    public static final String NEWS_EDITION = "&ned=";
    public static final String QUERY = "&q=";
    public static final String OUTPUT = "&output=rss";

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "AirNewsDatabaseInternal";
    public static final String TABLE_STARRED_NEWS= "AirNewsDatabaseInternalTableStarredNews";

    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_URL = "url";
    public static final String KEY_PUBLISHED_AT = "publishedAt";
    public static final String KEY_IMAGE_URL = "imageUrl";
    public static final String KEY_IMAGE = "image";


    public static final String TAG_TITLE = "title";
    public static final String TAG_LINK = "guid";
    public static final String TAG_DESCRIPTION = "description";
    public static final String TAG_DATE = "pubDate";
    public static final String TAG_RSS = "rss";
    public static final String TAG_ITEM = "item";
    //public static String ARTICLE = "https://newsapi.org/v1/articles?source=";
    //public static String API = "&apiKey=0cc858f1c2c0489e94597fb777a8fe23";

    //public static String SORT_BY = "&sortBy=";

    //public static String GET_ALL_SOURCES = "https://newsapi.org/v1/sources?language=en";
    //public static String GET_SOURCES_BY_CATEGORY="https://newsapi.org/v1/sources?category=";

    public static ArrayList<Country> COUNTRY_LIST = new ArrayList<Country>(){{
        add(new Country("all", "All"));
        add(new Country("af", "Afghanistan"));
        add(new Country("au", "Australia"));
        add(new Country("at", "Austria"));
        add(new Country("bd", "Bangladesh"));
        add(new Country("bt", "Bhutan"));
        add(new Country("br", "Brazil"));
        add(new Country("ca", "Canada"));
        add(new Country("ci", "Chile"));
        add(new Country("cn", "China"));
        add(new Country("hk", "Hong Kong"));
        add(new Country("co", "Colombia"));
        add(new Country("fr", "France"));
        add(new Country("de", "Germany"));
        add(new Country("il", "Israel"));
        add(new Country("it", "Italy"));
        add(new Country("in", "India"));
        add(new Country("jp", "Japan"));
        add(new Country("uk", "UK"));
        add(new Country("us", "US"));
    }};

}
