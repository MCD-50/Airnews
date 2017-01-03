package com.air.aircovg.helpers;

import com.air.aircovg.events.AsyncResponse;
import com.air.aircovg.scrapper.BbcNews;
import com.air.aircovg.scrapper.Bloomberg;
import com.air.aircovg.scrapper.BusinessInsider;
import com.air.aircovg.scrapper.BusinessInsiderUk;
import com.air.aircovg.scrapper.BuzzFeed;
import com.air.aircovg.scrapper.Cnn;
import com.air.aircovg.scrapper.Engadget;
import com.air.aircovg.scrapper.EntertainmentWeekly;
import com.air.aircovg.scrapper.Espn;
import com.air.aircovg.scrapper.EspnCricInfo;
import com.air.aircovg.scrapper.FinancialTimes;
import com.air.aircovg.scrapper.FoxSport;
import com.air.aircovg.scrapper.GoogleNews;
import com.air.aircovg.scrapper.HackerNews;
import com.air.aircovg.scrapper.MtvNews;
import com.air.aircovg.scrapper.MtvNewsUk;
import com.air.aircovg.scrapper.NationalGeographic;
import com.air.aircovg.scrapper.NewScientist;
import com.air.aircovg.scrapper.NewYorkTimes;
import com.air.aircovg.scrapper.SkySports;
import com.air.aircovg.scrapper.TalkSport;
import com.air.aircovg.scrapper.TechCrunch;
import com.air.aircovg.scrapper.TechRadar;
import com.air.aircovg.scrapper.TheEconomist;
import com.air.aircovg.scrapper.TheVerge;
import com.air.aircovg.scrapper.TheWallStreet;
import com.air.aircovg.scrapper.TimesOfIndia;

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
