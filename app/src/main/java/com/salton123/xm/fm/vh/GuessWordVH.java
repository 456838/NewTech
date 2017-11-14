package com.salton123.xm.fm.vh;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.salton123.xm.R;

/**
 * User: newSalton@outlook.com
 * Date: 2017/7/28 11:38
 * ModifyTime: 11:38
 * Description:
 */
public class GuessWordVH extends BaseRvViewHolder{
    public TextView tv_name ;
    public ImageView iv_icon ;
    public TextView tv_remind_count ;
    public GuessWordVH(View itemView) {
        super(itemView);
        tv_name = f(itemView, R.id.tv_name);
        iv_icon = f(itemView, R.id.iv_icon);
        tv_remind_count = f(itemView, R.id.tv_remind_count);
    }
}
