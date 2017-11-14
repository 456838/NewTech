package com.salton123.xm.fm;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.salton123.base.BaseSupportFragment;
import com.salton123.xm.R;
import com.salton123.xm.fm.widget.MenuViewItem;

/**
 * User: newSalton@outlook.com
 * Date: 2017/9/9 13:14
 * ModifyTime: 13:14
 * Description:
 */
public class TouchComponent extends BaseSupportFragment implements View.OnClickListener {
    MenuViewItem home_facade_mv ,home_pickup_mv ;
    LinearLayout rootLayout;
    @Override
    public int GetLayout() {
        return R.layout.component_touch;
    }

    @Override
    public void InitVariable(Bundle bundle) {

    }

    @Override
    public void InitViewAndData() {
        home_facade_mv  = f(R.id.home_facade_mv);
        home_pickup_mv  = f(R.id.home_pickup_mv);
        rootLayout = f(R.id.rootLayout);

    }

    @Override
    public void InitListener() {
        home_facade_mv.setOnClickListener(this);
        home_pickup_mv.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_facade_mv:
                toast("left clicked");
                rootLayout.setBackgroundResource(R.mipmap.ballgame);
                break;
            case R.id.home_pickup_mv:
                rootLayout.setBackgroundResource(R.mipmap.ballgame2);
                toast("right clicked");
                break;
        }
    }
}
