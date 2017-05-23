package com.air.aircovg.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.air.aircovg.fragments.AllNewsFragment;
import com.air.aircovg.fragments.StarredFragment;

/**
 * Created by ayush AS on 25/12/16.
 */

public class TabsPagerAdapter extends FragmentPagerAdapter {

    private String[] tabs = { "ALL NEWS", "PINNED" };
    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                return new AllNewsFragment();
            case 1:
                return new StarredFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }

}
