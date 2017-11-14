package com.salton123.xm.fm.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import com.salton123.xm.R;

/**
 * User: newSalton@outlook.com
 * Date: 2017/8/28 20:45
 * ModifyTime: 20:45
 * Description:
 */
public class StrokeText extends TextView {
    private int strokeColor = Color.RED;
    private int strokeWidth = 4;

    public StrokeText(Context context) {
        super(context);
    }

    public StrokeText(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StrokeText);
        strokeColor = a.getColor(R.styleable.StrokeText_textStrokeColor, strokeColor);
        strokeWidth = a.getDimensionPixelSize(R.styleable.StrokeText_textStrokeWidth, strokeWidth);
        a.recycle();
    }

    public void setStrokeColor(int color) {
        strokeColor = color;
    }

    public void setStrokeWidth(int wi) {
        strokeWidth = wi;
//        setPadding(wi, wi, wi, wi);
        setGravity(Gravity.CENTER);
        postInvalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth()+strokeWidth*2 , getMeasuredHeight()+strokeWidth*2);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right+strokeWidth*2, bottom+strokeWidth*2);
    }

    TextPaint paint ;
    @Override
    public void onDraw(Canvas canvas) {
        setGravity(Gravity.CENTER);
        final ColorStateList textColor = getTextColors();
        paint = this.getPaint();
        paint.setFakeBoldText(true); //设置粗体
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
//        paint.setStrokeMiter(10);
        this.setTextColor(strokeColor);
        paint.setStrokeWidth(strokeWidth);
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.FILL);
        setTextColor(textColor);
        setStrokeWidth(strokeWidth);
        super.onDraw(canvas);
    }
}
