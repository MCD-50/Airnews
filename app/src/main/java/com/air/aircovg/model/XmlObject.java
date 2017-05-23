package com.air.aircovg.model;

import java.util.ArrayList;

/**
 * Created by ayush AS on 20/5/17.
 */

public class XmlObject {
    private ArrayList<String> titles, links, images, pubDates;
    public XmlObject(ArrayList<String> titles, ArrayList<String> links, ArrayList<String> images, ArrayList<String> pubDates){
        this.titles = titles;
        this.images = images;
        this.links = links;
        this.pubDates = pubDates;
    }


    public ArrayList<String> getTitles() {
        return titles;
    }

    public void setTitles(ArrayList<String> titles) {
        this.titles = titles;
    }

    public ArrayList<String> getLinks() {
        return links;
    }

    public void setLinks(ArrayList<String> links) {
        this.links = links;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public ArrayList<String> getPubDates() {
        return pubDates;
    }

    public void setPubDates(ArrayList<String> pubDates) {
        this.pubDates = pubDates;
    }
}
