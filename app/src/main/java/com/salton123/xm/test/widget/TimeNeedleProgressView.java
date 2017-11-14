package com.salton123.xm.test.widget;

import android.content.Context;
import android.graphics.PointF;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.github.florent37.viewanimator.ViewAnimator;
import com.salton123.util.log.MLog;
import com.salton123.xm.R;

/**
 * User: newSalton@outlook.com
 * Date: 2017/9/26 20:46
 * ModifyTime: 20:46
 * Description:
 */
public class TimeNeedleProgressView extends RelativeLayout {
    public static final String TAG = "TimeNeedleProgressView";
    private View mView;         //ContentView
    private TimeNeedleSeekBar seekbar;
    private ImageView leastPoint;
    private RelativeLayout breakPointLayout;
    private int elapseProgress; //已经消逝的进度,用SecondProgress来承载

    public int getElapseProgress() {
        return elapseProgress;
    }

    public void setElapseProgress(int elapseProgress) {
        this.elapseProgress = elapseProgress;
        if (seekbar != null && elapseProgress < seekbar.getMax()) { //因为seekbar!=null在先，后面的seekbar.getMax才有机会执行
            setSecondaryProgress(elapseProgress);
        }
    }

    public TimeNeedleProgressView(Context context) {
        super(context);
        init();
    }

    public TimeNeedleProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TimeNeedleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TimeNeedleProgressView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private void init() {
        mView = LayoutInflater.from(getContext()).inflate(R.layout.view_time_needle, this, false);
        seekbar = f(R.id.seekbar);
        leastPoint = f(R.id.least_point);
        breakPointLayout = f(R.id.point_layout);
        addView(mView);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                trackLogic();
                // moveTo(progress);
                autoMove(seekBar.getSecondaryProgress(), seekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                trackLogic();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                trackLogic();
            }
        });
    }

    private void moveTo(int progress) {
        PointF pointF = seekbar.currentPoint(progress);

        leastPoint.setTranslationX(pointF.x);
    }

    private void autoMove(int fromProgress, int toProgress) {
        PointF fromPoint = seekbar.currentPoint(fromProgress);
        PointF toPoint = seekbar.currentPoint(toProgress);
        ViewAnimator.animate(leastPoint).translationX(fromPoint.x, toPoint.x).start();
    }

    /**
     * 踪迹逻辑，如果当前的进度小于已经录制的进度，则转到已经录制的进度
     */
    private void trackLogic() {
        if (getProgress() < getSecondaryProgress()) {
            seekbar.setProgress(getSecondaryProgress());
        }
    }

    /**
     * @return 获取当前进度
     */
    public int getProgress() {
        if (seekbar != null) {
            MLog.info(TAG, "point 1 = " + seekbar.progressX(seekbar.getProgress()) + ",point 2 =" + seekbar.progressX(seekbar.getSecondaryProgress()));
            return seekbar.getProgress();
        } else {
            MLog.error(TAG, "[getProgress] seekbar == null");
            return 0;
        }
    }


    public int getSecondaryProgress() {
        if (seekbar != null) {
            return seekbar.getSecondaryProgress();
        } else {
            MLog.error(TAG, "[getSecondProgress] seekbar == null");
            return 0;
        }
    }

    public void setSecondaryProgress(int secondaryProgress) {
        seekbar.setSecondaryProgress(secondaryProgress);
        // leastPoint.setX(seekbar.progressX(secondaryProgress));
        leastPoint.setTranslationX(seekbar.progressX(secondaryProgress));
    }


    protected <VT extends View> VT f(@IdRes int id) {
        return (VT) mView.findViewById(id);
    }
}
