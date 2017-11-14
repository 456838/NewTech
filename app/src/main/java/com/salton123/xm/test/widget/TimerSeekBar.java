package com.salton123.xm.test.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.SeekBar;

/**
 * User: newSalton@outlook.com
 * Date: 2017/10/5 18:25
 * ModifyTime: 18:25
 * Description:
 */
public class TimerSeekBar extends SeekBar {
    private Drawable mCurrentThumb;

    public TimerSeekBar(Context context) {
        super(context);
    }

    public TimerSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TimerSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setThumb(Drawable thumb) {
        super.setThumb(thumb);
        mCurrentThumb = thumb;
    }

    public int getThumbWidth() {
        if (mCurrentThumb != null) {
            return mCurrentThumb.getBounds().width();
        }
        return 0;
    }

}
