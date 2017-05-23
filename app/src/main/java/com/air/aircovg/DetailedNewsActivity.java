package com.air.aircovg;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.air.aircovg.helpers.DatabaseHelper;
import com.air.aircovg.helpers.NetworkStatusHelper;
import com.air.aircovg.helpers.WebClient;
import com.air.aircovg.model.News;

/**
 * Created by ayush AS on 31/12/16.
 */

public class DetailedNewsActivity extends AppCompatActivity implements WebClient.ProgressListener {

    News mNews;

    ProgressDialog progressDialog;
    ProgressBar mProgressBar;

    DatabaseHelper databaseHelper;
    NetworkStatusHelper networkStatusHelper;

    Menu mMenu;
    MenuItem mMenuItem;


    WebView mWebView;
    TextView mTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_news_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mTextView = (TextView) findViewById(R.id.emptyMessage);
        mWebView = (WebView) findViewById(R.id.webview);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        progressDialog = new ProgressDialog(DetailedNewsActivity.this);
        final WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mWebView.clearHistory();
        databaseHelper = new DatabaseHelper(this);

        networkStatusHelper = new NetworkStatusHelper(DetailedNewsActivity.this);
        mNews = (News)getIntent().getSerializableExtra("newsObject");

        setTitle(mNews.getmTitle().substring(0, 1).toUpperCase() + mNews.getmTitle().substring(1));


        mWebView.setWebChromeClient(new WebClient(this));
    }



    private void loadData(){
        if(networkStatusHelper.isNetworkAvailable()){
            mMenuItem.setVisible(true);
            mTextView.setVisibility(View.GONE);

            if(databaseHelper.isNewsExists(mNews.getmUrl())){
                mMenuItem.setIcon(R.drawable.ic_starred);
            }else {
                mMenuItem.setIcon(R.drawable.ic_not_starred);
            }

            mWebView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    view.loadUrl(mNews.getmUrl());
                    return super.shouldOverrideUrlLoading(view, request);
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    mProgressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    mProgressBar.setVisibility(View.GONE);
                }
            });
            mWebView.loadUrl(mNews.getmUrl());
        }else {

            mMenuItem.setVisible(false);
            mTextView.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mMenu = menu;
        mMenuItem = menu.findItem(R.id.starAction);
        loadData();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;

            case R.id.starAction:
                 addRemoveToggle();
                 return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addRemoveToggle(){
        if(databaseHelper.isNewsExists(mNews.getmUrl())){
            databaseHelper.deleteNews(mNews.getmUrl(), true);
            mMenuItem.setIcon(R.drawable.ic_not_starred);
        }else {
            databaseHelper.addNews(mNews);
            mMenuItem.setIcon(R.drawable.ic_starred);
        }
    }

    @Override
    public void onUpdateProgress(int progressValue) {
        mProgressBar.setProgress(progressValue);
        if (progressValue >= 90) {
            mProgressBar.setVisibility(View.GONE);
        }
    }
}
