package com.salton123.xm.fm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Pair;

import com.salton123.mvp.ui.BaseSupportPresenterFragment;
import com.salton123.xm.R;
import com.salton123.xm.business.OneToNContract;
import com.salton123.xm.business.OneToNPresenter;
import com.salton123.xm.view.adapter.IndexFragmentAdapter;
import com.salton123.xm.util.TabLayoutUtil;

import java.util.List;

/**
 * User: newSalton@outlook.com
 * Date: 2017/8/11 16:14
 * ModifyTime: 16:14
 * Description:
 */
public class IndexFragment extends BaseSupportPresenterFragment<OneToNContract.Presenter> implements OneToNContract.IndexFmIView {

    ViewPager vp_hot_music;
    TabLayout tab_hot_music;
    IndexFragmentAdapter adapter;

    @Override
    public int GetLayout() {
        return R.layout.fm_index;
    }

    @Override
    public void InitVariable(Bundle savedInstanceState) {
        mPresenter =new OneToNPresenter();
        adapter = new IndexFragmentAdapter(getFragmentManager());
    }

    @Override
    public void InitViewAndData() {
        vp_hot_music = f(R.id.vp_index);
        tab_hot_music = f(R.id.tab_hot_music);
        vp_hot_music.setAdapter(adapter);
        vp_hot_music.setCurrentItem(0, false);
        tab_hot_music.setupWithViewPager(vp_hot_music);
        tab_hot_music.setScrollPosition(0, 0, true);
        TabLayoutUtil.dynamicSetTabLayoutMode(tab_hot_music);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.getIndexFmData();
    }

    @Override
    public void InitListener() {

    }


    @Override
    public void onIndexFmData(List<Pair<Fragment, String>> data) {
        adapter.setData(data);
    }

    @Override
    public void onIndexFmError(String msg) {
        toast(msg);
        mPresenter.getIndexFmData();
    }
}
