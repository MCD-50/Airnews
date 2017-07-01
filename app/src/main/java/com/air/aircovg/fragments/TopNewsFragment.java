package com.air.aircovg.fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.air.aircovg.MainActivity;
import com.air.aircovg.R;
import com.air.aircovg.adapters.NewsAdapter;
import com.air.aircovg.helpers.AppConstants;
import com.air.aircovg.helpers.CollectionHelper;
import com.air.aircovg.helpers.InternetHelper;
import com.air.aircovg.helpers.NetworkStatusHelper;
import com.air.aircovg.helpers.SharedPreferenceHelper;
import com.air.aircovg.model.News;

import java.util.ArrayList;

/**
 * Created by ayush AS on 25/12/16.
 */

public class TopNewsFragment extends Fragment {

    NetworkStatusHelper networkStatusHelper;
    InternetHelper internetHelper;

    SharedPreferenceHelper sharedPreferenceHelper;
    NewsAdapter newsAdapter;
    ArrayList<News> mNews;

    CollectionHelper collectionHelper;

    ProgressDialog mProgressDialog;


    TextView mTextView;
    ListView mListView;
    SwipeRefreshLayout mSwipeRefreshLayout;

    int nextPage = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.news_layout, container, false);

        networkStatusHelper = new NetworkStatusHelper(getContext());
        internetHelper = new InternetHelper();
        sharedPreferenceHelper = new SharedPreferenceHelper(getActivity().getApplicationContext());
        mProgressDialog = new ProgressDialog(getContext());
        collectionHelper = new CollectionHelper();
        mTextView = (TextView) rootView.findViewById(R.id.emptyMessage);

        mListView = (ListView) rootView.findViewById(R.id.listView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh_layout);
        mListView.setDividerHeight(0);
        return rootView;
    }


    private void setAdapter() {
        mNews = new ArrayList<>();
        newsAdapter = new NewsAdapter(getContext(), mNews, false);
        mListView.setAdapter(newsAdapter);
    }

    private void loadData(int nextPage, boolean showDialog) {
        new getNewsAsync(nextPage, showDialog).execute();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sharedPreferenceHelper = new SharedPreferenceHelper(getActivity().getApplicationContext());
        setAdapter();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (networkStatusHelper.isNetworkAvailable()) {
                    onNetworkAvailable(1, false);
                } else {
                    mTextView.setVisibility(View.VISIBLE);
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });
        checkAndLoad();
    }

    public void checkAndLoad(){
        if (networkStatusHelper.isNetworkAvailable()) {
            onNetworkAvailable(nextPage, true);
        } else {
            mTextView.setVisibility(View.VISIBLE);
        }
    }


    private void onNetworkAvailable(int page,boolean showDialog){
        loadData(page, showDialog);
        mTextView.setVisibility(View.GONE);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((MainActivity)getActivity()).showNews(newsAdapter.getItem(position), false);
            }
        });

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                int threshold = 1;
                int itemCount = mListView.getCount();
                if (scrollState == SCROLL_STATE_IDLE) {
                    if (mListView.getLastVisiblePosition() >= itemCount - threshold && itemCount < 100) {
                        // Execute LoadMoreDataTask AsyncTask
                        loadData(nextPage, true);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    public class getNewsAsync extends AsyncTask<Void, Void, ArrayList<News>> {

        int mNextPage;
        boolean mShowDialog;
        public getNewsAsync(int nextPage, boolean showDialog){
            mNextPage = nextPage;
            mShowDialog = showDialog;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(mShowDialog){
                mProgressDialog.setMessage("Please wait...");
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
            }else {
                mNews.clear();
            }
        }

        @Override
        protected ArrayList<News> doInBackground(Void... params) {
            ArrayList<News> newsList= new ArrayList<>();
            try{
                String selectedCountry = sharedPreferenceHelper.getData(AppConstants.TAG_COUNTRY, "india");
                String selectedLanguage = sharedPreferenceHelper.getData(AppConstants.TAG_LANGUAGE, "english");

                String jsonString = internetHelper.getJsonString(AppConstants.BASE, mNextPage, selectedCountry, selectedLanguage);
                newsList = collectionHelper.getNewsListFromJsonString(jsonString);

                nextPage += 1;
            }catch (Exception e){
                e.printStackTrace();
            }
            return newsList;
        }

        @Override
        protected void onPostExecute(ArrayList<News> newsArrayList) {
            if(mShowDialog) mProgressDialog.dismiss();

            if (newsArrayList.size() > 0) {
                for(News x : newsArrayList) {
                    mNews.add(x);
                }
                mSwipeRefreshLayout.setRefreshing(false);
                newsAdapter.notifyDataSetChanged();
            }else {
                mTextView.setVisibility(View.VISIBLE);
            }
        }
    }

}
