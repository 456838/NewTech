package com.salton123.xm.mvp.fm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.salton123.base.BaseSupportFragment;
import com.salton123.xm.R;
import com.salton123.xm.mvp.model.ApiMethod;
import com.salton123.xm.mvp.model.MultiSearchBean;
import com.salton123.xm.mvp.view.adapter.IndexItemAdapter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by salton on 2017/8/13.
 */

public class IndexItemFragment extends BaseSupportFragment{
    private IndexItemAdapter mAdapter ;
    @Override
    public int GetLayout() {
        return R.layout.fm_index_item;
    }
    String keyword ;
    @Override
    public void InitVariable(Bundle savedInstanceState) {
        keyword  = getArguments().getString(ARG_ITEM);

    }

    @Override
    public void InitViewAndData() {

    }

    @Override
    public void InitListener() {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        ApiMethod.multiSearch(keyword).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<MultiSearchBean>() {
            @Override
            public void accept(MultiSearchBean multiSearchBean) throws Exception {
                String retSrt = new GsonBuilder().setPrettyPrinting().create().toJson(multiSearchBean,MultiSearchBean.class);
                Log.e("aa",retSrt);
            }
        });
    }
}
