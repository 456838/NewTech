package com.salton123.xm.fm;

import android.os.Bundle;
import android.util.Log;

import com.salton123.base.BaseSupportFragment;
import com.salton123.util.LogUtils;
import com.salton123.xm.R;

/**
 * User: newSalton@outlook.com
 * Date: 2017/8/10 21:28
 * ModifyTime: 21:28
 * Description:
 */
public class CategoryFragment extends BaseSupportFragment {
    @Override
    public int GetLayout() {
        return R.layout.fm_category;
    }

    @Override
    public void InitVariable(Bundle savedInstanceState) {
        LogUtils.e("bb","hello");
        Log.e("aa","hello");
    }

    @Override
    public void InitViewAndData() {

    }

    @Override
    public void InitListener() {

    }
}
