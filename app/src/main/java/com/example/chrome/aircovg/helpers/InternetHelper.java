package com.example.chrome.aircovg.helpers;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ayush AS on 27/12/16.
 */

public class InternetHelper {


    public String getJsonString(String item, String sortBy) throws IOException {
        String jsonString;

        URL _obj = new URL(AppConstants.getFullNewsUrl(item, sortBy));
        HttpURLConnection _connection = (HttpURLConnection) _obj.openConnection();
        _connection.connect();
        BufferedReader _reader = new BufferedReader(new InputStreamReader(_connection.getInputStream()));

        String result;
        StringBuilder _sBuilder = new StringBuilder();

        while((result = _reader.readLine()) != null){
            _sBuilder.append(result);
        }

        jsonString = _sBuilder.toString();
        _reader.close();

        return jsonString;
    }

}
