package com.salton123.xm;

import android.Manifest;
import android.os.Bundle;

import com.salton123.base.ApplicationBase;
import com.salton123.base.BaseSupportActivity;
import com.salton123.base.BaseSupportFragment;
import com.salton123.event.StartBrotherEvent;
import com.salton123.util.EventUtil;
import com.salton123.xm.mvp.fm.SlidingUpPanelFragment;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;
import com.ximalaya.ting.android.sdkdownloader.XmDownloadManager;

import org.greenrobot.eventbus.Subscribe;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/19 18:15
 * Time: 18:15
 * Description:
 */
public class MainActivity extends BaseSupportActivity {

    private CommonRequest mXimalaya;

    @Override
    public int GetLayout() {
        return R.layout.fm_container;
    }

    @Override
    public void InitVariable(Bundle savedInstanceState) {
        EventUtil.register(this);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.fl_container, BaseSupportFragment.newInstance(SlidingUpPanelFragment.class));
        }
        mXimalaya = CommonRequest.getInstanse();
        mXimalaya.init(this, XmConfig.APP_SECRET);
        // 此代码表示播放时会去监测下是否已经下载
        XmPlayerManager.getInstance(this).setCommonBusinessHandle(XmDownloadManager.getInstance());
        // 可以监听该Activity下的所有Fragment的18个 生命周期方法
//        registerFragmentLifecycleCallbacks(new FragmentLifecycleCallbacks() {
//
//            @Override
//            public void onFragmentSupportVisible(SupportFragment fragment) {
//                Log.i("MainActivity", "onFragmentSupportVisible--->" + fragment.getClass().getSimpleName());
//            }
//
//            @Override
//            public void onFragmentCreated(SupportFragment fragment, Bundle savedInstanceState) {
//                super.onFragmentCreated(fragment, savedInstanceState);
//            }
//            // 省略其余生命周期方法
//        });
    }

    @Override
    public void InitViewAndData() {

    }


    @Override
    public void InitListener() {
        checkPermission();
    }

    private void checkPermission() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.setLogging(true);
        rxPermissions.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .subscribe(new Observer<Permission>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Permission permission) {
                        if (permission.name.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE) && permission.granted) {
                            ApplicationBase.<XmApplication>getInstance().initXm();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

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
    protected void onDestroy() {
        super.onDestroy();
        EventUtil.unregister(this);
    }
}
