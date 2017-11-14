package com.salton123.xm.share;

import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;

import com.salton123.base.BaseSupportFragment;
import com.salton123.xm.R;
import com.salton123.xm.share.bean.NormalShareTypeItem;
import com.salton123.xm.share.bean.StarShareTypeItem;
import com.salton123.xm.share.strategy.IBaseShareStrategy;
import com.salton123.xm.share.strategy.NormalShareStrategy;
import com.salton123.xm.share.strategy.StarShareStrategy;

/**
 * User: newSalton@outlook.com
 * Date: 2017/8/2 21:56
 * ModifyTime: 21:56
 * Description:
 */
public class ShareFragment extends BaseSupportFragment {

    IBaseShareStrategy iBaseShareStrategy;

    @Override
    public int GetLayout() {
        return R.layout.fm_share;
    }

    public IBaseShareStrategy getStrategy() {
        return new StarShareStrategy();
    }

    public IBaseShareStrategy getNormalShareStrategy() {
        return new NormalShareStrategy();
    }

    @Override
    public void InitVariable(Bundle savedInstanceState) {
        iBaseShareStrategy = getStrategy();
    }

    @Override
    public void InitViewAndData() {
        ViewStub vs_share = f(R.id.vs_share);
        iBaseShareStrategy.initView(vs_share);
    }

    @Override
    public void InitListener() {
        f(R.id.tv_switch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iBaseShareStrategy instanceof StarShareStrategy) {
                    iBaseShareStrategy.setData(new StarShareTypeItem());
                } else if (iBaseShareStrategy instanceof StarShareStrategy) {
                    iBaseShareStrategy.setData(new NormalShareTypeItem());
                }
            }
        });
    }
}
