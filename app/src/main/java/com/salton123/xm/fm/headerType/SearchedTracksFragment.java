package com.salton123.xm.fm.headerType;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.salton123.mvp.ui.BaseSupportPresenterFragment;
import com.salton123.xm.R;

import com.salton123.xm.business.OneToNContract;
import com.salton123.xm.business.OneToNPresenter;
import com.salton123.xm.view.EndLessOnScrollListener;
import com.salton123.xm.view.adapter.MrGuoTrackAdapter;
import com.salton123.xm.wrapper.XmPlayerStatusAdapter;
import com.ximalaya.ting.android.opensdk.model.PlayableModel;
import com.ximalaya.ting.android.opensdk.model.track.SearchTrackList;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;

import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/19 10:17
 * Time: 10:17
 * Description:
 */
public class SearchedTracksFragment extends BaseSupportPresenterFragment<OneToNContract.Presenter> implements OneToNContract.SearchedTracksFmIView {
    private SwipeRefreshLayout refresh;
    private RecyclerView recycler;
    private MrGuoTrackAdapter mAdapter;
    private int categoryId = 0;
    private int page = 1;
    private int pageSize = 20;
    private String keyword = "郭德纲";
    private XmPlayerManager mPlayerManager;

    @Override
    public int GetLayout() {
        return R.layout.fm_album_list;
    }

//    Album mAlbum;

    @Override
    public void InitVariable(Bundle savedInstanceState) {
        keyword = getArguments().getString(ARG_ITEM);
        mPresenter = new OneToNPresenter();
        mPlayerManager = XmPlayerManager.getInstance(_mActivity);
        mPlayerManager.addPlayerStatusListener(listener);
    }

    @Override
    public void InitViewAndData() {
        refresh = f(R.id.refresh);
        recycler = f(R.id.recycler);
        mAdapter = new MrGuoTrackAdapter(recycler);
        refresh.setProgressViewOffset(false, 100, 200);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                mPresenter.getSearchedTracks(keyword, categoryId, 4, page++, pageSize);
            }
        });
        LinearLayoutManager layout = new LinearLayoutManager(_mActivity);
        recycler.setLayoutManager(layout);
        recycler.addOnScrollListener(new EndLessOnScrollListener(layout, 1) {
            @Override
            public void onLoadMore() {
                mPresenter.getSearchedTracks(keyword, categoryId, 4, page++, pageSize);
            }
        });
        recycler.setAdapter(mAdapter);

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.getSearchedTracks(keyword, categoryId,4, page++, pageSize);
    }

    @Override
    public void InitListener() {
        mAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                mPlayerManager.playList(mAdapter.getData(), position);
            }
        });
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

    @Override
    public void onSearchedTracksData(SearchTrackList searchTrackList) {
        if (searchTrackList.getTracks().size() < pageSize) toast("数据加载完毕");
        if (refresh.isRefreshing()) {
            refresh.setRefreshing(false);
//            page = 1;
            mAdapter.clear();
            mAdapter.setData(searchTrackList.getTracks());
        } else {
            mAdapter.addMoreData(searchTrackList.getTracks());
        }
    }

    @Override
    public void onSearchedTracksError(int resCode, String errorMsg) {
        toast(errorMsg);
    }
}
