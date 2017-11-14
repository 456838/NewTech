package com.salton123.xm.test.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.SeekBar;

import com.salton123.util.log.MLog;

/**
 * User: newSalton@outlook.com
 * Date: 2017/9/27 14:42
 * ModifyTime: 14:42
 * Description:
 */
public class TimeNeedleSeekBar extends SeekBar {
    private String numTextFormat = "秒";
    private String numText;        //进度内容
    private Paint mPaint;

    private Drawable mIndeterminateDrawable;

    public TimeNeedleSeekBar(Context context) {
        super(context);
        init();
    }

    public TimeNeedleSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TimeNeedleSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    Rect progressBound;
    Paint.FontMetrics mFontMetrics;

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mFontMetrics = mPaint.getFontMetrics();
    }


    // @Override
    // protected void onDraw(Canvas canvas) {
    //     super.onDraw(canvas);
    // try {
    //     progressBound = this.getProgressDrawable().getBounds();
    //     float startX = progressBound.width() * getProgress() / getMax();
    //     float startY = progressBound.height();
    //     canvas.drawLine(startX + progressBound.top, startY - 50, startX + progressBound.top, startY + progressBound.height() + 50, mPaint);
    // } catch (Exception e) {
    //     e.printStackTrace();
    // }

    // }

    public PointF currentPoint(float progress) {
        int height = getMeasuredHeight();
        PointF pointF = new PointF(progressX(progress), height);
        return pointF;
    }

    public float progressX(float progress) {
        MLog.info("TAG", "[progressX] progress= " + progress);
        int width = getMeasuredWidth();
        float currentX = width * (progress / getMax());
        return currentX;
    }
}
