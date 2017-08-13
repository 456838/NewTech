package com.salton123.xm.mvp.fm;

import android.app.Notification;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.salton123.base.BaseSupportFragment;
import com.salton123.event.StartBrotherEvent;
import com.salton123.mvp.ui.BaseSupportPresenterFragment;
import com.salton123.util.EventUtil;
import com.salton123.util.log.MLog;
import com.salton123.xm.MainActivity;
import com.salton123.xm.R;
import com.salton123.xm.mvp.business.AlbumListFmContract;
import com.salton123.xm.mvp.business.AlbumListFmPresenter;
import com.salton123.xm.mvp.view.EndLessOnScrollListener;
import com.salton123.xm.mvp.view.adapter.AlbumAdapter;
import com.salton123.xm.view.widget.StatusTitleBar;
import com.salton123.xm.view.widget.floatingmusicmenu.FloatingMusicMenu;
import com.salton123.xm.wrapper.XmAdsStatusAdapter;
import com.salton123.xm.wrapper.XmPlayerStatusAdapter;
import com.salton123.xm.xmUtils;
import com.ximalaya.ting.android.opensdk.model.PlayableModel;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.AlbumList;
import com.ximalaya.ting.android.opensdk.model.track.Track;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;
import com.ximalaya.ting.android.opensdk.player.appnotification.XmNotificationCreater;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayListControl;
import com.ximalaya.ting.android.opensdk.player.service.XmPlayerException;

import org.greenrobot.eventbus.Subscribe;
import org.xutils.common.Callback;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.Random;

import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;

/**
 * User: newSalton@outlook.com
 * Date: 2017/8/11 16:14
 * ModifyTime: 16:14
 * Description:
 */
public class SlidingUpPanelFragment extends BaseSupportPresenterFragment<AlbumListFmPresenter> implements AlbumListFmContract.View {
    //FloatingMusicButton
    private FloatingMusicMenu musicMenu;
    private FloatingActionButton playingBtn, modeBtn, detailBtn, nextBtn, aboutBtn;

    private SwipeRefreshLayout refresh;
    private RecyclerView recycler;
    private AlbumAdapter mAdapter;
    private int categoryId = 12;
    private int page = 1;
    private int pageSize = 20;
    private int calcDimension = 1;
    private String tagName = "郭德纲";
    StatusTitleBar mHeaderView;
    private XmPlayerManager mPlayerManager;

    @Override
    public int GetLayout() {
        return R.layout.fm_sliding_up_panel;
    }

    @Override
    public void InitVariable(Bundle savedInstanceState) {
        EventUtil.register(this);
        mPresenter = new AlbumListFmPresenter();
        int ranNum = new Random().nextInt(10);
        calcDimension = ranNum % 3 + 1;
        System.out.println("ranNum:" + ranNum + ",calcDimension:" + calcDimension);
        mPlayerManager = XmPlayerManager.getInstance(_mActivity);
        Notification mNotification = XmNotificationCreater.getInstanse(_mActivity).initNotification(_mActivity, MainActivity.class);
        // 如果之前贵方使用了 `XmPlayerManager.init(int id, Notification notification)` 这个初始化的方式
        // 请参考`4.8 播放器通知栏使用`重新添加新的通知栏布局,否则直接升级可能导致在部分手机播放时崩溃
        // 如果不想使用sdk内部搞好的notification,或者想自建notification 可以使用下面的  init()函数进行初始化
        mPlayerManager.init((int) System.currentTimeMillis(), mNotification);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //放在onViewCreated方便监听
        mPlayerManager.addPlayerStatusListener(playerStatusAdapter);
        mPlayerManager.addAdsStatusListener(adsStatusAdapter);
        mPlayerManager.setOnConnectedListerner(new XmPlayerManager.IConnectListener() {
            @Override
            public void onConnected() {
//                mXimalaya.setDefaultPagesize(50);
                mPlayerManager.setPlayMode(XmPlayListControl.PlayMode.PLAY_MODEL_LIST_LOOP);
                MLog.info("newsalton", "sdk connected");
            }
        });
    }

