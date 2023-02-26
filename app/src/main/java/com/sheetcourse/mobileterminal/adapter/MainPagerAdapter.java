package com.sheetcourse.mobileterminal.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.sheetcourse.mobileterminal.fragment.FragmentFactory;


/**
 * Created by zs on 2016/5/9.
 */
public class MainPagerAdapter extends FragmentStatePagerAdapter {

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentFactory.createForMain(position);
    }

    @Override
    public int getCount() {
        return 3;
    }

}
