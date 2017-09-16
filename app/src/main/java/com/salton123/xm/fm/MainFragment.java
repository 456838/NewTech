package com.salton123.xm.fm;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;

import com.salton123.base.BaseSupportFragment;
import com.salton123.event.StartBrotherEvent;
import com.salton123.xm.R;
import com.salton123.xm.view.widget.BottomBar;
import com.salton123.xm.view.widget.BottomBarTab;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * User: newSalton@outlook.com
 * Date: 2017/8/15 10:47
 * ModifyTime: 10:47
 * Description:
 */
public class MainFragment extends BaseSupportFragment {
    BottomBar mNavigationView;
    private SupportFragment[] mFragments = new SupportFragment[4];
    public static final int ZERO = 0;
    public static final int FIRST = 1;
    public static final int SECOND = 2;
    public static final int THIRD = 3;

    @Override
    public int GetLayout() {
        return R.layout.fm_main;
    }

    @Override
    public void InitVariable(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mFragments[ZERO] = BaseSupportFragment.newInstance(FirstFragment.class);
            mFragments[FIRST] = (BaseSupportFragment.newInstance(SecondFragment.class));
//            SecondFragment fragment = (SecondFragment) ARouter.getInstance().build("/fm/secondfragment").navigation();
//            mFragments[FIRST] = fragment;
            mFragments[SECOND] = (BaseSupportFragment.newInstance(ThirdFragment.class));
            mFragments[THIRD] = (BaseSupportFragment.newInstance(FourthFragment.class));
            loadMultipleRootFragment(R.id.act_container, ZERO
                    , mFragments[ZERO]
                    , mFragments[FIRST]
                    , mFragments[SECOND]
                    , mFragments[THIRD]
            );
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用,也可以通过getSupportFragmentManager.getFragments()自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[ZERO] = findChildFragment(FirstFragment.class);
            mFragments[FIRST] = findChildFragment(SecondFragment.class);
            mFragments[SECOND] = findChildFragment(ThirdFragment.class);
            mFragments[THIRD] = findChildFragment(FourthFragment.class);
        }
    }

    @Override
    public void InitViewAndData() {
        EventBus.getDefault().register(this);
        mNavigationView = f(R.id.bottom_navigation_bar);
        mNavigationView
                .addItem(new BottomBarTab(_mActivity, R.drawable.movie_icon, "热门"))
                .addItem(new BottomBarTab(_mActivity, R.drawable.music_icon, "直播"))
                .addItem(new BottomBarTab(_mActivity, R.drawable.book_icon, "小视频"))
                .addItem(new BottomBarTab(_mActivity, R.drawable.newspaper_icon, "个人"))
        ;
    }

    @Override
    public void InitListener() {
        mNavigationView.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
//                showHideFragment(mFragments[position]);
                showHideFragment(mFragments[position], mFragments[prePosition]);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
                // 这里推荐使用EventBus来实现 -> 解耦
                // 在FirstPagerFragment,FirstHomeFragment中接收, 因为是嵌套的Fragment
                // 主要为了交互: 重选tab 如果列表不在顶部则移动到顶部,如果已经在顶部,则刷新
            }
        });
    }

    /**
     * start other BrotherFragment
     */
    @Subscribe
    public void startBrother(StartBrotherEvent event) {
        start(event.targetFragment);
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    /**
     * 处理回退事件
     *
     * @return
     */
    @Override
    public boolean onBackPressedSupport() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(_mActivity);
        builder.
                setTitle("Hi").
                setMessage(Html.fromHtml("要退出App吗？")).
                setPositiveButton("嗯", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        _mActivity.finish();
                        System.exit(0);
                    }
                }).setNegativeButton("再看看", null)
                .show();
        return true;
    }
//
//    public interface OnBackToFirstListener {
//        void onBackToFirstFragment();
//    }
}
