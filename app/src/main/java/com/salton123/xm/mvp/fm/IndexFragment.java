package com.salton123.xm.mvp.fm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.salton123.base.BaseSupportFragment;
import com.salton123.mvp.ui.BaseSupportPresenterFragment;
import com.salton123.xm.R;
import com.salton123.xm.mvp.business.index.IndexFragmentContract;
import com.salton123.xm.mvp.business.index.IndexFragmentPresenter;
import com.salton123.xm.mvp.view.adapter.IndexFragmentAdapter;
import com.salton123.xm.util.TabLayoutUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by salton on 2017/8/13.
 */

public class IndexFragment extends BaseSupportPresenterFragment<IndexFragmentPresenter> implements IndexFragmentContract.View {

    ViewPager vp_hot_music;
    TabLayout tab_hot_music;
    IndexFragmentAdapter adapter;

    @Override
    public int GetLayout() {
        return R.layout.fm_index;
    }

    @Override
    public void InitVariable(Bundle savedInstanceState) {
        mPresenter =new IndexFragmentPresenter();
    }

    @Override
    public void InitViewAndData() {
        vp_hot_music = f(R.id.vp_index);
        tab_hot_music = f(R.id.tab_hot_music);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.getData();
    }


    @Override
    public void InitListener() {

    }

    @Override
    public void setData(List<String> areaList) {
        List<Fragment> fragments = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        if (areaList != null) {
            //除了固定的其他频道被选中，添加
            for (String area : areaList) {
                final IndexItemFragment fragment= BaseSupportFragment.newInstance(IndexItemFragment.class,area);
                fragments.add(fragment);
                titles.add(area);
            }
        }

        if (vp_hot_music.getAdapter() == null) {
            //初始化ViewPager
            adapter = new IndexFragmentAdapter(getFragmentManager(),fragments,titles);
            vp_hot_music.setAdapter(adapter);
        } else {
            adapter = (IndexFragmentAdapter) vp_hot_music.getAdapter();
            adapter.updateFragments(fragments, titles);
        }
        vp_hot_music.setCurrentItem(0, false);
        tab_hot_music.setupWithViewPager(vp_hot_music);
        tab_hot_music.setScrollPosition(0, 0, true);
        TabLayoutUtil.dynamicSetTabLayoutMode(tab_hot_music);
    }

    @Override
    public void setError(String msg) {
        toast(msg);
        mPresenter.getData();
    }
}
