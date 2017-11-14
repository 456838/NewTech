package com.salton123.xm.fm.vh;

import android.view.View;
import android.widget.LinearLayout;

import com.salton123.xm.R;

/**
 * User: newSalton@outlook.com
 * Date: 2017/7/28 11:38
 * ModifyTime: 11:38
 * Description:
 */
public class EmptyVH extends BaseRvViewHolder {
    private LinearLayout linearLayout;

    public EmptyVH(View itemView) {
        super(itemView);
        linearLayout = f(itemView, R.id.ll_empty);
    }
}
