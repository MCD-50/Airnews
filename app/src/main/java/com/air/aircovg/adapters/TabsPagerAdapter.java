package com.air.aircovg.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;

import com.air.aircovg.fragments.EntertainmentNewsFragment;
import com.air.aircovg.fragments.LifestyleNewsFragment;
import com.air.aircovg.fragments.PoliticsNewsFragment;
import com.air.aircovg.fragments.SportsNewsFragment;
import com.air.aircovg.fragments.TopNewsFragment;
import com.air.aircovg.fragments.PinnedNewsFragment;

import java.util.ArrayList;

/**
 * Created by ayush AS on 25/12/16.
 */

public class TabsPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> registeredFragments = new ArrayList<Fragment>(){{
        add(new TopNewsFragment());
        add(new SportsNewsFragment());
        add(new PoliticsNewsFragment());
        add(new EntertainmentNewsFragment());
        add(new LifestyleNewsFragment());
        add(new PinnedNewsFragment());
    }};
    private String[] tabs = { "TOP", "SPORTS", "POLITICS", "ENTERTAINMENT", "LIFESTYLE", "PINNED" };
    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {
        switch (index) {
            case 0:
                return new TopNewsFragment();
            case 1:
                return new SportsNewsFragment();
            case 2:
                return new PoliticsNewsFragment();
            case 3:
                return new EntertainmentNewsFragment();
            case 4:
                return new LifestyleNewsFragment();
            case 5:
                return new PinnedNewsFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }

    public ArrayList<Fragment> getRegisteredFragment() {
        return registeredFragments;
    }

}
