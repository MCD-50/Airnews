package com.air.aircovg.helpers;

import android.content.Context;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StreamCorruptedException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by ayush AS on 27/12/16.
 */

public class InternetHelper {
    public String getJsonString(String url, int pageNumber, String searchString, String languageName) throws IOException {
        String jsonString;

        String data = URLEncoder.encode("page_number", "UTF-8")
                + "=" + URLEncoder.encode(String.valueOf(pageNumber), "UTF-8");

        data += "&" + URLEncoder.encode("search_string", "UTF-8") + "="
                + URLEncoder.encode(searchString, "UTF-8");


        data += "&" + URLEncoder.encode("language_name", "UTF-8")
                + "=" + URLEncoder.encode(languageName, "UTF-8");

        URL _obj = new URL(url);
        HttpURLConnection _connection = (HttpURLConnection) _obj.openConnection();
        _connection.setReadTimeout(15000);
        _connection.setConnectTimeout(15000);
        _connection.setRequestMethod("POST");
        _connection.setDoInput(true);
        _connection.setDoOutput(true);

        OutputStreamWriter wr = new OutputStreamWriter(_connection.getOutputStream());
        wr.write(data);
        wr.flush();

        //_connection.connect();
        BufferedReader _reader = new BufferedReader(new InputStreamReader(_connection.getInputStream()));

        String result;
        StringBuilder _sBuilder = new StringBuilder();

        while ((result = _reader.readLine()) != null) {
            _sBuilder.append(result);
        }

        jsonString = _sBuilder.toString();
        _reader.close();

        return jsonString;
    }
}
