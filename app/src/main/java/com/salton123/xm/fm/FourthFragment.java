package com.salton123.xm.fm;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.salton123.base.BaseSupportFragment;
import com.salton123.xm.R;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/6/15 11:35
 * Time: 11:35
 * Description:
 */
public class FourthFragment extends BaseSupportFragment {
    ProgressDialog mProgressDialog;
    private String TAG = "FourthFragment";
    @Override
    public int GetLayout() {
        return R.layout.fm_test;
    }

    private String demoPluginName = "plugindemo01-debug.apk";

    @Override
    public void InitVariable(Bundle savedInstanceState) {



    }


    @Override
    public void InitViewAndData() {
        mProgressDialog = new ProgressDialog(_mActivity);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setMessage("插件加载中...");


    }

    @Override
    public void InitListener() {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

    }
}
