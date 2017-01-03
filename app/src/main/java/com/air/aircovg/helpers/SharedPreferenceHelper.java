package com.air.aircovg.helpers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ayush AS on 27/12/16.
 */

public class SharedPreferenceHelper {


    private SharedPreferences sharedPreferences;
    public SharedPreferenceHelper(Context context){
        sharedPreferences = context.getSharedPreferences("sortBy", 0);
    }

    //we have single data to be stored..
    public void saveData(String sortBy){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("sortBy", sortBy);
        editor.apply();
    }


    public String getData(){
        return sharedPreferences.getString("sortBy", "top");
    }

}
