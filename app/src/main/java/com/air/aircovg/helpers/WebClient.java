package com.air.aircovg.helpers;

import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by ayush AS on 2/1/17.
 */

public class WebClient extends WebChromeClient {
    private ProgressListener mListener;
    public WebClient(ProgressListener listener) {
        mListener = listener;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        mListener.onUpdateProgress(newProgress);
        super.onProgressChanged(view, newProgress);
    }

    public interface ProgressListener {
        void onUpdateProgress(int progressValue);
    }
}
