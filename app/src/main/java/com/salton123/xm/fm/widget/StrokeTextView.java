package com.salton123.xm.fm.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

import com.salton123.xm.R;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * User: newSalton@outlook.com
 * Date: 2017/7/31 17:06
 * ModifyTime: 17:06
 * Description:
 */
public class StrokeTextView extends TextView {
    private static final int HORIZENTAL = 0;
    private static final int VERTICAL = 1;

    private int[] mGradientColor;
    private int mStrokeWidth;
    private int mStrokeColor = Color.BLACK;
    private LinearGradient mGradient;
    private boolean gradientChanged;
    private int mTextColor;
    private TextPaint mPaint;
    private int mGradientOrientation;


    public StrokeTextView(Context context) {
        super(context);
        init(context, null);
    }

    public StrokeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }

    public StrokeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mPaint = getPaint();
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StrokeTextView);
            mStrokeColor = a.getColor(R.styleable.StrokeTextView_strokeColor, Color.BLACK);
            mStrokeWidth = a.getDimensionPixelSize(R.styleable.StrokeTextView_strokeWidth, 0);
            mGradientOrientation = a.getInt(R.styleable.StrokeTextView_gradientOrientation, HORIZENTAL);
            setStrokeColor(mStrokeColor);

            setStrokeWidth(mStrokeWidth);
            setGradientOrientation(mGradientOrientation);
            a.recycle();
        }
    }

    public void setGradientOrientation(int orientation) {
        if (mGradientOrientation != orientation) {
            mGradientOrientation = orientation;
            gradientChanged = true;
            invalidate();
        }
    }

    public void setGradientColor(int[] gradientColor) {
        if (!Arrays.equals(gradientColor, mGradientColor)) {
            mGradientColor = gradientColor;
            gradientChanged = true;
            invalidate();
        }
    }

    public void setStrokeColor(int color) {
        if (mStrokeColor != color) {
            mStrokeColor = color;
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mStrokeWidth > 0) {
            /**
             * 绘制描边
             */
            mTextColor = getCurrentTextColor(); //保存文本的颜色
            mPaint.setStrokeWidth(mStrokeWidth); //设置描边宽度
            mPaint.setFakeBoldText(true); //设置粗体
            mPaint.setShadowLayer(mStrokeWidth, 0, 0, 0);
            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            setColor(mStrokeColor); // 设置描边颜色
            mPaint.setShader(null);  //清空shader
            super.onDraw(canvas);
            /**
             * 绘制文本
             */
            if (gradientChanged) { //如果有渐变颜色，则设置LinearGradient
                if (mGradientColor != null) {
                    mGradient = getGradient();
                }
                gradientChanged = false;
            }
            if (mGradient != null) { //设置渐变Shader
                mPaint.setShader(mGradient);
                mPaint.setColor(Color.WHITE);
            } else {
                setColor(mTextColor);
            }

            mPaint.setStrokeWidth(0);
            mPaint.setFakeBoldText(false);
            mPaint.setShadowLayer(0, 0, 0, 0);
            setPadding(mStrokeWidth,mStrokeWidth,mStrokeWidth,mStrokeWidth);
            super.onDraw(canvas);
        } else {
            setPadding(mStrokeWidth,mStrokeWidth,mStrokeWidth,mStrokeWidth);
            super.onDraw(canvas);

        }
    }

    public void setStrokeWidth(int width) {
        mStrokeWidth = width;
        invalidate();
    }

    private LinearGradient getGradient() {
        LinearGradient gradient;
        if (mGradientOrientation == HORIZENTAL) {
            gradient = new LinearGradient(0, 0, getWidth(), 0, mGradientColor, null, Shader.TileMode.CLAMP);
        } else {
            gradient = new LinearGradient(0, 0, 0, getHeight(), mGradientColor, null, Shader.TileMode.CLAMP);
        }
        return gradient;
    }

    private void setColor(int color) {
        Field textColorField;
        try {
            textColorField = TextView.class.getDeclaredField("mCurTextColor");
            textColorField.setAccessible(true);
            textColorField.set(this, color);
            textColorField.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mPaint.setColor(color);
    }

//    @Override
//    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        super.onLayout(true, left, top, right, bottom);
//    }

//    @Override
//    public void setPaddingRelative(int start, int top, int end, int bottom) {
//        super.setPaddingRelative(start+mStrokeWidth, top+mStrokeWidth, end+mStrokeWidth, bottom+mStrokeWidth);
//    }
//
//    @Override
//    public void setPadding(int left, int top, int right, int bottom) {
//        super.setPadding(left+mStrokeWidth, top+mStrokeWidth, right+mStrokeWidth, bottom+mStrokeWidth);
//    }


//    @Override
//    public void setPadding(int left, int top, int right, int bottom) {
//        super.setPadding(left+mStrokeWidth, top+mStrokeWidth, right+mStrokeWidth, bottom+mStrokeWidth);
//        invalidate();
//    }

//    boolean DEBUG = true;
//    /**
//     * onMeasure() related methods
//     */
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        final int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
//        final int heighSpectMode = MeasureSpec.getMode(heightMeasureSpec);
//
////        final int newWidthMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth()+mStrokeWidth*4, widthSpecMode);
////        final int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredHeight()+mStrokeWidth*4, heighSpectMode);
////        setMeasuredDimension(newWidthMeasureSpec, newHeightMeasureSpec);
//        boolean reMeasureWidth = false;
//        boolean reMeasureHeight = false;
//        int newMeasuredWidth = 0;
//        int newMeasuredHeight = 0;
//
//        final int measuredWidthAndState = getMeasuredWidthAndState();
//        final int measuredHeightAndState = getMeasuredHeightAndState();
//        final int measuredWidthMode = MeasureSpec.getMode(measuredWidthAndState);
//        final int measuredHeightMode = MeasureSpec.getMode(measuredHeightAndState);
//
//        // check whether width require reMeasuring
////        if (widthSpecMode == MeasureSpec.AT_MOST) {
//            final int measuredWidth = getMeasuredWidth();
//            final int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
//            final int drawableWidth = mStrokeWidth;
//
//            if (drawableWidth > measuredWidth) {
//                newMeasuredWidth = drawableWidth < parentWidth ? drawableWidth : parentWidth;
//                reMeasureWidth = true;
//                if (DEBUG) {
//                    Log.i(TAG, "measureWidth:" + measuredWidth + ",newMeasureWidth:" + newMeasuredWidth);
//                }
//            }
////        }
//        // check whether height require reMeasuring
////        if (heighSpectMode == MeasureSpec.AT_MOST) {
//            final int measuredHeight = getMeasuredHeight();
//            final int parenHeight = MeasureSpec.getSize(heightMeasureSpec);
//            final int drawableHeight = mStrokeWidth;
//
//            if (drawableHeight > measuredHeight) {
//                newMeasuredHeight = drawableHeight < parenHeight ? drawableHeight : parenHeight;
//                reMeasureHeight = true;
//                if (DEBUG) {
//                    Log.i(TAG, "measuredHeight:" + measuredHeight + ",newMeasuredHeight:" + newMeasuredHeight);
//                }
//            }
////        }
//        // reMeasure if necessary
//        if (reMeasureWidth && reMeasureHeight) {
//            final int newWidthMeasureSpec = MeasureSpec.makeMeasureSpec(newMeasuredWidth, measuredWidthMode);
//            final int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(newMeasuredHeight, measuredHeightMode);
//            setMeasuredDimension(newWidthMeasureSpec, newHeightMeasureSpec);
//            if (DEBUG) {
//                Log.i(TAG, "reMeasureWidth && reMeasureHeight");
//            }
//        } else if (reMeasureWidth) {
//            final int newWidthMeasureSpec = MeasureSpec.makeMeasureSpec(newMeasuredWidth, measuredWidthMode);
//            setMeasuredDimension(newWidthMeasureSpec, measuredHeightAndState);
//            if (DEBUG) {
//                Log.i(TAG, "reMeasureWidth");
//            }
//        } else if (reMeasureHeight) {
//            final int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(newMeasuredHeight, measuredHeightMode);
//            setMeasuredDimension(measuredWidthAndState, newHeightMeasureSpec);
//            if (DEBUG) {
//                Log.i(TAG, "reMeasureHeight");
//            }
//        }
//    }


}
