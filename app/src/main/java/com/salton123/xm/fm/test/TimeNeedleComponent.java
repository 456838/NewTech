package com.salton123.xm.fm.test;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.salton123.base.BaseSupportFragment;
import com.salton123.xm.R;

/**
 * User: newSalton@outlook.com
 * Date: 2017/9/26 20:26
 * ModifyTime: 20:26
 * Description:
 */
public class TimeNeedleComponent extends BaseSupportFragment {

    View baseline_top, baseline_bottom;
    int[] top = new int[2];
    int[] bottom = new int[2];
    ImageView iv_icon;

    @Override
    public int GetLayout() {
        return R.layout.test;
    }

    @Override
    public void InitVariable(Bundle savedInstanceState) {

    }

    @Override
    public void InitViewAndData() {
        baseline_top = f(R.id.baseline_top);
        baseline_bottom = f(R.id.baseline_bottom);
        iv_icon = f(R.id.iv_icon);
        baseline_top.post(new Runnable() {
            @Override
            public void run() {
                baseline_top.getLocationInWindow(top);


            }
        });
        baseline_top.post(new Runnable() {
            @Override
            public void run() {
                baseline_bottom.getLocationInWindow(bottom);

            }
        });
    }

    boolean isTop = true;

    @Override
    public void InitListener() {
        iv_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTop) {
                    iv_icon.setTranslationY(bottom[1]);
//                    iv_icon.setTranslationY(500);
//                    ViewAnimator.animate(iv_icon).translationY(500).start();
                }else{
//                    ViewAnimator.animate(iv_icon).translationY(100).start();
//                    iv_icon.setTranslationY(100);
                    iv_icon.setTranslationY(top[1]);
                }
                isTop = !isTop;
            }
        });
    }
}
