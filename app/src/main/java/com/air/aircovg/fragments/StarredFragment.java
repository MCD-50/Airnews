package com.air.aircovg.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.air.aircovg.MainActivity;
import com.air.aircovg.R;
import com.air.aircovg.adapters.StarredAdapter;
import com.air.aircovg.events.DatabaseEvents;
import com.air.aircovg.helpers.DatabaseHelper;
import com.air.aircovg.helpers.EventHelper;
import com.air.aircovg.model.News;
import com.air.aircovg.scrapper.MtvNews;

import java.util.ArrayList;

/**
 * Created by ayush AS on 25/12/16.
 */

public class StarredFragment extends Fragment implements DatabaseEvents {

    StarredAdapter starredAdapter;
    ArrayList<News> mNews;
    DatabaseHelper databaseHelper;
    ListView mListView;
    TextView message;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.starred_layout, container, false);
        EventHelper.initDatabaseEvent(this);
        mListView = (ListView) rootView.findViewById(R.id.listView);
        mListView.setDividerHeight(0);
        message = (TextView) rootView.findViewById(R.id.emptyMessage);
        return rootView;
    }

    public void loadAdapter(){
        mNews = new ArrayList<>();
        starredAdapter = new StarredAdapter(getContext(),mNews);
        mListView.setAdapter(starredAdapter);
        updateMessage();
    }

    private void updateMessage(){
        if(mNews.size() > 0)
            message.setVisibility(View.GONE);
        else
            message.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadAdapter();
        databaseHelper = new DatabaseHelper(getContext());
        for(News n : databaseHelper.getAllNews()){
            mNews.add(n);
        }

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((MainActivity)getActivity()).showNews(starredAdapter.getItem(position), true);
            }
        });
    }

    @Override
    public void addOrRemoveNews(News news, boolean isAdded) {
        if(!isAdded){
            for(int i = 0; i < mNews.size(); i++){
                if(mNews.get(i).getmUrl().equalsIgnoreCase(news.getmUrl())){
                    mNews.remove(i);
                    break;
                }
            }
        }else {
            mNews.add(news);
        }
        starredAdapter.notifyDataSetChanged();
        updateMessage();
    }

}
