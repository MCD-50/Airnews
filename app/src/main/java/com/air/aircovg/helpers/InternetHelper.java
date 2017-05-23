package com.air.aircovg.helpers;

import android.content.Context;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
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

        URL _obj = new URL(StringHelpers.getFullNewsUrl(item, sortBy));
        HttpURLConnection _connection = (HttpURLConnection) _obj.openConnection();
        _connection.connect();
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

    public XmlPullParser getXml(String query) {
        try {
            URL url = new URL(query);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //conn.setReadTimeout(10000 /* milliseconds */);
            //conn.setConnectTimeout(15000 /* milliseconds */);
            //conn.setRequestMethod("GET");
            //conn.setDoInput(true);
            conn.connect();
            InputStream stream = conn.getInputStream();

            XmlPullParser myParser = Xml.newPullParser();
            myParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            myParser.setInput(stream, null);
            myParser.nextTag();
            return myParser;
        } catch (Exception e) {
            return null;
        }
    }
}
