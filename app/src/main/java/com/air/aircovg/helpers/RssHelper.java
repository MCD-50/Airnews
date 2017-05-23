package com.air.aircovg.helpers;

/**
 * Created by ayush AS on 7/5/17.
 */

import com.air.aircovg.model.News;
import com.air.aircovg.model.XmlObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class RssHelper {
    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<String> description = new ArrayList<>();
    private ArrayList<String> links = new ArrayList<>();
    private ArrayList<String> images = new ArrayList<>();
    private ArrayList<String> pubDates = new ArrayList<>();

    // We don't use namespaces
    private final String ns = null;

    public ArrayList<News> fetchXML(String query) throws IOException, XmlPullParserException {
        InternetHelper internetHelper = new InternetHelper();
        return readFeed(internetHelper.getXml(query));
    }


    private ArrayList<News> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        boolean inside = false;
        parser.require(XmlPullParser.START_TAG, null, AppConstants.TAG_RSS);
        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            int eventType = parser.getEventType();
            if (eventType == XmlPullParser.START_TAG) {
                String name = parser.getName();
                if(name.equalsIgnoreCase(AppConstants.TAG_ITEM)){
                    inside = true;
                } else if (name.equalsIgnoreCase(AppConstants.TAG_TITLE) && inside) {
                    titles.add(readTitle(parser));
                } else if (name.equalsIgnoreCase(AppConstants.TAG_DESCRIPTION) && inside) {
                    String dec = readImage(parser);
                    description.add(StringHelpers.parseDescription(dec));
                    images.add(StringHelpers.parseImage(dec));
                }else if (name.equalsIgnoreCase(AppConstants.TAG_LINK) && inside) {
                    links.add(readLink(parser));
                } else if (name.equalsIgnoreCase(AppConstants.TAG_DATE) && inside) {
                    pubDates.add(readDate(parser));
                }
            }else if(eventType == XmlPullParser.END_TAG && parser.getName().equalsIgnoreCase(AppConstants.TAG_ITEM)){
                inside = false;
            }
        }
        return getNewsList();
    }

    private String readLink(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, AppConstants.TAG_LINK);
        String link = readText(parser);
        link = StringHelpers.parseLink(link);
        parser.require(XmlPullParser.END_TAG, ns, AppConstants.TAG_LINK);
        return link;
    }

    private String readTitle(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, AppConstants.TAG_TITLE);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, AppConstants.TAG_TITLE);
        return title;
    }

    private String readImage(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, AppConstants.TAG_DESCRIPTION);
        String dec = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, AppConstants.TAG_DESCRIPTION);
        return dec;
    }

    private String readDate(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, AppConstants.TAG_DATE);
        String date = readText(parser);
        date = StringHelpers.parsePubDate(date);
        parser.require(XmlPullParser.END_TAG, ns, AppConstants.TAG_DATE);
        return date;
    }

    // For the tags title and link, extract their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }


    private ArrayList<News> parseXMLAndStoreIt(XmlPullParser myParser) {

        boolean insideItem = false;

        try {
            // Returns the type of current event: START_TAG, END_TAG, etc..
            int eventType = myParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if (myParser.getName().equalsIgnoreCase("item")) {
                        insideItem = true;
                    } else if (myParser.getName().equalsIgnoreCase("title")) {
                        if (insideItem){
                            //Log.i("....",myParser.nextText()); // extract the headline
                            titles.add(myParser.nextText());
                        }
                    } else if (myParser.getName().equalsIgnoreCase("guid")) {
                        if (insideItem){
                            //Log.i("....",myParser.nextText()); // extract the headline
                            links.add(StringHelpers.parseLink(myParser.nextText()));
                        }
                    }else if (myParser.getName().equalsIgnoreCase("description")) {
                        if (insideItem){
                            //Log.i("....",myParser.nextText()); // extract the headline
                            images.add(StringHelpers.parseDescription(myParser.nextText()));
                        }
                    }else if (myParser.getName().equalsIgnoreCase("pubDate")) {
                        if (insideItem){
                            //Log.i("....",myParser.nextText()); // extract the headline
                            pubDates.add(StringHelpers.parsePubDate(myParser.nextText()));
                        }
                    }
                } else if (eventType == XmlPullParser.END_TAG && myParser.getName().equalsIgnoreCase("item")) {
                    insideItem = false;
                }
                eventType = myParser.next(); // move to next element
            }

            return getNewsList();

        }

        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private ArrayList<News> getNewsList(){
        ArrayList<News> newsList = new ArrayList<>();
        for( int i = 0; i < titles.size(); i++){
            News news = new News(titles.get(i), description.get(i), links.get(i), pubDates.get(i));
            news.setmImageUrl(images.get(i));
            newsList.add(news);
        }
        return  newsList;
    }
}
