package com.salton123.xm.mvp.business;

import com.salton123.mvp.presenter.BasePresenter;
import com.salton123.mvp.view.BaseView;
import com.ximalaya.ting.android.opensdk.model.track.TrackList;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/19 10:18
 * Time: 10:18
 * Description:
 */
public interface TracksFmContract {
    interface View extends BaseView {

        void showTracks(TrackList list);

        void onError(int resCode, String errorMsg);
    }

    interface Presenter extends BasePresenter<View> {
        /**
         * @param album_id        专辑ID
         * @param sort    sort String 否 “asc”表示喜马拉雅正序，”desc”表示喜马拉雅倒序，”time_asc”表示时间升序，”time_desc”表示时间降序，默认为”asc”
         * @param page           返回第几页，必须大于等于1，不填默认为1
         * @param pageSize       每页多少条，默认20，最多不超过200
         */
        void getTracks(long album_id,String sort, int page, int pageSize);

    }
}
