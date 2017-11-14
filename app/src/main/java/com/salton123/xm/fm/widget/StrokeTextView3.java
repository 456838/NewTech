package com.salton123.xm.fm.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;

import com.salton123.xm.R;

/**
 * User: newSalton@outlook.com
 * Date: 2017/8/25 17:53
 * ModifyTime: 17:53
 * Description:
 */
public class StrokeTextView3 extends TextView {
    private TextView outlineTextView = null;

    public StrokeTextView3(Context context) {
        super(context);
        outlineTextView = new TextView(context);
        init(context, null);
    }

    @Override
    public void setTypeface(Typeface tf) {
        super.setTypeface(tf);
        if (outlineTextView!=null)
        outlineTextView.setTypeface(tf);
    }

    public StrokeTextView3(Context context, AttributeSet attrs) {
        super(context, attrs);
        outlineTextView = new TextView(context, attrs);
        init(context, attrs);
    }

    public StrokeTextView3(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        outlineTextView = new TextView(context, attrs, defStyle);
        init(context, attrs);
    }
    private int mStrokeWidth;
    private int mStrokeColor = Color.BLACK;
    public void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StrokeTextView);
            mStrokeColor = a.getColor(R.styleable.StrokeTextView_strokeColor, Color.BLACK);
            mStrokeWidth = a.getDimensionPixelSize(R.styleable.StrokeTextView_strokeWidth, 0);
            TextPaint paint = outlineTextView.getPaint();
            paint.setStrokeWidth(mStrokeWidth);// 描边宽度
            paint.setStyle(Paint.Style.STROKE);
            outlineTextView.setTextColor(Color.parseColor("#350303"));// 描边颜色
            outlineTextView.setGravity(getGravity());
        }
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        super.setLayoutParams(params);
        outlineTextView.setLayoutParams(params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 设置轮廓文字
        CharSequence outlineText = outlineTextView.getText();
        if (outlineText == null || !outlineText.equals(this.getText())) {
            outlineTextView.setText(getText());
            postInvalidate();
        }
        outlineTextView.measure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        outlineTextView.layout(left+mStrokeWidth, top+mStrokeWidth, right+mStrokeWidth, bottom+mStrokeWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        outlineTextView.draw(canvas);
        super.onDraw(canvas);
    }
}