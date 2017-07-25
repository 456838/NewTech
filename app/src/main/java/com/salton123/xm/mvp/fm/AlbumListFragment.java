package com.salton123.xm.mvp.fm;

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
import com.salton123.xm.R;
import com.salton123.xm.mvp.view.EndLessOnScrollListener;
import com.salton123.xm.mvp.business.AlbumListFmContract;
import com.salton123.xm.mvp.business.AlbumListFmPresenter;
import com.salton123.xm.mvp.view.adapter.AlbumAdapter;
import com.salton123.xm.wrapper.XmPlayerStatusAdapter;
import com.ximalaya.ting.android.opensdk.model.PlayableModel;
import com.ximalaya.ting.android.opensdk.model.album.AlbumList;

import java.util.Random;

import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/21 20:35
 * Time: 20:35
 * Description:
 */
public class AlbumListFragment extends BaseSupportPresenterFragment<AlbumListFmPresenter> implements AlbumListFmContract.View {
    private SwipeRefreshLayout refresh;
    private RecyclerView recycler;
    private AlbumAdapter mAdapter;
    private int categoryId = 12;
    private int page = 1;
    private int pageSize = 20;
    private int calcDimension = 1;
    private String tagName = "郭德纲";
//    private XmPlayerManager mPlayerManager;

    @Override
    public int GetLayout() {
        return R.layout.fm_mr_guo;
    }

    @Override
    public void InitVariable(Bundle savedInstanceState) {
        mPresenter = new AlbumListFmPresenter();
        int ranNum =new Random().nextInt(10);
        calcDimension =ranNum%3;
        System.out.println("ranNum:"+ranNum+",calcDimension:"+calcDimension);
//        mPlayerManager = XmPlayerManager.getInstance(_mActivity);
//        mPlayerManager.addPlayerStatusListener(listener);
//        mPresenter.getCategories();
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
                mPresenter.getAlbumList(tagName, categoryId, calcDimension+"", page++, pageSize);
            }
        });
        LinearLayoutManager layout = new LinearLayoutManager(_mActivity);
        recycler.setLayoutManager(layout);
        recycler.addOnScrollListener(new EndLessOnScrollListener(layout, 1) {
            @Override
            public void onLoadMore() {
                mPresenter.getAlbumList(tagName, categoryId, calcDimension+"", page++, pageSize);
            }
        });
        recycler.setAdapter(mAdapter);

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.getAlbumList(tagName, categoryId,calcDimension+"", page++, pageSize);
    }

    @Override
    public void InitListener() {
        mAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
//                mPlayerManager.playList(mAdapter.getData(), position);
                EventUtil.sendEvent(new StartBrotherEvent(BaseSupportFragment.newInstance(TracksFragment.class, mAdapter.getItem(position))));
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
    public void onDestroyView() {
        super.onDestroyView();
//        mPlayerManager.removePlayerStatusListener(listener);
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
