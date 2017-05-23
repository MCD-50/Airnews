package com.air.aircovg.helpers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ayush AS on 27/12/16.
 */

public class SharedPreferenceHelper {

    private SharedPreferences sharedPreferences;
    public SharedPreferenceHelper(Context context){
        sharedPreferences = context.getSharedPreferences("ned", 0);
    }

    //we have single data to be stored..
    public void saveData(String key, String value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getData(String key){
        return sharedPreferences.getString(key, "all");
    }

}
