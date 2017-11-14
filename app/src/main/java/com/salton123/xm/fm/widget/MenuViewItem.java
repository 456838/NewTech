package com.salton123.xm.fm.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.salton123.util.Colors;

/**
 * User: newSalton@outlook.com
 * Date: 2017/9/9 13:16
 * ModifyTime: 13:16
 * Description:
 */
public class MenuViewItem extends FrameLayout {
    private static final String TAG = "MenuViewItem";
    private int width = -1;
    private int height = -1;
    private Bitmap bitmap;

    public MenuViewItem(@NonNull Context context) {
        super(context);
    }

    public MenuViewItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MenuViewItem(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MenuViewItem(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action != MotionEvent.ACTION_DOWN) {
            return super.onTouchEvent(event);
        }

        int x = (int) event.getX();
        int y = (int) event.getY();

        if (width == -1 || height == -1) {
            bitmap = ((BitmapDrawable) getBackground().getCurrent()).getBitmap();
            width = getWidth();
            height = getHeight();
        }

        if (bitmap == null || x < 0 || y < 0 || x >= width || y >= height) {
            return false;
        }
        if (x < bitmap.getWidth() && y < bitmap.getHeight()) {
            int pixel = bitmap.getPixel(x, y);
            if (pixel == Colors.TRANSPARENT) {
                return false;
            }
        }
        return super.onTouchEvent(event);
    }
}
