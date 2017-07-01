package com.air.aircovg;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.air.aircovg.adapters.SlidingAdapter;
import com.air.aircovg.events.ClickListener;
import com.air.aircovg.helpers.AppConstants;
import com.air.aircovg.helpers.SharedPreferenceHelper;
import com.klinker.android.sliding.MultiShrinkScroller;
import com.klinker.android.sliding.SlidingActivity;

/**
 * Created by mcd-50 on 29/6/17.
 */

public class LanguageActivity extends SlidingActivity implements ClickListener {

    SharedPreferenceHelper sharedPreferenceHelper;
    @Override
    public void init(Bundle savedInstanceState) {
        setTitle("Choose a language");

        setPrimaryColors(
                getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorPrimaryDark)
        );

        sharedPreferenceHelper = new SharedPreferenceHelper(getApplicationContext());

        setContent(R.layout.language_layout);
        RecyclerView listView = (RecyclerView) findViewById(R.id.language_list);
        listView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(LanguageActivity.this);
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(new SlidingAdapter(LanguageActivity.this, AppConstants.LANGUAGE_LIST, this,  sharedPreferenceHelper.getData(AppConstants.TAG_LANGUAGE, "english")));
    }

    @Override
    public void OnItemClick(String item) {
        sharedPreferenceHelper.saveData(AppConstants.TAG_LANGUAGE, item);
        finish();
    }
}

