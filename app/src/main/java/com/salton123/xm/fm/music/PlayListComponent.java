package com.salton123.xm.fm.music;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.salton123.base.BaseSupportFragment;
import com.salton123.xm.R;
import com.salton123.xm.fm.widget.RecyclerViewFastScroller;

/**
 * User: newSalton@outlook.com
 * Date: 2017/9/15 18:16
 * ModifyTime: 18:16
 * Description:
 */
public class PlayListComponent extends BaseSupportFragment {
    RecyclerView recyclerView;
    RecyclerViewFastScroller fastScroller;
    ProgressBar progressBar;
    View emptyView;
    PlayListAdapter mAdapter ;

    @Override
    public int GetLayout() {
        return R.layout.component_play_list;
    }

    @Override
    public void InitVariable(Bundle bundle) {

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

    }
}
