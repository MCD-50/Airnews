package com.example.chrome.aircovg.helpers;

import java.util.ArrayList;

/**
 * Created by ayush AS on 27/12/16.
 */

public class ChannelsHelper {

    String[] general={"bbc-news","cnn","google-news","the-times-of-india"};
    String[] technology={"engadget","hacker-news","techcrunch","techradar","the-verge"};
    String[] sport={"espn","fox-sports","espn-cric-info","sky-sports-news","talksport"};
    String[] business={"bloomberg","business-insider","business-insider-uk","the-economist","the-wall-street-journal","financial-times"};
    String[] entertainment={"buzzfeed","entertainment-weekly"};
    String[] music={"mtv-news","mtv-news-uk"};
    String[] science={"national-geographic","new-scientist"};

    public String[] getListFromCategory(String string){

       if(string.contains("general")){
           return general;
       }else if(string.contains("sport")){
           return sport;
       }else if(string.contains("technology")){
           return technology;
       }else if(string.contains("business")){
           return business;
       }else if(string.contains("entertainment")){
           return entertainment;
       }else if(string.contains("music")){
           return music;
       }else {
           return science;
       }

    }

    public String[] getAllChannels(){
        return new String[]{
                "bbc-news","cnn","google-news","the-times-of-india",
                "engadget","hacker-news","techcrunch","techradar","the-verge",
                "espn","fox-sports","espn-cric-info","sky-sports-news","talksport",
                "bloomberg","business-insider","business-insider-uk","the-economist","the-wall-street-journal","financial-times",
                "buzzfeed","entertainment-weekly",
                "mtv-news","mtv-news-uk",
                "national-geographic","new-scientist"
        };
    }
}
