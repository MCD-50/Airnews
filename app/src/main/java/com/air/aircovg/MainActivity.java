package com.air.aircovg;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.air.aircovg.events.AsyncEvent;
import com.air.aircovg.fragments.EntertainmentNewsFragment;
import com.air.aircovg.fragments.LifestyleNewsFragment;
import com.air.aircovg.fragments.PinnedNewsFragment;
import com.air.aircovg.fragments.PoliticsNewsFragment;
import com.air.aircovg.fragments.SportsNewsFragment;
import com.air.aircovg.fragments.TopNewsFragment;
import com.air.aircovg.helpers.AppConstants;
import com.air.aircovg.helpers.NetworkStatusHelper;
import com.astuetz.PagerSlidingTabStrip;
import com.air.aircovg.adapters.TabsPagerAdapter;
import com.air.aircovg.model.News;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    //versionName = getBaseContext().getPackageManager().getPackageInfo(getBaseContext().getPackageName(), 0 ).versionName;
    //versionCode = getBaseContext().getPackageManager().getPackageInfo(getBaseContext().getPackageName(), 0 ).versionCode;
    //String myUserAgent = "Airnews"

    TabsPagerAdapter tabsPagerAdapter;
    ViewPager viewPager;
    NavigationView navigationView;
    com.getbase.floatingactionbutton.FloatingActionButton fabShare, fabSettings, fabLanguage, fabCountry;
    CoordinatorLayout coordinatorLayout;
    NetworkStatusHelper networkStatusHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        networkStatusHelper = new NetworkStatusHelper(MainActivity.this);

        viewPager = (ViewPager) findViewById(R.id.activity_main_pager);
        tabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabsPagerAdapter);
        viewPager.setOffscreenPageLimit(5);


        fabCountry = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.fab_country);
        fabLanguage = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.fab_language);
        fabShare = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.fab_share);
        fabSettings = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.fab_setting);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.cordinateLayout);

        PagerSlidingTabStrip pagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.activity_main_slidingTabStrip);
        pagerSlidingTabStrip.setViewPager(viewPager);


        fabCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, CountryActivity.class), AppConstants.DATA_CHANGE_RESULT_CODE);
            }
        });

        fabLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, LanguageActivity.class), AppConstants.DATA_CHANGE_RESULT_CODE);
            }
        });

        fabShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFavClicked();
            }
        });

        fabSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });
    }

    public void showNews(News news, boolean isStarred) {
        if (networkStatusHelper.isNetworkAvailable()) {
            Intent i = new Intent(MainActivity.this, DetailedNewsActivity.class);
            if (isStarred) {
                News s = new News(news.getmAuthor(), news.getmTitle(), news.getmDescription(), news.getmUrl(), news.getmPublishedAt());
                i.putExtra("newsObject", s);
            } else {
                i.putExtra("newsObject", news);
            }
            startActivity(i);
        } else {
            Toast.makeText(this, "No active internet.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Snackbar.make(coordinatorLayout, "Restart the app for the changes to takes place.", Snackbar.LENGTH_LONG).show();
    }



    public void onFavClicked() {
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Airnews");
            String sAux = "Discover Airnews for android.\n\n";
            sAux = sAux + "https://play.google.com/store/apps/details?id=com.air.aircovg&hl=en\n\n";
            sAux = sAux + "Source code at Github\n\n";
            sAux = sAux + "https://www.github.com/mcd-50/airnews";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "Choose to share"));
        } catch (Exception e) {
            //e.toString();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}
