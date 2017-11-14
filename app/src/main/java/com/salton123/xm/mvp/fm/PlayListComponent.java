package com.salton123.xm.mvp.fm;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.salton123.mvp.ui.BaseSupportPresenterFragment;
import com.salton123.xm.R;
import com.salton123.xm.mvp.business.OneToNContract;
import com.salton123.xm.mvp.business.OneToNPresenter;
import com.salton123.xm.view.widget.RecyclerViewFastScroller;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.TrackList;

/**
 * User: newSalton@outlook.com
 * Date: 2017/9/15 18:16
 * ModifyTime: 18:16
 * Description:
 */
public class PlayListComponent extends BaseSupportPresenterFragment<OneToNContract.Presenter> implements OneToNContract.TracksFmIView {
    RecyclerView recyclerView;
    RecyclerViewFastScroller fastScroller;
    ProgressBar progressBar;
    View emptyView;
    PlayListAdapter mAdapter ;
    private long album_id = 0;
    private int page = 1;
    private int pageSize = 20;
    private String sort = "asc";

    public static <T extends Fragment> T newInstance(Class<T> clz, long value) {
        Bundle bundle = new Bundle();
        bundle.putLong("arg_item", value);
        T fragment = null;

        try {
            fragment =clz.newInstance();
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public int GetLayout() {
        return R.layout.component_play_list;
    }

    @Override
    public void InitVariable(Bundle bundle) {
        if (getArguments().getParcelable(ARG_ITEM) instanceof Album){
            Album mAlbum;
            mAlbum = getArguments().getParcelable(ARG_ITEM);
            if(mAlbum!=null){
                album_id = mAlbum.getId();
            }
        }else if(getArguments().getLong(ARG_ITEM)>0){
            album_id= getArguments().getLong(ARG_ITEM);
        }
        mPresenter = new OneToNPresenter();
    }

    @Override
    public void InitViewAndData() {
        recyclerView =f(R.id.recycler_view);
        fastScroller =f(R.id.fast_scroller);
        progressBar= f(R.id.progress_bar);
        emptyView = f(R.id.text_view_empty);
    }

    @Override
    public void InitListener() {
        mAdapter = new PlayListAdapter(recyclerView);
    }

    @Override
    public void showTracks(TrackList list) {

    }

    @Override
    public void onShowTracksError(int resCode, String errorMsg) {

    }
}
