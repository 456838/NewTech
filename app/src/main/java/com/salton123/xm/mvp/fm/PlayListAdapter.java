package com.salton123.xm.mvp.fm;

import android.support.v7.widget.RecyclerView;

import com.salton123.util.SizeUtils;
import com.salton123.xm.R;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * User: newSalton@outlook.com
 * Date: 2017/9/15 18:38
 * ModifyTime: 18:38
 * Description:
 */
public class PlayListAdapter extends BGARecyclerViewAdapter<Track> {

    public PlayListAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.item_local_music);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, Track model) {
        helper.setText(R.id.text_view_name, model.getTrackTitle() + "").setText(R.id.text_view_artist, model.getTrackTags()).setText(R.id.text_view_duration, SizeUtils.FormetFileSize(model.getDownloadSize()));

    }
}
