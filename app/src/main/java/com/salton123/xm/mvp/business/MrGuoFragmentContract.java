package com.salton123.xm.mvp.business;

import com.salton123.mvp.presenter.BasePresenter;
import com.salton123.mvp.view.BaseView;
import com.ximalaya.ting.android.opensdk.model.album.SearchAlbumList;
import com.ximalaya.ting.android.opensdk.model.track.SearchTrackList;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/19 10:18
 * Time: 10:18
 * Description:
 */
public interface MrGuoFragmentContract {
    interface View extends BaseView {
        void showData(SearchAlbumList searchAlbumList);
        void showSearchedTracksData(SearchTrackList searchTrackList);

        void onError(int resCode, String errorMsg);
    }

    interface Presenter extends BasePresenter<View> {
//        /**
//         * @param keyword        搜索关键词
//         * @param categoryId     分类ID，不填或者为0检索全库
//         * @param calc_dimension 排序条件：2-最新，3-最多播放，4-最相关（默认）
//         * @param page           返回第几页，必须大于等于1，不填默认为1
//         * @param pageSize       每页多少条，默认20，最多不超过200
//         */
//        void getData(String keyword, int categoryId, String calc_dimension, int page, int pageSize);

        /**
         * @param keyword        搜索关键词
         * @param categoryId     分类ID，不填或者为0检索全库
         * @param calc_dimension 排序条件：2-最新，3-最多播放，4-最相关（默认）
         * @param page           返回第几页，必须大于等于1，不填默认为1
         * @param pageSize       每页多少条，默认20，最多不超过200
         */
        void getSearchedTracks(String keyword, int categoryId, String calc_dimension, int page, int pageSize);
    }
}
