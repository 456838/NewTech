package com.salton123.xm.fm;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.viewanimator.ViewAnimator;
import com.salton123.base.BaseSupportFragment;
import com.salton123.xm.R;

/**
 * User: newSalton@outlook.com
 * Date: 2017/9/8 18:05
 * ModifyTime: 18:05
 * Description:
 */
public class AnimationComponent extends BaseSupportFragment{
    private ImageView iv_win ;
    private FloatingActionButton fb_move ;
    private TextView tv_hint;
    @Override
    public int GetLayout() {
        return R.layout.component_animation;
    }

    @Override
    public void InitVariable(Bundle savedInstanceState) {

    }

    @Override
    public void InitViewAndData() {
        iv_win = f(R.id.iv_win);
        fb_move = f(R.id.fb_move);
        tv_hint = f(R.id.tv_hint);
    }

    @Override
    public void InitListener() {
        fb_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_win.setVisibility(View.VISIBLE);
                scaleAnimation();
            }
        });
    }

    private void scaleAnimation(){
//        ViewAnimator.animate(iv_win).translationY(-1000, 0).alpha(0,1).andAnimate(tv_hint)
//                .dp().translationX(-20, 0)
//                .decelerate()
//                .duration(2000)
//                .thenAnimate(tv_hint)
//                .scale(1f, 0.5f, 1f)
//                .accelerate()
//                .duration(1000)
//                .start();

        ViewAnimator.animate(iv_win).scale(5,1).duration(5000).start();
    }
}
