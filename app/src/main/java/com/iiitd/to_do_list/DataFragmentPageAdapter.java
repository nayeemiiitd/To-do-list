package com.iiitd.to_do_list;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Nayeem on 11/4/2016.
 */

public  class DataFragmentPageAdapter extends FragmentPagerAdapter {

    ArrayList<Detail> users;

    public DataFragmentPageAdapter(FragmentManager fm, ArrayList<Detail> usersList) {
        super(fm);
        this.users=usersList;
    }

    @Override
    public Fragment getItem(int index) {

        return PageFragment.newInstance(users.get(index));
    }

    @Override
    public int getCount() {

        return users.size();
    }
}
