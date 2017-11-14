package com.salton123.xm.fm.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;

import com.salton123.xm.R;

/**
 * User: newSalton@outlook.com
 * Date: 2017/8/28 21:56
 * ModifyTime: 21:56
 * Description:
 */
public class StrokeText2 extends android.support.v7.widget.AppCompatTextView {

    private boolean isStroke = false;
    private float strokeWidth = 0.0f;
    private int strokeColor;

    public StrokeText2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        initView(context, attrs);
    }

    public StrokeText2(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView(context, attrs);
    }

    public StrokeText2(Context context) {
        super(context);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StrokeTextView);
        isStroke = a.getBoolean(R.styleable.StrokeTextView_textStroke, false);
        strokeWidth = a.getFloat(R.styleable.StrokeTextView_textStrokeWidth2, 0.0f);
        strokeColor = a.getColor(R.styleable.StrokeTextView_textStrokeColor2, 0xffffffff);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (isStroke) {
            ColorStateList states = getTextColors();
            getPaint().setStyle(Paint.Style.FILL_AND_STROKE);
            getPaint().setStrokeWidth(strokeWidth);
            setGravity(Gravity.CENTER);
            setTextColor(strokeColor);
            super.onDraw(canvas);
            getPaint().setStyle(Paint.Style.STROKE);
            setTextColor(states);
        }

        super.onDraw(canvas);
    }

    public void setStroke(boolean stroke) {
        isStroke = stroke;
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
    }
}