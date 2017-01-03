package com.air.aircovg.events;

import com.air.aircovg.model.News;

/**
 * Created by ayush AS on 27/12/16.
 */

public interface DatabaseEvents {
    void addOrRemoveNews(News news, boolean isAdded);
}
