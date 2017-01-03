package com.air.aircovg;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.astuetz.PagerSlidingTabStrip;
import com.air.aircovg.adapters.TabsPagerAdapter;
import com.air.aircovg.model.News;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    TabsPagerAdapter tabsPagerAdapter;
    ViewPager viewPager;
    NavigationView navigationView;
    FloatingActionButton fab;
    CoordinatorLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "Airverge");
                    String sAux = "Discover Airverge for android.\n\n";
                    sAux = sAux + "https://play.google.com/store/apps/details?id=com.air.aircovg&hl=en\n\n";
                    sAux = sAux + "Source code at Github\n\n";
                    sAux = sAux + "https://www.github.com/mcd-50";
                    i.putExtra(Intent.EXTRA_TEXT, sAux);
                    startActivity(Intent.createChooser(i, "Choose to share"));
                } catch(Exception e) {
                    //e.toString();
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        viewPager = (ViewPager) findViewById(R.id.activity_main_pager);
        tabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabsPagerAdapter);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.cordinateLayout);

        PagerSlidingTabStrip pagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.activity_main_slidingTabStrip);
        pagerSlidingTabStrip.setViewPager(viewPager);

    }

    public void showNews(News news, boolean isStarred) {
        Intent i = new Intent(MainActivity.this, DetailedNewsActivity.class);
        if(isStarred){
            News s = new News(news.getmAuthor(), news.getmTitle(), news.getmDescription(), news.getmUrl(), news.getmPublishedAt(), news.getmSource());
            s.setmImageUrl(news.getmImageUrl());
            i.putExtra("newsObject", s);
        }else {
            i.putExtra("newsObject", news);
        }

        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigationView.setCheckedItem(R.id.nav_home);
    }

    public void showSnackbar(){
        Snackbar.make(coordinatorLayout, "You are not connected to internet. Please check your connection and try again in a little bit.", Snackbar.LENGTH_LONG).show();
    }

    public void toggleVisible(boolean isVisible) {
//        if(isVisible)
//            fab.setVisibility(View.VISIBLE);
//        else
//            fab.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_categories)
            startActivity(new Intent(MainActivity.this, CategoriesActivity.class));
        else if (id == R.id.nav_settings)
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));

//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}