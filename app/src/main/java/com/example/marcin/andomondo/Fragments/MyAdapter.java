package com.example.marcin.andomondo.Fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


/**
 * Created by Marcin on 6/14/2017.
 */

public class MyAdapter extends FragmentPagerAdapter {
    public MyAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int position) {

        if(position==0)
            return MyGraphFragment.newInstance();
        else if(position==1)
            return MyMapFragment.newInstance();
        else
            return MyFacebookFragment.newInstance();
    }
}