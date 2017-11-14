package com.salton123.xm.share.strategy;

import android.view.ViewStub;

import com.salton123.xm.share.bean.BaseShareItem;

/**
 * User: newSalton@outlook.com
 * Date: 2017/8/3 10:32
 * ModifyTime: 10:32
 * Description:根据策略加载不同的view
 */
public interface IBaseShareStrategy<R, T extends BaseShareItem> {
    R setData(T bean);

    R initView(ViewStub viewStub);
}
