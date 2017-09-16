package com.salton123.xm.mvp.fm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.salton123.base.BaseSupportFragment;
import com.salton123.xm.R;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/6/15 11:35
 * Time: 11:35
 * Description:
 */

public class SecondFragment extends BaseSupportFragment {

    @Override
    public int GetLayout() {
        return R.layout.fm_container;
    }

    @Override
    public void InitVariable(Bundle savedInstanceState) {
            
    }

    @Override
    public void InitViewAndData() {
        Toast.makeText(_mActivity,"Second",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void InitListener() {
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.fl_container,BaseSupportFragment.newInstance(ApiTestFragment.class));
        }
    }
}
