package com.example.chrome.aircovg;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chrome.aircovg.events.AsyncResponse;
import com.example.chrome.aircovg.helpers.DatabaseHelper;
import com.example.chrome.aircovg.helpers.NetworkStatusHelper;
import com.example.chrome.aircovg.helpers.ScrappingEngine;
import com.example.chrome.aircovg.helpers.WebClient;
import com.example.chrome.aircovg.model.News;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

/**
 * Created by ayush AS on 31/12/16.
 */

public class DetailedNewsActivity extends AppCompatActivity implements WebClient.ProgressListener {


    TextView author, title, content, published;
    ImageView image;
    News news;
    ProgressDialog progressDialog;
    ProgressBar mProgressBar;
    ScrappingEngine scrappingEngine;
    DatabaseHelper helper;
    Menu menu;
    MenuItem menuItem;
    //ScrollView scrollView;
    NetworkStatusHelper networkStatusHelper;
    WebView webView;
    TextView message;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_news_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //scrappingEngine = new ScrappingEngine(this);
        progressDialog = new ProgressDialog(DetailedNewsActivity.this);
        message = (TextView) findViewById(R.id.detEmptyMessage);
        //scrollView = (ScrollView) findViewById(R.id.scrollBar);
        webView = (WebView) findViewById(R.id.webview);

        final WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.clearHistory();

        helper = new DatabaseHelper(this);

        networkStatusHelper = new NetworkStatusHelper(DetailedNewsActivity.this);
        news = (News)getIntent().getSerializableExtra("newsObject");

        setTitle(news.getmAuthor().substring(0, 1).toUpperCase() + news.getmAuthor().substring(1));

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        webView.setWebChromeClient(new WebClient(this));

    }



    /*private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            showProgressDialog();
        }


        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if(progressDialog != null){
                progressDialog.dismiss();
            }
        }
    }*/

/*    public void showProgressDialog(){

        //progressDialog.setMessage("Please wait...");
        //progressDialog.setIndeterminate(true);
        //progressDialog.setCancelable(true);
        //progressDialog.show();
    }*/


    private void showAlert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(DetailedNewsActivity.this);
        alert.setTitle("Couldn't Connect, Try again");
        alert.setMessage("You need a network connection to use airflow. Please connect your mobile network or WiFi.");
        alert.setPositiveButton("RELOAD",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        webView.loadUrl(news.getmUrl());
                    }
                });
        alert.setNegativeButton("CANCEL", null);
        alert.show();
    }


/*    @Override
    public void processFinish(String data) {
        progressDialog.dismiss();
        scrollView.setVisibility(View.VISIBLE);
        if(news != null && !TextUtils.isEmpty(data)){
            ((TextView)findViewById(R.id.detailed_news_author)).setText(news.getmAuthor());
            ((TextView) findViewById(R.id.detailed_news_title)).setText(news.getmTitle());
            ((TextView) findViewById(R.id.detailed_news_publish)).setText(String.valueOf("Published on " + news.getmPublishedAt()));
            content = (TextView) findViewById(R.id.detailed_news_content);
            content.setMovementMethod(LinkMovementMethod.getInstance());
            content.setText(data);
            Picasso.with(this).load(news.getmImageUrl()).placeholder(R.drawable.back_small).into((ImageView) findViewById(R.id.detailed_news_image));
            Picasso.with(this).load(news.getmImageUrl()).placeholder(R.drawable.back_big).into((ImageView) findViewById(R.id.detailed_news_imageBig));

        }else {

            this.finish();
        }
    }*/

    private void loadData(){
        if(networkStatusHelper.isNetworkAvailable()){
            menuItem.setVisible(true);
            message.setVisibility(View.GONE);

            if(helper.isNewsExists(news.getmUrl())){
                menuItem.setIcon(R.drawable.ic_starred);
            }else {
                menuItem.setIcon(R.drawable.ic_not_starred);
            }

            webView.setWebViewClient(new WebViewClient() {

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    view.loadUrl(news.getmUrl());
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
            //showProgressDialog();
            webView.loadUrl(news.getmUrl());
            //scrappingEngine.resolveData(news.getmUrl(), news.getmSource());
        }else {

            menuItem.setVisible(false);
            //item.setVisible(false);
            //scrollView.setVisibility(View.GONE);
            //webView.setVisibility(View.co);
            message.setVisibility(View.VISIBLE);
        }




        /*webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                progressDialog.dismiss();
                showAlert();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                showProgressDialog();
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                progressDialog.dismiss();
                String webUrl = webView.getUrl();
            }

        });*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu x) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, x);
        menu = x;
        menuItem = menu.findItem(R.id.starAction);
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
        if(helper.isNewsExists(news.getmUrl())){
            helper.deleteNews(news.getmUrl(), true);
            menuItem.setIcon(R.drawable.ic_not_starred);
        }else {
            helper.addNews(news);
            menuItem.setIcon(R.drawable.ic_starred);
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
