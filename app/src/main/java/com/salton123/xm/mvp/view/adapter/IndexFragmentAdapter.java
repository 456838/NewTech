package com.salton123.xm.mvp.view.adapter;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

/**
 * Created by salton on 2017/8/13.
 */

public class IndexFragmentAdapter extends FragmentPagerAdapter {

    private FragmentManager mFragmentManager;
    private List<Fragment> mFragments;
    private List<String> mTitles;

    public IndexFragmentAdapter(FragmentManager fm) {
        super(fm);
    }


    /**
     * 更新频道，前面固定的不更新，后面的一律更新
     *
     * @param fragments
     * @param titles
     */
    public void updateFragments(List<Fragment> fragments, List<String> titles) {
        for (int i = 0; i < fragments.size(); i++) {
            final Fragment fragment = mFragments.get(i);
            final FragmentTransaction ft = mFragmentManager.beginTransaction();
            if (i > 2) {
                ft.remove(fragment);
                mFragments.remove(i);
            }
            ft.commit();
        }
        for (int i = 0; i < fragments.size(); i++) {
            if (i > 2) {
                mFragments.add(fragments.get(i));
            }
        }
        this.mTitles = titles;
        notifyDataSetChanged();
    }

    public IndexFragmentAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        super(fm);
        mFragmentManager = fm;
        mFragments = fragments;
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

}