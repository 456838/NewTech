package com.salton123.xm.fm;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.salton123.base.BaseSupportFragment;
import com.salton123.util.ScreenUtils;
import com.salton123.xm.R;
import com.salton123.xm.fm.widget.StrokeDimTextView;
import com.salton123.xm.fm.widget.StrokeText2;
import com.salton123.xm.fm.widget.StrokeTextView;
import com.salton123.xm.fm.widget.StrokeTextView3;

/**
 * User: newSalton@outlook.com
 * Date: 2017/7/27 22:53
 * ModifyTime: 22:53
 * Description:
 */
public class TestFragment extends BaseSupportFragment {

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.e("aa", "onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("aa", "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("aa", "onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("aa", "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("aa", "onStart");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("aa", "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("aa", "onDetach");
    }

    @Override
    public int GetLayout() {
        return R.layout.fm_test;
    }

    @Override
    public void InitVariable(Bundle savedInstanceState) {

    }

    @Override
    public void InitViewAndData() {
        final StrokeTextView tv_stroke = f(R.id.tv_stroke);

        Typeface typeface = FontUtils.getTypeFace(_mActivity, FontUtils.FontType.REFRIGERATOR_DELUXE_HEAVY);
        tv_stroke.setTypeface(typeface);
        tv_stroke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_stroke.setText("502");
            }
        });
        StrokeDimTextView strokeText = f(R.id.strokeText);
        strokeText.setTypeface(typeface);
        strokeText.setText("89");
        final StrokeTextView3 tv_stroke3 = f(R.id.tv_stroke3);
        tv_stroke3.setTypeface(typeface);
        tv_stroke3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_stroke3.setText("502");
            }
        });


        TextView tv_remind_count = f(R.id.tv_remind_count);
        SpannableString spannableString = new SpannableString("1444人");
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#0099EE"));
        spannableString.setSpan(colorSpan, 1, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv_remind_count.setText("正在参与:" + spannableString);
        float px = ScreenUtils.dpToPx(_mActivity, 16);
        System.out.println("InitViewAndData:" + px);
        DisplayMetrics metric = new DisplayMetrics();
        _mActivity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;  // 屏幕宽度（像素）
        int height = metric.heightPixels;  // 屏幕高度（像素）
        float density = metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
        System.out.println("width="+width+",height="+height+",density="+density+",densityDpi="+densityDpi+",px="+px);
//        width=1080,height=1920,density=2.625,densityDpi=420

        StrokeText2 strokeText2 = f(R.id.strokeText2);
        strokeText2.setTypeface(typeface);
        strokeText2.setStrokeColor(Color.parseColor("#0099EE"));
        strokeText2.setStrokeWidth(5.0f);
        strokeText2.setStroke(true);

        TextView tv_698 = f(R.id.tv_698);
//        tv_698.setTypeface(typeface);

        SpannableString spannableString2 = new SpannableString(" "+201+"分");
        Drawable drawable = getResources().getDrawable(R.drawable.label_score);
        drawable.setBounds(0, 0, 20, 20);
        ImageSpan imageSpan = new ImageSpan(drawable);
        spannableString2.setSpan(imageSpan, 1, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv_698.setText(spannableString);
    }

    static boolean status;

    @Override
    public void InitListener() {
        f(R.id.tv_method_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadRotateAnimation(v, status);
                status = !status;
            }
        });
    }

    ObjectAnimator animator;

    public void loadRotateAnimation(final View view, boolean show) {
        if (show) {
            if (animator != null && !animator.isRunning())
                view.setVisibility(View.VISIBLE);
            animator = ObjectAnimator.ofFloat(view, "rotation", 0, 360);
            animator.setInterpolator(new LinearInterpolator());
            animator.setRepeatCount(-1);
            animator.setDuration(2000);
            animator.start();
        } else {
            view.post(new Runnable() {
                @Override
                public void run() {
                    if (animator != null && animator.isRunning()) {
                        animator.cancel();
                        view.setVisibility(View.GONE);
                    }
                }
            });

        }
    }

}
