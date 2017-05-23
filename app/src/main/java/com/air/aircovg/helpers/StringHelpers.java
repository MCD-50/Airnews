package com.air.aircovg.helpers;

import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.text.style.ImageSpan;

import org.jsoup.helper.StringUtil;

/**
 * Created by ayush AS on 20/5/17.
 */

public class StringHelpers {

    public static String parseDescription(String description){
        String url = null;
        try{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                url =  getDescriptionSafely(Html.fromHtml(description ,Html.FROM_HTML_MODE_LEGACY).toString());
            }else{
                url =  getDescriptionSafely(Html.fromHtml(description).toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return url;
    }

    public static String parseImage(String description){
        String x = null;
        try{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                x = getImageSafely(Html.fromHtml(description ,Html.FROM_HTML_MODE_LEGACY));
            }else{
                x = getImageSafely(Html.fromHtml(description));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return x ;
    }

    public static String parseLink(String link){
        return link.split("=")[1].trim();
    }

    public static String parsePubDate(String pubDate){
        return pubDate.replace("GMT", " ").trim();
    }

    public static String getFullNewsUrl(String ned, String q){
        String query = ned.equals("all") ? (q == null ? AppConstants.BASE + AppConstants.OUTPUT
                : AppConstants.BASE + AppConstants.QUERY + q + AppConstants.OUTPUT) :
                (q == null ? AppConstants.BASE + AppConstants.NEWS_EDITION + ned + AppConstants.OUTPUT
                : AppConstants.BASE + AppConstants.NEWS_EDITION + ned + AppConstants.QUERY + q + AppConstants.OUTPUT);
        return query;
    }

    private static String getDescriptionSafely(String des){
        String[] _x = des
                .replaceAll("\n", " ")
                .replaceAll("[^a-zA-Z0-9 ]", "")
                .trim()
                .split("  ");
        if(_x.length > 2){
            return _x[0] + "\n\n" + _x[2];
        }else if(_x.length > 0){
            return _x[0];
        }else{
            return "No description available for this article.";
        }
    }

    private static String getImageSafely(Spanned spanned){
        String x = null;
        for (ImageSpan span : spanned.getSpans(0, spanned.length(), ImageSpan.class)){
            if(span.getSource() != null){
                x = "http:" + span.getSource();
                break;
            }
        }
        return x;
    }
}
