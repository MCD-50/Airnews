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

public class Espn extends AsyncTask<String, Void, String> {
    private AsyncResponse delegate = null;
    public Espn(AsyncResponse delegate){
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

        Elements content = null;
        if (html != null) {
            content = html.body().getElementsByClass("story-body__inner");
            Elements paraList = content.first().getElementsByTag("p");
            for (Element para : paraList) {
                para.removeAttr("img");
                data += para.text();
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
