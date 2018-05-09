package com.example.julia.delivery.mainscreen;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.julia.delivery.MainActivity;

class PagerAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;
    private MainActivity activity;

    PagerAdapter(FragmentManager fm, int NumOfTabs, MainActivity activity) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.activity = activity;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return OrderTabFragment.createNewInstance(activity);
            case 1:
                return new MapTabFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
