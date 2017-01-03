package com.example.chrome.aircovg.helpers;

import java.security.PublicKey;

/**
 * Created by ayush AS on 27/12/16.
 */

public class AppConstants {


    public static String ARTICLE = "https://newsapi.org/v1/articles?source=";
    public static String API = "&apiKey=0cc858f1c2c0489e94597fb777a8fe23";

    public static String SORT_BY = "&sortBy=";

    public static String GET_ALL_SOURCES = "https://newsapi.org/v1/sources?language=en";
    public static String GET_SOURCES_BY_CATEGORY="https://newsapi.org/v1/sources?category=";

    public static String getFullNewsUrl(String searchItem, String sort){
        return ARTICLE + searchItem + SORT_BY + sort + API;
    }


    public static String getCategoryFullUrl(String category){
          return GET_SOURCES_BY_CATEGORY + category;
    }

}
