package com.salton123.xm;

import android.Manifest;
import android.app.Notification;
import android.os.Bundle;
import android.util.Log;

import com.salton123.base.ApplicationBase;
import com.salton123.base.BaseSupportActivity;
import com.salton123.base.BaseSupportFragment;
import com.salton123.event.StartBrotherEvent;
import com.salton123.util.EventUtil;
import com.salton123.util.log.MLog;
import com.salton123.xm.mvp.fm.AlbumListFragment;
import com.salton123.xm.wrapper.XmAdsStatusAdapter;
import com.salton123.xm.wrapper.XmPlayerStatusAdapter;
import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.model.PlayableModel;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;
import com.ximalaya.ting.android.opensdk.player.appnotification.XmNotificationCreater;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;
import com.ximalaya.ting.android.sdkdownloader.XmDownloadManager;

import org.greenrobot.eventbus.Subscribe;

import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.helper.FragmentLifecycleCallbacks;
import rx.Subscriber;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/19 18:15
 * Time: 18:15
 * Description:
 */
public class MainActivity extends BaseSupportActivity {

    private CommonRequest mXimalaya;
    private XmPlayerManager mPlayerManager;

    @Override
    public int GetLayout() {
        return R.layout.fm_container;
    }

    @Override
    public void InitVariable(Bundle savedInstanceState) {
        EventUtil.register(this);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.fl_container, BaseSupportFragment.newInstance(AlbumListFragment.class));
        }
        mXimalaya = CommonRequest.getInstanse();
        mXimalaya.init(this, XmConfig.APP_SECRET);
        mPlayerManager = XmPlayerManager.getInstance(this);
        Notification mNotification = XmNotificationCreater.getInstanse(this).initNotification(this.getApplicationContext(), MainActivity.class);
        // 如果之前贵方使用了 `XmPlayerManager.init(int id, Notification notification)` 这个初始化的方式
        // 请参考`4.8 播放器通知栏使用`重新添加新的通知栏布局,否则直接升级可能导致在部分手机播放时崩溃
        // 如果不想使用sdk内部搞好的notification,或者想自建notification 可以使用下面的  init()函数进行初始化
        mPlayerManager.init((int) System.currentTimeMillis(), mNotification);
//		mPlayerManager.init();
        mPlayerManager.addPlayerStatusListener(playerStatusAdapter);
        mPlayerManager.addAdsStatusListener(adsStatusAdapter);
        mPlayerManager.setOnConnectedListerner(new XmPlayerManager.IConnectListener() {
            @Override
            public void onConnected() {
                mXimalaya.setDefaultPagesize(50);
                mPlayerManager.setPlayMode(XmPlayListControl.PlayMode.PLAY_MODEL_LIST_LOOP);
                MLog.info("newsalton", "sdk connected");
            }
        });

        // 此代码表示播放时会去监测下是否已经下载
        XmPlayerManager.getInstance(this).setCommonBusinessHandle(XmDownloadManager.getInstance());
        // 可以监听该Activity下的所有Fragment的18个 生命周期方法
        registerFragmentLifecycleCallbacks(new FragmentLifecycleCallbacks() {

            @Override
            public void onFragmentSupportVisible(SupportFragment fragment) {
                Log.i("MainActivity", "onFragmentSupportVisible--->" + fragment.getClass().getSimpleName());
            }

            @Override
            public void onFragmentCreated(SupportFragment fragment, Bundle savedInstanceState) {
                super.onFragmentCreated(fragment, savedInstanceState);
            }
            // 省略其余生命周期方法
        });
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
                .subscribe(new Subscriber<Permission>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Permission permission) {
                        if (permission.name.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE) && permission.granted) {
                            ApplicationBase.<XmApplication>getInstance().initXm();
                        }
                    }
                });
    }

    XmPlayerStatusAdapter playerStatusAdapter = new XmPlayerStatusAdapter() {
        @Override
        public void onSoundSwitch(PlayableModel playableModel, PlayableModel playableModel1) {
            super.onSoundSwitch(playableModel, playableModel1);

        }
    };

    XmAdsStatusAdapter adsStatusAdapter = new XmAdsStatusAdapter() {
        @Override
        public void onStartGetAdsInfo() {

        }
    };

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
