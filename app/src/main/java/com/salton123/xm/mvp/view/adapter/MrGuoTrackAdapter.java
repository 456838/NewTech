package com.salton123.xm.mvp.view.adapter;

import android.support.v7.widget.RecyclerView;

import com.facebook.drawee.view.DraweeView;
import com.salton123.common.image.FrescoImageLoader;
import com.salton123.xm.R;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/19 10:49
 * Time: 10:49
 * Description:
 */
public class MrGuoTrackAdapter extends BGARecyclerViewAdapter<Track>{
    public MrGuoTrackAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.adapter_mr_guo);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, Track model) {
        helper.setText(R.id.tv_name,model.getCoverUrlSmall()).setText(R.id.tv_intro,model.getRadioName());
        FrescoImageLoader.display((DraweeView) helper.getView(R.id.sdv_thumbnail),model.getValidCover());

    }
}
