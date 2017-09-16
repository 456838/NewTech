package com.salton123.xm.fm.headerType;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.salton123.xm.R;

import com.salton123.xm.business.OneToNContract;
import com.salton123.xm.business.OneToNPresenter;

import com.salton123.xm.mvp.fm.TracksFragment;
import com.salton123.xm.view.EndLessOnScrollListener;
import com.salton123.xm.view.adapter.AlbumAdapter;
import com.salton123.xm.xmUtils;
import com.ximalaya.ting.android.opensdk.auth.utils.StringUtil;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.album.AlbumList;

import java.util.Random;

import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;

/**
 * User: newSalton@outlook.com
 * Date: 2017/8/14 20:22
 * ModifyTime: 20:22
 * Description:
 */
public class AlbumListFragment extends BaseSupportPresenterFragment<OneToNContract.Presenter> implements OneToNContract.AlbumListIView {
    private static final String TAG = AlbumListFragment.class.getName();
    private SwipeRefreshLayout refresh;
    private RecyclerView recycler;
    private AlbumAdapter mAdapter;
    private int categoryId = 12;
    private int page = 1;
    private int pageSize = 20;
    private int calcDimension = 1;
    private String tagName = "郭德纲";

    @Override
    public int GetLayout() {
        return R.layout.fm_album_list;
    }

    @Override
    public void InitVariable(Bundle savedInstanceState) {
        tagName = getArguments().getString(ARG_ITEM);
        if (StringUtil.isBlank(tagName))tagName="郭德纲";
        mPresenter = new OneToNPresenter();
        int ranNum = new Random().nextInt(10);
        calcDimension = ranNum % 3 + 1;
        System.out.println("ranNum:" + ranNum + ",calcDimension:" + calcDimension);
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
                mPresenter.getAlbumList(tagName, categoryId, calcDimension , page++, pageSize);
            }
        });
        LinearLayoutManager layout = new LinearLayoutManager(_mActivity);
        recycler.setLayoutManager(layout);
        recycler.addOnScrollListener(new EndLessOnScrollListener(layout, 1) {
            @Override
            public void onLoadMore() {
                mPresenter.getAlbumList(tagName, categoryId, calcDimension, page++, pageSize);
            }
        });
        recycler.setAdapter(mAdapter);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        MLog.info("aa","[onLazyInitView] tagName="+tagName+",categoryId="+categoryId+",page="+page+",pageSize="+pageSize);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.getAlbumList(tagName, categoryId, calcDimension , page++, pageSize);
    }

    @Override
    public void InitListener() {
        mAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                Album album = mAdapter.getItem(position);
                xmUtils.saveAlbum(_mActivity, album);
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
    public void onShowAlbumError(int resCode, String errorMsg) {
        MLog.error(TAG,"[onShowAlbumError] resCode= "+resCode+",errorMsg="+errorMsg);
        toast(errorMsg);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
