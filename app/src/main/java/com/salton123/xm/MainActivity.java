package com.salton123.xm;

import android.Manifest;
import android.os.Bundle;

import com.salton123.base.BaseSupportActivity;
import com.salton123.base.BaseSupportFragment;
import com.salton123.event.StartBrotherEvent;
import com.salton123.util.EventUtil;
import com.salton123.xm.fm.MainFragment;
import com.salton123.xm.util.ToolUtil;
import com.salton123.xm.wrapper.XmAdsStatusAdapter;
import com.salton123.xm.wrapper.XmPlayerStatusAdapter;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.ximalaya.ting.android.opensdk.model.PlayableModel;
import com.ximalaya.ting.android.opensdk.model.advertis.Advertis;
import com.ximalaya.ting.android.opensdk.model.live.radio.Radio;
import com.ximalaya.ting.android.opensdk.model.live.schedule.Schedule;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;

import org.greenrobot.eventbus.Subscribe;

import io.reactivex.functions.Consumer;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/19 18:15
 * Time: 18:15
 * Description:
 */
public class MainActivity extends BaseSupportActivity {
    private XmPlayerManager mPlayerManager;
    @Override
    public int GetLayout() {
        return R.layout.fm_container;
    }

    @Override
    public void InitVariable(Bundle savedInstanceState) {
        EventUtil.register(this);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.fl_container, BaseSupportFragment.newInstance(MainFragment.class));
        }
        // 此代码表示播放时会去监测下是否已经下载
        mPlayerManager = XmPlayerManager.getInstance(this);
        mPlayerManager.setCommonBusinessHandle(XmlyInitializer.getInstance().getDownloader());
        mPlayerManager.init();
        mPlayerManager.addPlayerStatusListener(mPlayrStatusListener);
        mPlayerManager.addAdsStatusListener(mAdsStatusAdapter);
        mPlayerManager.addOnConnectedListerner(new XmPlayerManager.IConnectListener() {
            @Override
            public void onConnected() {
                mPlayerManager.removeOnConnectedListerner(this);
                mPlayerManager.setPlayMode(XmPlayListControl.PlayMode.PLAY_MODEL_LIST_LOOP);
                toast("播放器初始化成功！");
            }
        });
        XmlyInitializer.getInstance().registerReceiver(XmlyBroadcastReceiver.class,MainActivity.class);
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
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.name.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE) && permission.granted) {
//                            ApplicationBase.<XmApplication>getInstance().initXm();
                        }
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
        if (mPlayerManager!=null){
            mPlayerManager.removePlayerStatusListener(mPlayrStatusListener);
        }
       XmlyInitializer.unInitialize();
    }

    XmPlayerStatusAdapter mPlayrStatusListener = new XmPlayerStatusAdapter() {
        @Override
        public void onSoundSwitch(PlayableModel playableModel, PlayableModel playableModel1) {
            super.onSoundSwitch(playableModel, playableModel1);
            PlayableModel model = mPlayerManager.getCurrSound();
            if (model != null) {
                String title = null;
                String coverUrl = null;
                if (model instanceof Track) {
                    Track info = (Track) model;
                    title = info.getTrackTitle();
                    coverUrl = info.getCoverUrlLarge();
                } else if (model instanceof Schedule) {
                    Schedule program = (Schedule) model;
                    title = program.getRelatedProgram().getProgramName();
                    coverUrl = program.getRelatedProgram().getBackPicUrl();
                } else if (model instanceof Radio) {
                    Radio radio = (Radio) model;
                    title = radio.getRadioName();
                    coverUrl = radio.getCoverUrlLarge();
                }
            }
        }

        @Override
        public void onPlayProgress(int currPos, int duration) {
            super.onPlayProgress(currPos, duration);
            String title = "";
            PlayableModel info = mPlayerManager.getCurrSound();
            if (info != null) {
                if (info instanceof Track) {
                    title = ((Track) info).getTrackTitle();
                } else if (info instanceof Schedule) {
                    title = ((Schedule) info).getRelatedProgram().getProgramName();
                } else if (info instanceof Radio) {
                    title = ((Radio) info).getRadioName();
                }
            }
            String line =title + "[" + ToolUtil.formatTime(currPos) + "/" + ToolUtil.formatTime(duration) + "]";
            int progress = (int) (100 * currPos / (float) duration);
        }
    };

    XmAdsStatusAdapter mAdsStatusAdapter = new XmAdsStatusAdapter(){
        @Override
        public void onStartPlayAds(Advertis advertis, int i) {
            super.onStartPlayAds(advertis, i);
            advertis.getImageUrl();
        }

        @Override
        public void onCompletePlayAds() {
            super.onCompletePlayAds();
            PlayableModel model = mPlayerManager.getCurrSound();
            if (model!=null && model instanceof Track){
                ((Track)model).getCoverUrlSmall();
            }
        }
    };

}