    @Override
    public void InitViewAndData() {
        refresh = f(R.id.refresh);
        recycler = f(R.id.recycler);
        mAdapter = new AlbumAdapter(recycler);
        refresh.setProgressViewOffset(false, 100, 200);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                mPresenter.getAlbumList(tagName, categoryId, calcDimension + "", page++, pageSize);
            }
        });
        LinearLayoutManager layout = new LinearLayoutManager(_mActivity);
        recycler.setLayoutManager(layout);
        recycler.addOnScrollListener(new EndLessOnScrollListener(layout, 1) {
            @Override
            public void onLoadMore() {
                mPresenter.getAlbumList(tagName, categoryId, calcDimension + "", page++, pageSize);
            }
        });
        mHeaderView = (StatusTitleBar) inflater().inflate(R.layout.simple_title_layout, null);
        mHeaderView.setTitleText("推荐", View.VISIBLE).setTitleListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                toast("hello");
            }
        });
        mAdapter.addHeaderView(mHeaderView);
        recycler.setAdapter(mAdapter.getHeaderAndFooterAdapter());

        musicMenu = f(R.id.floatingMusicMenu);
        playingBtn = f(R.id.fab_play);
        modeBtn = f(R.id.fab_mode);
        nextBtn = f(R.id.fab_next);
        detailBtn = f(R.id.fab_player);
        aboutBtn = f(R.id.fab_about);
        playingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toast("playingBtn");

            }
        });

        modeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toast("modeBtn");
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toast("nextBtn");
            }
        });
        detailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                toast("detailBtn");
//                ArrayList<Track> playList = (ArrayList<Track>) mPlayerManager.getPlayList();
                Album album = xmUtils.getAlbum(_mActivity);
                EventUtil.sendEvent(new StartBrotherEvent(TracksFragment.newInstance(TracksFragment.class, album)));
                musicMenu.collapse();
            }
        });
        aboutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toast("aboutBtn");
                EventUtil.sendEvent(new StartBrotherEvent(BaseSupportFragment.newInstance(AboutFragment.class)));
                musicMenu.collapse();
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.getAlbumList(tagName, categoryId, calcDimension + "", page++, pageSize);
    }

    @Override
    public void InitListener() {
        mAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
//                mPlayerManager.playList(mAdapter.getData(), position);
                Album album = mAdapter.getItem(position);
                xmUtils.saveAlbum(_mActivity, album);
//                SerializeUtils.serialization(getContext().getCacheDir()+ File.separator+"album",album);
                EventUtil.sendEvent(new StartBrotherEvent(BaseSupportFragment.newInstance(TracksFragment.class, album)));
            }
        });
    }


    @Override
    public void showAlbum(AlbumList list) {
        if (list.getAlbums().size() < pageSize) {
            toast("数据加载完毕");
        }
        if (refresh.isRefreshing()) {
            refresh.setRefreshing(false);
            page = 1;
            mAdapter.clear();
            mAdapter.setData(list.getAlbums());
        } else {
            mAdapter.addMoreData(list.getAlbums());
        }
    }

    @Override
    public void onError(int resCode, String errorMsg) {
        toast(errorMsg);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        mPlayerManager.removePlayerStatusListener(listener);
        EventUtil.unregister(this);
    }

    XmPlayerStatusAdapter listener = new XmPlayerStatusAdapter() {
        @Override
        public void onSoundSwitch(PlayableModel playableModel, PlayableModel playableModel1) {
            super.onSoundSwitch(playableModel, playableModel1);
            if (mAdapter != null) {
                mAdapter.notifyDataSetChanged();
            }
        }
    };

    @Subscribe
    public void updateCover(Track track) {
//        track.getCoverUrlSmall();
        x.image().loadDrawable(track.getCoverUrlSmall(), ImageOptions.DEFAULT, new Callback.CommonCallback<Drawable>() {
            @Override
            public void onSuccess(Drawable result) {
                musicMenu.setMusicCover(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

//        musicMenu.setMusicCover(getResources().getDrawable(R.drawable.moefou));
    }

    XmPlayerStatusAdapter playerStatusAdapter = new XmPlayerStatusAdapter() {
        @Override
        public void onPlayStart() {
            musicMenu.rotateStart();
        }

        @Override
        public void onPlayPause() {
            musicMenu.rotateStop();
        }

        @Override
        public void onPlayStop() {
            musicMenu.rotateStop();
        }

        @Override
        public void onPlayProgress(int currPos, int duration) {
            float progress = (float) currPos * 100 / (float) duration;
            musicMenu.setProgress(progress);
//            System.out.println("currPos:" + currPos + ",duration:" + duration + ",progress=" + progress);
        }

        @Override
        public boolean onError(XmPlayerException e) {
            return false;
        }

        @Override
        public void onSoundSwitch(PlayableModel lastModel, PlayableModel curMode) {

        }
    };

    XmAdsStatusAdapter adsStatusAdapter = new XmAdsStatusAdapter() {
        @Override
        public void onStartGetAdsInfo() {

        }
    };
}
