package com.salton123.xm.mvp.business.index;

import com.salton123.mvp.presenter.RxPresenter;

import java.util.Arrays;

/**
 * Created by salton on 2017/8/13.
 */

public class IndexFragmentPresenter extends RxPresenter<IndexFragmentContract.View> implements IndexFragmentContract.Presenter {

    private String[] indexItems= new String[]{
              "郭德纲","岳云鹏","段子来了","高晓松","吴晓波"
    };
    @Override
    public void getData() {
        mView.setData(Arrays.asList(indexItems));
    }
}