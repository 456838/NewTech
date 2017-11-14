package com.salton123.xm.test;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.salton123.base.BaseSupportFragment;
import com.salton123.util.ScreenUtils;
import com.salton123.xm.R;

/**
 * User: newSalton@outlook.com
 * Date: 2017/8/23 11:22
 * ModifyTime: 11:22
 * Description:
 */
public class LayoutTestComponent extends BaseSupportFragment {
    private TextView tv_replay;
    @Override
    public int GetLayout() {
        return R.layout.layout_test;
    }

    @Override
    public void InitVariable(Bundle savedInstanceState) {

    }
    ImageView iv_icon;
    @Override
    public void InitViewAndData() {
        scaleBigToSmallAnimation = new ScaleAnimation(5f, 1f, 5f, 1f,
                Animation.ZORDER_TOP, 1f, Animation.ZORDER_TOP,1f);
        tv_replay = f(R.id.tv_replay);
        iv_icon = f(R.id.iv_icon);
        float px = ScreenUtils.dpToPx(_mActivity, 16);
        System.out.println("InitViewAndData:" + px);
        DisplayMetrics metric = new DisplayMetrics();
        _mActivity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;  // 屏幕宽度（像素）
        int height = metric.heightPixels;  // 屏幕高度（像素）
        float density = metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
        System.out.println("width="+width+",height="+height+",density="+density+",densityDpi="+densityDpi+",px="+px);
        tv_replay.setText(ScreenUtils.pxToDp(_mActivity,tv_replay.getTextSize())+""+"width="+width+",height="+height+",density="+density+",densityDpi="+densityDpi+",px="+px);
//        width=1080,height=1920,density=2.625,densityDpi=420,px=42.0  samsung9100
//        width=1080,height=1920,density=3.0,densityDpi=480,px=48.0 xiaomi5s
//        width=1080,height=1920,density=3.0,densityDpi=480,px=48.0 vivo x9
//        width=1080,height=1794,density=3.0,densityDpi=480,px=48.0 huawei p9

        loadScaleAnimation(iv_icon,true);
        iv_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadScaleAnimation(iv_icon,true);
            }
        });
    }

    @Override
    public void InitListener() {

    }

    Animation scaleBigToSmallAnimation;
    public void loadScaleAnimation(final ImageView view, final boolean show) {

                if (show) {
                    view.setVisibility(View.VISIBLE);
                    scaleBigToSmallAnimation.setDuration(1000);
                    scaleBigToSmallAnimation.setFillAfter(true);//动画执行完后是否停留在执行完的状态
                    view.startAnimation(scaleBigToSmallAnimation);
                } else {
                    view.post(new Runnable() {
                        @Override
                        public void run() {
                            if (scaleBigToSmallAnimation != null) {
                                scaleBigToSmallAnimation.cancel();
                            }
                        }
                    });
                }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (scaleBigToSmallAnimation!=null){
            scaleBigToSmallAnimation.cancel();
            scaleBigToSmallAnimation=null ;
        }
    }
}
