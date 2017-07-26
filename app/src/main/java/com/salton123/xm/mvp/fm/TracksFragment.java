package com.salton123.xm.mvp.fm;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.salton123.common.image.FrescoImageLoader;
import com.salton123.mvp.ui.BaseSupportPresenterFragment;
import com.salton123.xm.R;
import com.salton123.xm.mvp.business.TracksFmContract;
import com.salton123.xm.mvp.business.TracksFmPresenter;
import com.salton123.xm.mvp.view.EndLessOnScrollListener;
import com.salton123.xm.mvp.view.adapter.TracksAdapter;
import com.salton123.xm.wrapper.XmPlayerStatusAdapter;
import com.ximalaya.ting.android.opensdk.auth.utils.StringUtil;
import com.ximalaya.ting.android.opensdk.model.PlayableModel;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.TrackList;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;
import com.ximalaya.ting.android.sdkdownloader.XmDownloadManager;
import com.ximalaya.ting.android.sdkdownloader.downloadutil.IDoSomethingProgress;
import com.ximalaya.ting.android.sdkdownloader.exception.AddDownloadException;

import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;


/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/21 20:35
 * Time: 20:35
 * Description:
 */
public class TracksFragment extends BaseSupportPresenterFragment<TracksFmPresenter> implements TracksFmContract.View {
    private SwipeRefreshLayout refresh;
    private RecyclerView recycler;
    private TracksAdapter mAdapter;
    private long album_id = 0;
    private int page = 1;
    private int pageSize = 20;
    private String sort = "asc";
    private XmPlayerManager mPlayerManager;

    @Override
    public int GetLayout() {
        return R.layout.fm_mr_guo;
    }

    Album mAlbum;
    RelativeLayout ll_track_intro;

    @Override
    public void InitVariable(Bundle savedInstanceState) {
        mAlbum = getArguments().getParcelable(ARG_ITEM);
        album_id = mAlbum.getId();
        mPresenter = new TracksFmPresenter();
        mPlayerManager = XmPlayerManager.getInstance(_mActivity);
        mPlayerManager.addPlayerStatusListener(listener);
    }

    @Override
    public void InitViewAndData() {
        refresh = f(R.id.refresh);
        recycler = f(R.id.recycler);
        mAdapter = new TracksAdapter(recycler);
        refresh.setProgressViewOffset(false, 100, 200);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                mPresenter.getTracks(album_id, sort, page++, pageSize);
            }
        });
        LinearLayoutManager layout = new LinearLayoutManager(_mActivity);
        recycler.setLayoutManager(layout);
        recycler.addOnScrollListener(new EndLessOnScrollListener(layout, 1) {
            @Override
            public void onLoadMore() {
                mPresenter.getTracks(album_id, sort, page++, pageSize);
            }
        });
        ll_track_intro = (RelativeLayout) inflater().inflate(R.layout.music_detial_top_item, null);
        mAdapter.addHeaderView(ll_track_intro);
        recycler.setAdapter(mAdapter.getHeaderAndFooterAdapter());
    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.getTracks(album_id, sort, page++, pageSize);
    }

    @Override
    public void InitListener() {
        mAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                loadRotateAnimation(itemView.findViewById(R.id.iv_play_music));
                mPlayerManager.playList(mAdapter.getData(), position);
            }
        });
        mAdapter.setOnItemChildClickListener(new BGAOnItemChildClickListener() {
            @Override
            public void onItemChildClick(ViewGroup parent, View childView, int position) {
                switch (childView.getId()) {
                    case R.id.iv_download:
                        //ui
                        childView.setEnabled(false);
                        XmDownloadManager.getInstance().downloadSingleTrack(mAdapter.getItem(position).getDataId(), new IDoSomethingProgress<AddDownloadException>() {
                            @Override
                            public void begin() {

                            }

                            @Override
                            public void success() {

                            }

                            @Override
                            public void fail(AddDownloadException e) {

                            }
                        });
                        break;
                }
            }
        });
    }

    private void loadRotateAnimation(View view){
        view.setVisibility(View.VISIBLE);
        ObjectAnimator animator = ObjectAnimator.ofFloat(view,"rotation",0,360);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(-1);
        animator.setDuration(2000);
        animator.start();
    }


    @Override
    public void showTracks(TrackList list) {
        if (list.getTracks().size() < pageSize) toast("数据加载完毕");
        if (refresh.isRefreshing()) {
            refresh.setRefreshing(false);
//            page = 1;
            mAdapter.clear();
            mAdapter.setData(list.getTracks());
        } else {
            mAdapter.addMoreData(list.getTracks());
        }
        initHeaderData(list);
    }


    private void initHeaderData(TrackList list) {
        SimpleDraweeView sdv_thumbnail = (SimpleDraweeView) ll_track_intro.findViewById(R.id.sdv_thumbnail);
        TextView tv_title = (TextView) ll_track_intro.findViewById(R.id.title);
        TextView tv_expandable = (TextView) ll_track_intro.findViewById(R.id.expandable_text);
        FrescoImageLoader.display(sdv_thumbnail, list.getCoverUrlLarge());
        if (StringUtil.isBlank(list.getAlbumTitle())) {
            tv_title.setVisibility(View.GONE);
        }
        if (StringUtil.isBlank(list.getAlbumIntro())) {
            tv_expandable.setVisibility(View.GONE);
        }
        tv_title.setText(list.getAlbumTitle());
        String intro = list.getAlbumIntro();
        tv_expandable.setText(intro == null ? "" : intro);
    }


    @Override
    public void onError(int resCode, String errorMsg) {
        toast(errorMsg);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPlayerManager.removePlayerStatusListener(listener);
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
}
