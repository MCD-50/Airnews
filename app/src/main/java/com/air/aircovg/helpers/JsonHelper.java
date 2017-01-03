package com.air.aircovg.helpers;

import android.text.TextUtils;

import com.air.aircovg.model.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ayush AS on 27/12/16.
 */

public class JsonHelper {

    public ArrayList<News> getAllNews(String jsonString) throws JSONException {

        ArrayList<News> newsArrayList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray array = jsonObject.getJSONArray("articles");
        String source = jsonObject.getString("source");

        for (int i = 0 ; i < array.length(); i++){
            jsonObject = array.getJSONObject(i);

            News news = new News(
                        getAuthor(jsonObject.getString("author")),
                        getTitle(jsonObject.getString("title")),
                        getDescription(jsonObject.getString("description")),
                        jsonObject.getString("url"),
                        getPublishedAt(jsonObject.getString("publishedAt")),
                        source);

            news.setmImageUrl(jsonObject.getString("urlToImage"));
            newsArrayList.add(news);
        }


        return newsArrayList;
    }

     private String getPublishedAt(String dateString){
        if(TextUtils.isEmpty(dateString) || dateString.equalsIgnoreCase("null") ){
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
            return df.format(c.getTime());
        }
        String[]  x = dateString.split("T");
        return x[0];
    }

    private String getAuthor(String authorString){
        if(TextUtils.isEmpty(authorString) || authorString.equalsIgnoreCase("null"))
            return "Unknown Author";
        return authorString.substring(0,1).toUpperCase() + authorString.substring(1);
    }

    private String getTitle(String titleString){
        if(TextUtils.isEmpty(titleString) || titleString.equalsIgnoreCase("null"))
            return "There was a problem fetching title for this news.";
        return titleString.substring(0,1).toUpperCase() + titleString.substring(1);
    }

    private String getDescription(String titleString){
        if(TextUtils.isEmpty(titleString) || titleString.equalsIgnoreCase("null"))
            return "There was a problem fetching description for this news.";
        return titleString.substring(0,1).toUpperCase() + titleString.substring(1);
    }

}
