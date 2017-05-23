package com.air.aircovg.helpers;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Created by ayush AS on 20/5/17.
 */

public class CollectionHelper {

    public static void showAlert(Context mContext, String title, String message){
        AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setPositiveButton("OK",null);
        alert.setNegativeButton("CANCEL", null);
        alert.show();
    }
}
