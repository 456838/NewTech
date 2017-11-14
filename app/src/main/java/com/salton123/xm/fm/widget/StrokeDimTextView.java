package com.salton123.xm.fm.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;

import com.salton123.xm.fm.FontUtils;

public class StrokeDimTextView extends TextView {
    private TextView storekeText = null;        // 用于描边的TextView

    public StrokeDimTextView(Context context) {
        super(context);
        storekeText = new TextView(context);
        init();
    }

    public StrokeDimTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        storekeText = new TextView(context, attrs);
        init();
    }


    public StrokeDimTextView(Context context, AttributeSet attrs,
                          int defStyle) {
        super(context, attrs, defStyle);
        storekeText = new TextView(context, attrs, defStyle);
        init();
    }

    public void init() {
        TextPaint tp1 = storekeText.getPaint();
        tp1.setStrokeWidth(20);                          // 设置描边宽度
        tp1.setStyle(Style.STROKE);                     // 对文字只描边
        storekeText.setTextColor(Color.RED);            // 设置描边颜色
        storekeText.setGravity(getGravity());
        storekeText.setTypeface(FontUtils.getTypeFace(getContext(), FontUtils.FontType.DIN_MITTELSCHRIFT_ALTERNATE));
    }

    @Override
    public void setTypeface(Typeface tf) {
        super.setTypeface(tf);
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        super.setLayoutParams(params);
        storekeText.setLayoutParams(params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        CharSequence tt = storekeText.getText();

        // 两个TextView上的内容必须一致
        if (tt == null || !tt.equals(this.getText())) {
            storekeText.setText(getText());
            this.postInvalidate();
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth()+20 , getMeasuredHeight()+20);
        storekeText.measure(widthMeasureSpec, heightMeasureSpec);
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        super.onLayout(changed, left, top, right+10, bottom+10);
        storekeText.layout(left, top, right, bottom);
//                super.onLayout(changed, left+50, top+50, right+50, bottom+50);
//        storekeText.layout(left+10, top+4, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        storekeText.draw(canvas);
        super.onDraw(canvas);
    }
}