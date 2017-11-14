package com.salton123.xm.share.strategy;

import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;
import android.widget.Toast;

import com.salton123.base.ViewHolder;
import com.salton123.xm.R;
import com.salton123.xm.share.bean.StarShareTypeItem;

/**
 * User: newSalton@outlook.com
 * Date: 2017/8/3 11:27
 * ModifyTime: 11:27
 * Description:
 */
public class StarShareStrategy implements IBaseShareStrategy<StarShareStrategy,StarShareTypeItem> {

    StarShareTypeItem starShareTypeBean;

    View mView;
    TextView tv_content;

    @Override
    public StarShareStrategy setData(StarShareTypeItem bean) {
        starShareTypeBean = bean;
        return this;
    }

    @Override
    public StarShareStrategy initView(ViewStub viewStub) {
        viewStub.setLayoutResource(R.layout.container_star_share_type);
        mView = viewStub.inflate();
        tv_content = ViewHolder.get(mView, R.id.tv_name);
        initListener();
        return this;
    }

    private void initListener() {
        tv_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "hello", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
