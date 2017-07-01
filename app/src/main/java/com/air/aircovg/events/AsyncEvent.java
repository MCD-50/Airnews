package com.air.aircovg.events;

/**
 * Created by mcd-50 on 29/6/17.
 */

public interface AsyncEvent {
    void onProgressShow();
    void onProgressHide();
    void onFinished(int newPage);
}
