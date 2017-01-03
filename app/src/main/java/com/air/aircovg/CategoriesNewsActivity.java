package com.air.aircovg;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.air.aircovg.adapters.CategoriesAdapter;
import com.air.aircovg.adapters.CategoriesNewsAdapter;
import com.air.aircovg.helpers.CategoriesHelper;
import com.air.aircovg.helpers.ChannelsHelper;
import com.air.aircovg.helpers.InternetHelper;
import com.air.aircovg.helpers.JsonHelper;
import com.air.aircovg.helpers.NetworkStatusHelper;
import com.air.aircovg.helpers.SharedPreferenceHelper;
import com.air.aircovg.model.News;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * Created by ayush AS on 29/12/16.
 */

public class CategoriesNewsActivity extends AppCompatActivity {

    ListView mListView;
    CategoriesNewsAdapter adapter;
    ChannelsHelper channelsHelper;
    ArrayList<News> mNews;
    String[] channels;
    ProgressDialog mProgressDialog;
    JsonHelper jsonHelper;
    InternetHelper internetHelper;
    SharedPreferenceHelper sharedPreferenceHelper;
    int currentPage = 0;
    int channelLength = 0;

    NetworkStatusHelper networkStatusHelper;
    TextView message;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories_news_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        message = (TextView) findViewById(R.id.catEmptyMessage);
        mListView = (ListView) findViewById(R.id.listView);
        jsonHelper = new JsonHelper();
        channelsHelper = new ChannelsHelper();
        internetHelper = new InternetHelper();
        String category = getIntent().getStringExtra("categoryName");
        channels = channelsHelper.getListFromCategory(category);
        mProgressDialog = new ProgressDialog(this);
        channelLength = channels.length;

        sharedPreferenceHelper = new SharedPreferenceHelper(getApplicationContext());

        setTitle(category.substring(0, 1).toUpperCase() + category.substring(1));
        networkStatusHelper = new NetworkStatusHelper(CategoriesNewsActivity.this);
        //Collections.shuffle(Arrays.asList(channels));

        mNews = new ArrayList<>();
        adapter = new CategoriesNewsAdapter(CategoriesNewsActivity.this, mNews);
        mListView.setAdapter(adapter);
        mListView.setDividerHeight(0);

        if(networkStatusHelper.isNetworkAvailable()){
            message.setVisibility(View.GONE);
            loadData(currentPage);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    showNews(adapter.getItem(position));
                }
            });
            mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    int threshold = 1;
                    int itemCount = mListView.getCount();
                    if (scrollState == SCROLL_STATE_IDLE) {
                        if (mListView.getLastVisiblePosition() >= itemCount - threshold && currentPage < channelLength - 1) {
                            loadData(currentPage);
                        }
                    }
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                }
            });
        }else {

            message.setVisibility(View.VISIBLE);
        }

    }

    public void showNews(News news){
        Intent i = new Intent(CategoriesNewsActivity.this, DetailedNewsActivity.class);
        i.putExtra("newsObject", news);
        startActivity(i);
    }

    public void loadData(int count) {
        new getNewsAsync(count).execute();
    }

    public class getNewsAsync extends AsyncTask<Void, Void, ArrayList<News>> {

        String[] channelList;
        public getNewsAsync(int count){
            channelList = new String[] {channels[count],
                    channels[count + 1]};
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        @Override
        protected ArrayList<News> doInBackground(Void... params) {
            ArrayList<News> allNewses = new ArrayList<News>();

            try {
                for(String x : channelList){
                    for(News n : jsonHelper.getAllNews(internetHelper.getJsonString(x, sharedPreferenceHelper.getData()))){
                        allNewses.add(n);
                    }
                }
            } catch (Exception file){
                try{
                    allNewses.clear();
                    for(String x : channelList){
                        for(News n : jsonHelper.getAllNews(internetHelper.getJsonString(x, "top"))){
                            allNewses.add(n);
                        }
                    }
                }catch (Exception any){
                    any.printStackTrace();
                }

            }

            return allNewses;
        }

        @Override
        protected void onPostExecute(ArrayList<News> newsArrayList) {
            mProgressDialog.dismiss();

            if (newsArrayList.size() > 0) {
                for(News x : newsArrayList)
                    mNews.add(x);

                currentPage += 2;
                adapter.notifyDataSetChanged();
            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Write your logic here
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
