package com.salton123.xm.share.strategy;

import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;
import android.widget.Toast;

import com.salton123.base.ViewHolder;
import com.salton123.xm.R;
import com.salton123.xm.share.bean.NormalShareTypeItem;

/**
 * User: newSalton@outlook.com
 * Date: 2017/8/3 10:34
 * ModifyTime: 10:34
 * Description:
 */
public class NormalShareStrategy implements IBaseShareStrategy<NormalShareStrategy, NormalShareTypeItem> {
    private final static String TAG = "NormalShareStrategy";
    NormalShareTypeItem mShareTypeBean;
    View mView;
    TextView tv_content;

    @Override
    public NormalShareStrategy setData(NormalShareTypeItem bean) {
        mShareTypeBean = bean;
        if (mShareTypeBean != null) {
            tv_content.setText(bean.toString());
        }
        return this;
    }

    @Override
    public NormalShareStrategy initView(ViewStub viewStub) {
        viewStub.setLayoutResource(R.layout.container_normal_share_type);
        mView = viewStub.inflate();
        tv_content = ViewHolder.get(mView, R.id.tv_content);
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
