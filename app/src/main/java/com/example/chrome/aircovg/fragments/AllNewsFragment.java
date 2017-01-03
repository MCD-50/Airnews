package com.example.chrome.aircovg.fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.chrome.aircovg.MainActivity;
import com.example.chrome.aircovg.R;
import com.example.chrome.aircovg.adapters.AllNewsAdapter;
import com.example.chrome.aircovg.helpers.AppConstants;
import com.example.chrome.aircovg.helpers.CategoriesHelper;
import com.example.chrome.aircovg.helpers.ChannelsHelper;
import com.example.chrome.aircovg.helpers.InternetHelper;
import com.example.chrome.aircovg.helpers.JsonHelper;
import com.example.chrome.aircovg.helpers.NetworkStatusHelper;
import com.example.chrome.aircovg.helpers.SharedPreferenceHelper;
import com.example.chrome.aircovg.model.News;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * Created by ayush AS on 25/12/16.
 */

public class AllNewsFragment extends Fragment {

    ProgressDialog mProgressDialog;
    ArrayList<News> mNews;
    JsonHelper jsonHelper;
    InternetHelper internetHelper;
    ChannelsHelper channelsHelper;
    AllNewsAdapter allNewsAdapter;
    ListView mListView;
    int currentPage = 0;
    int channelLength = 0;
    String[] channels;
    SharedPreferenceHelper sharedPreferenceHelper;
    NetworkStatusHelper networkStatusHelper;
    Button button;
    TextView message;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.all_news_layout, container, false);

        mProgressDialog = new ProgressDialog(getContext());
        jsonHelper = new JsonHelper();
        channelsHelper = new ChannelsHelper();
        internetHelper = new InternetHelper();

        channels = channelsHelper.getAllChannels();
        channelLength = channels.length;
        networkStatusHelper = new NetworkStatusHelper(getContext());
        Collections.shuffle(Arrays.asList(channels));


        mListView = (ListView) rootView.findViewById(R.id.listView);
        message = (TextView) rootView.findViewById(R.id.emptyMessage);
        button = (Button) rootView.findViewById(R.id.refreshNow);
        mListView.setDividerHeight(0);

        return rootView;
    }



    private void setAdapter() {
        mNews = new ArrayList<>();
        allNewsAdapter = new AllNewsAdapter(getContext(), mNews);
        mListView.setAdapter(allNewsAdapter);
    }

    private void loadData(int count) {
        new getNewsAsync(count).execute();
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sharedPreferenceHelper = new SharedPreferenceHelper(getActivity().getApplicationContext());

        setAdapter();


        if(networkStatusHelper.isNetworkAvailable()){
            executeInner();
        }else {
            message.setVisibility(View.VISIBLE);
            button.setVisibility(View.VISIBLE);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeOnNetworkAvailable();
            }
        });
    }


    private void executeOnNetworkAvailable(){
        if(networkStatusHelper.isNetworkAvailable()){
            executeInner();
        }else {
            message.setVisibility(View.VISIBLE);
            button.setVisibility(View.VISIBLE);
        }
    }


    private void executeInner(){
        loadData(currentPage);
        message.setVisibility(View.GONE);
        button.setVisibility(View.GONE);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((MainActivity)getActivity()).showNews(allNewsAdapter.getItem(position), false);
            }
        });

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                int threshold = 1;
                int itemCount = mListView.getCount();
                if (scrollState == SCROLL_STATE_IDLE) {
                    if (mListView.getLastVisiblePosition() >= itemCount - threshold && currentPage < channelLength - 1) {
                        // Execute LoadMoreDataTask AsyncTask
                        loadData(currentPage);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    public class getNewsAsync extends AsyncTask<Void, Void, ArrayList<News>> {

        String[] channelList;
        public getNewsAsync(int count){
            channelList = new String[] {channels[count], channels[count + 1]};
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
                  for(String channel : channelList){
                     for(News n : jsonHelper.getAllNews(internetHelper.getJsonString(channel, sharedPreferenceHelper.getData()))){
                          allNewses.add(n);
                      }
                  }
            }catch (Exception file){
                try{
                    allNewses.clear();
                    for(String channel : channelList){
                        for(News n : jsonHelper.getAllNews(internetHelper.getJsonString(channel, "top"))){
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
                allNewsAdapter.notifyDataSetChanged();
            }

        }
    }

}
