package com.air.aircovg;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.air.aircovg.adapters.SlidingAdapter;
import com.air.aircovg.events.ClickListener;
import com.air.aircovg.helpers.AppConstants;
import com.air.aircovg.helpers.SharedPreferenceHelper;
import com.klinker.android.sliding.SlidingActivity;

/**
 * Created by mcd-50 on 1/7/17.
 */

public class CountryActivity extends SlidingActivity implements ClickListener {

    SharedPreferenceHelper sharedPreferenceHelper;
    @Override
    public void init(Bundle savedInstanceState) {
        setTitle("Choose your country");

        setPrimaryColors(
                getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorPrimaryDark)
        );

        sharedPreferenceHelper = new SharedPreferenceHelper(getApplicationContext());

        setContent(R.layout.language_layout);
        RecyclerView listView = (RecyclerView) findViewById(R.id.language_list);
        listView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CountryActivity.this);
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(new SlidingAdapter(CountryActivity.this, AppConstants.COUNTRY_LIST, this,  sharedPreferenceHelper.getData(AppConstants.TAG_COUNTRY, "india")));
    }

    @Override
    public void OnItemClick(String item) {
        sharedPreferenceHelper.saveData(AppConstants.TAG_COUNTRY, item);
        finish();
    }
}