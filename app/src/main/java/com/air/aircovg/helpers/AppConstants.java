package com.air.aircovg.helpers;

import java.util.ArrayList;

/**
 * Created by ayush AS on 27/12/16.
 */

public class AppConstants {

    //https://news.google.com/news/feeds?cf=all&ned=us&q=education&output=rss
    public static final String BASE = "https://airnews.herokuapp.com/scrape";

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "AirNewsDatabaseInternal";
    public static final String TABLE_STARRED_NEWS= "AirNewsDatabaseInternalTableStarredNews";

    public static final String KEY_ID = "id";
    public static final String KEY_AUTHOR = "author";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_URL = "url";
    public static final String KEY_PUBLISHED_AT = "publishedAt";


    public static final String TAG_COUNTRY = "country";
    public static final String TAG_LANGUAGE = "language";


    public static ArrayList<String> COUNTRY_LIST = new ArrayList<String>(){{
        add("World");
        add("Afghanistan");
        add("Australia");
        add("Austria");
        add("Bangladesh");
        add("Bhutan");
        add("Brazil");
        add("Canada");
        add("Chile");
        add("China");
        add("Colombia");
        add("France");
        add("Germany");
        add("Israel");
        add("Italy");
        add("India");
        add("Japan");
        add("Russia");
        add("UK");
        add("US");
    }};


    public static ArrayList<String> LANGUAGE_LIST = new ArrayList<String>(){{
        add("Chinese");
        add("Dutch");
        add("English");
        add("French");
        add("German");
        add("Gujarati");
        add("Hebrew");
        add("Hindi");
        add("Italian");
        add("Japanese");
        add("Malayalam");
        add("Marathi");
        add("Punjabi");
        add("Russian");
        add("Tamil");
    }};

    //public static String ARTICLE = "https://newsapi.org/v1/articles?source=";
    //public static String API = "&apiKey=0cc858f1c2c0489e94597fb777a8fe23";

    //public static String SORT_BY = "&sortBy=";

    //public static String GET_ALL_SOURCES = "https://newsapi.org/v1/sources?language=en";
    //public static String GET_SOURCES_BY_CATEGORY="https://newsapi.org/v1/sources?category=";




}
