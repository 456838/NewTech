package com.salton123.xm.mvp.business;

import com.salton123.mvp.presenter.RxPresenter;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.track.TrackList;

import java.util.HashMap;
import java.util.Map;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/19 10:34
 * Time: 10:34
 * Description:
 */
public class TracksFmPresenter extends RxPresenter<TracksFmContract.View> implements TracksFmContract.Presenter {

    @Override
    public void getTracks(long album_id, String sort, int page, int pageSize) {
        Map<String, String> map = new HashMap<String, String>();
        map.put(DTransferConstants.ALBUM_ID, album_id+"");
        map.put(DTransferConstants.SORT, sort);
        map.put(DTransferConstants.PAGE, page+"");
        map.put(DTransferConstants.PAGE_SIZE, pageSize+"");
        CommonRequest.getTracks(map, new IDataCallBack<TrackList>(){

            @Override
            public void onSuccess(TrackList trackList) {
                if(mView!=null){        //抛弃UI销毁以后的操作
                    mView.showTracks(trackList);
                }

            }

            @Override
            public void onError(int i, String s) {
                mView.onError(i, s);
            }
        });
    }
}
