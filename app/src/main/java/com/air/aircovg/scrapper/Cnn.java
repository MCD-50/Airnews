package com.air.aircovg.scrapper;

import android.os.AsyncTask;

import com.air.aircovg.events.AsyncResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by ayush AS on 30/12/16.
 */

public class Cnn extends AsyncTask<String, Void, String> {
    private AsyncResponse delegate = null;
    public Cnn(AsyncResponse delegate){
        this.delegate = delegate;
    }

    @Override
    protected String doInBackground(String... params) {
        String data = " ";
        Document html = null;
        try {
            html = Jsoup.connect(params[0]).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Element content = null;
        if (html != null) {
            content = html.body().getElementById("body-text");
            Elements divs = content.children().first().getElementsByAttributeValue("class", "zn-body__paragraph");
            for (Element div : divs) {
                div.removeAttr("img");
                data += div.text();
            }
            divs = content.children().first().getElementsByAttributeValue("class", "zn-body__read-all").first().getElementsByAttributeValue("class", "zn-body__paragraph");
            for (Element div : divs) {
                div.removeAttr("img");
                data += div.text();
            }
        }

        return data.trim();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        delegate.processFinish(s);
    }
}
