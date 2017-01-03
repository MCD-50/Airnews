package com.example.chrome.aircovg.helpers;

import com.example.chrome.aircovg.events.AsyncResponse;
import com.example.chrome.aircovg.scrapper.BbcNews;
import com.example.chrome.aircovg.scrapper.Bloomberg;
import com.example.chrome.aircovg.scrapper.BusinessInsider;
import com.example.chrome.aircovg.scrapper.BusinessInsiderUk;
import com.example.chrome.aircovg.scrapper.BuzzFeed;
import com.example.chrome.aircovg.scrapper.Cnn;
import com.example.chrome.aircovg.scrapper.Engadget;
import com.example.chrome.aircovg.scrapper.EntertainmentWeekly;
import com.example.chrome.aircovg.scrapper.Espn;
import com.example.chrome.aircovg.scrapper.EspnCricInfo;
import com.example.chrome.aircovg.scrapper.FinancialTimes;
import com.example.chrome.aircovg.scrapper.FoxSport;
import com.example.chrome.aircovg.scrapper.GoogleNews;
import com.example.chrome.aircovg.scrapper.HackerNews;
import com.example.chrome.aircovg.scrapper.MtvNews;
import com.example.chrome.aircovg.scrapper.MtvNewsUk;
import com.example.chrome.aircovg.scrapper.NationalGeographic;
import com.example.chrome.aircovg.scrapper.NewScientist;
import com.example.chrome.aircovg.scrapper.NewYorkTimes;
import com.example.chrome.aircovg.scrapper.SkySports;
import com.example.chrome.aircovg.scrapper.TalkSport;
import com.example.chrome.aircovg.scrapper.TechCrunch;
import com.example.chrome.aircovg.scrapper.TechRadar;
import com.example.chrome.aircovg.scrapper.TheEconomist;
import com.example.chrome.aircovg.scrapper.TheVerge;
import com.example.chrome.aircovg.scrapper.TheWallStreet;
import com.example.chrome.aircovg.scrapper.TimesOfIndia;

/**
 * Created by ayush AS on 27/12/16.
 */


public class ScrappingEngine{

    private AsyncResponse asyncResponse;
    public ScrappingEngine(AsyncResponse response) {
        asyncResponse = response;
    }



    public void resolveData(String url, String source){
        switch (source.toLowerCase()){
            case "bbc-news" : new BbcNews(asyncResponse).execute(url);
                break;
            case "cnn" : new Cnn(asyncResponse).execute(url);
                break;
            case "google-news" : new GoogleNews(asyncResponse).execute(url);
                break;
            case "the-new-york-times" : new NewYorkTimes(asyncResponse).execute(url);
                break;
            case "the-times-of-india" : new TimesOfIndia(asyncResponse).execute(url);
                break;
            case "engadget" : new Engadget(asyncResponse).execute(url);
                break;
            case "hacker-news" : new HackerNews(asyncResponse).execute(url);
                break;
            case "techcrunch" : new TechCrunch(asyncResponse).execute(url);
                break;
            case "techradar" : new TechRadar(asyncResponse).execute(url);
                break;
            case "the-verge" : new TheVerge(asyncResponse).execute(url);
                break;
            case "espn" : new Espn(asyncResponse).execute(url);
                break;
            case "fox-sports" : new FoxSport(asyncResponse).execute(url);
                break;
            case "espn-cric-info" : new EspnCricInfo(asyncResponse).execute(url);
                break;
            case "sky-sports-news" : new SkySports(asyncResponse).execute(url);
                break;
            case "talksport" : new TalkSport(asyncResponse).execute(url);
                break;

            case "bloomberg" : new Bloomberg(asyncResponse).execute(url);
                break;
            case "business-insider" : new BusinessInsider(asyncResponse).execute(url);
                break;
            case "business-insider-uk" : new BusinessInsiderUk(asyncResponse).execute(url);
                break;
            case "the-economist" : new TheEconomist(asyncResponse).execute(url);
                break;
            case "the-wall-street-journal" : new TheWallStreet(asyncResponse).execute(url);
                break;
            case "financial-times" : new FinancialTimes(asyncResponse).execute(url);
                break;

            case "buzzfeed" : new BuzzFeed(asyncResponse).execute(url);
                break;
            case "entertainment-weekly" : new EntertainmentWeekly(asyncResponse).execute(url);
                break;
            case "mtv-news" : new MtvNews(asyncResponse).execute(url);
                break;
            case "mtv-news-uk" : new MtvNewsUk(asyncResponse).execute(url);
                break;
            case "national-geographic" : new NationalGeographic(asyncResponse).execute(url);
                break;
            case "new-scientist" : new NewScientist(asyncResponse).execute(url);
                break;

            default:
                asyncResponse.processFinish(" ");

        }


    }



}
