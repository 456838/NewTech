package com.salton123.xm.fm.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.SeekBar;

/**
 * User: newSalton@outlook.com
 * Date: 2017/10/5 17:32
 * ModifyTime: 17:32
 * Description:
 */
public class TimerSeekView extends SeekBar {
    public TimerSeekView(Context context) {
        super(context);
    }

    public TimerSeekView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TimerSeekView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }


    @Override
    public void setThumb(Drawable thumb) {
        super.setThumb(thumb);
    }

    @Override
    public Drawable getThumb() {
        return super.getThumb();
    }

    private void test(){

    }
}
