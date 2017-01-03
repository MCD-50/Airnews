package com.air.aircovg;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.air.aircovg.adapters.CategoriesAdapter;
import com.air.aircovg.helpers.CategoriesHelper;

/**
 * Created by ayush AS on 29/12/16.
 */

public class CategoriesActivity extends AppCompatActivity {


    ListView mListView;
    CategoriesAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mListView = (ListView) findViewById(R.id.listView);
        adapter = new CategoriesAdapter(CategoriesActivity.this);
        mListView.setAdapter(adapter);
        mListView.setDividerHeight(0);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(CategoriesActivity.this, CategoriesNewsActivity.class);
                i.putExtra("categoryName", adapter.getItem(position).getmName());
                startActivity(i);
            }
        });
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
