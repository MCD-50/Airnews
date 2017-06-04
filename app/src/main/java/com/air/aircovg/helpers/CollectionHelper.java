package com.air.aircovg.helpers;

import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;

import com.air.aircovg.model.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by ayush AS on 20/5/17.
 */

public class CollectionHelper {

    public static void showAlert(Context mContext, String title, String message){
        AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setPositiveButton("OK", null);
        alert.setNegativeButton("CANCEL", null);
        alert.show();
    }

    public ArrayList<News> getNewsListFromJsonString(String jsonString) throws JSONException {
        ArrayList<News> newsArrayList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray array = jsonObject.getJSONArray("items");

        for (int i = 0 ; i < array.length(); i++){
            jsonObject = array.getJSONObject(i);

            String url = jsonObject.getString("url");
            //String author, title, description, url, permUrl, publishedAt, imageUrl;
            if(url == null || TextUtils.isEmpty(url) || url.equalsIgnoreCase("null")){
                continue;
            }

            String author = getString(jsonObject.getString("author"), "Unknown author");
            String title = getString(jsonObject.getString("title"), "Unknown title");
            String description = getString(jsonObject.getString("description"), "There was a problem fetching description for this item.");
            String publishedAt = getString(jsonObject.getString("published_at"), "Unknown date");


            News news = new News(author, title, description, url, publishedAt);
            newsArrayList.add(news);
        }
        return newsArrayList;
    }


    private String getString(String value, String inPlace){
        if(value == null || TextUtils.isEmpty(value) || value.equalsIgnoreCase("null"))
            return inPlace;
        return value;
    }
}
