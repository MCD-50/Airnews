package com.example.chrome.aircovg.scrapper;

import android.os.AsyncTask;

import com.example.chrome.aircovg.events.AsyncResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by ayush AS on 30/12/16.
 */

public class MtvNews extends AsyncTask<String, Void, String> {
    private AsyncResponse delegate = null;
    public MtvNews(AsyncResponse delegate){
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
