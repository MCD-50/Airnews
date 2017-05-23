package com.air.aircovg.fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.air.aircovg.MainActivity;
import com.air.aircovg.R;
import com.air.aircovg.adapters.NewsAdapter;
import com.air.aircovg.helpers.RssHelper;
import com.air.aircovg.helpers.InternetHelper;
import com.air.aircovg.helpers.NetworkStatusHelper;
import com.air.aircovg.helpers.SharedPreferenceHelper;
import com.air.aircovg.helpers.StringHelpers;
import com.air.aircovg.model.News;
import com.air.aircovg.model.XmlObject;

import java.util.ArrayList;

/**
 * Created by ayush AS on 25/12/16.
 */

public class AllNewsFragment extends Fragment {

    NetworkStatusHelper networkStatusHelper;
    InternetHelper internetHelper;

    SharedPreferenceHelper sharedPreferenceHelper;
    NewsAdapter newsAdapter;
    ArrayList<News> mNews;

    RssHelper rssHelper;

    ProgressDialog mProgressDialog;

    Button mButton;
    TextView mTextView;
    ListView mListView;

    int currentPage = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.all_news_layout, container, false);

        networkStatusHelper = new NetworkStatusHelper(getContext());
        internetHelper = new InternetHelper();
        rssHelper = new RssHelper();
        mProgressDialog = new ProgressDialog(getContext());

        mTextView = (TextView) rootView.findViewById(R.id.emptyMessage);
        mButton = (Button) rootView.findViewById(R.id.refreshNow);
        mListView = (ListView) rootView.findViewById(R.id.listView);
        mListView.setDividerHeight(0);
        return rootView;
    }


    private void setAdapter() {
        mNews = new ArrayList<>();
        newsAdapter = new NewsAdapter(getContext(), mNews, false);
        mListView.setAdapter(newsAdapter);
    }

    private void loadData(int count) {
        //we are not using paging
        new getNewsAsync().execute();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sharedPreferenceHelper = new SharedPreferenceHelper(getActivity().getApplicationContext());
        setAdapter();

        if(networkStatusHelper.isNetworkAvailable()){
            onNetworkAvailable();
        }else {
            mTextView.setVisibility(View.VISIBLE);
            mButton.setVisibility(View.VISIBLE);
        }

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefreshClick();
            }
        });
    }


    private void onRefreshClick(){
        if(networkStatusHelper.isNetworkAvailable()){
            onNetworkAvailable();
        }else {
            mTextView.setVisibility(View.VISIBLE);
            mButton.setVisibility(View.VISIBLE);
        }
    }


    private void onNetworkAvailable(){
        loadData(currentPage);
        mTextView.setVisibility(View.GONE);
        mButton.setVisibility(View.GONE);
        loadData(0);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((MainActivity)getActivity()).showNews(newsAdapter.getItem(position), false);
            }
        });
    }

    public class getNewsAsync extends AsyncTask<Void, Void, ArrayList<News>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        @Override
        protected ArrayList<News> doInBackground(Void... params) {
            ArrayList<News> newsList= new ArrayList<>();
            try{
                String ned = sharedPreferenceHelper.getData("ned");
                String q = StringHelpers.getFullNewsUrl(ned, "education");
                newsList = rssHelper.fetchXML(q);
            }catch (Exception e){
                e.printStackTrace();
            }
            return newsList;
        }

        @Override
        protected void onPostExecute(ArrayList<News> newsArrayList) {
            mProgressDialog.dismiss();
            if (newsArrayList.size() > 0) {
                for(News x : newsArrayList)
                    mNews.add(x);
                //currentPage += 2;
                newsAdapter.notifyDataSetChanged();
            }

        }
    }

}
