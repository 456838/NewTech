package com.salton123.xm.mvp.view.adapter;

import android.support.v7.widget.RecyclerView;

import com.salton123.util.SizeUtils;
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
public class TracksAdapter extends BGARecyclerViewAdapter<Track> {
    public TracksAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.adapter_tracks);
    }

    @Override
    protected void setItemChildListener(BGAViewHolderHelper helper, int viewType) {
        super.setItemChildListener(helper, viewType);
        helper.setItemChildClickListener(R.id.iv_download);
        helper.setItemChildClickListener(R.id.iv_play_music);
    }


    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, Track model) {
        helper
//                .setText(R.id.author, model.getAnnouncer().getNickname() + "")
                .setText(R.id.track_title, model.getTrackTitle() + "")
                .setText(R.id.track_tags, SizeUtils.FormetFileSize(model.getDownloadSize()))
//                .setText(R.id.subhead, model.getTrackTags() + "")
//                .setText(R.id.post_time, model.getTrackIntro())
 ;

//        FrescoImageLoader.display((DraweeView) helper.getView(R.id.sdv_thumbnail), model.getCoverUrlLarge());
//        LogUtils.e("aa" + new GsonBuilder().setPrettyPrinting().create().toJson(model));
    }


}
