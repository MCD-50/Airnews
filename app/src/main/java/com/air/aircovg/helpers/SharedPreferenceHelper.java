package com.air.aircovg.helpers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ayush AS on 27/12/16.
 */

public class SharedPreferenceHelper {

    //keys => country, language
    private SharedPreferences sharedPreferences;
    public SharedPreferenceHelper(Context context){
        sharedPreferences = context.getSharedPreferences("country", 0);
    }

    public void saveData(String key, String value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getData(String key, String defaultValue){
        return sharedPreferences.getString(key, defaultValue);
    }

}
