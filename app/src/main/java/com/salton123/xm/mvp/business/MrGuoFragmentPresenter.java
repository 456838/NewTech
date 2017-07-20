package com.salton123.xm.mvp.business;

import com.salton123.mvp.presenter.RxPresenter;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.track.SearchTrackList;

import java.util.HashMap;
import java.util.Map;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/19 10:34
 * Time: 10:34
 * Description:
 */
public class MrGuoFragmentPresenter extends RxPresenter<MrGuoFragmentContract.View> implements MrGuoFragmentContract.Presenter {

//    @Override
//    public void getData(String keyword, int categoryId, String calc_dimension, int page, int pageSize) {
//        Map<String, String> param = new HashMap<String, String>();
//        param.put(DTransferConstants.SEARCH_KEY, keyword);
//        param.put(DTransferConstants.CATEGORY_ID, "" + categoryId);
//        param.put(DTransferConstants.PAGE, "" + page);
//        param.put(DTransferConstants.PAGE_SIZE, "" + pageSize);
//        CommonRequest.getSearchedAlbums(param, new IDataCallBack<SearchAlbumList>() {
//            @Override
//            public void onSuccess(SearchAlbumList searchAlbumList) {
//                mView.showData(searchAlbumList);
//            }
//
//            @Override
//            public void onError(int i, String s) {
//                mView.onError(i, s);
//            }
//        });
//    }

    @Override
    public void getSearchedTracks(String keyword, int categoryId, String calc_dimension, int page, int pageSize) {
        Map<String, String> param = new HashMap<String, String>();
        param.put(DTransferConstants.SEARCH_KEY, keyword);
        param.put(DTransferConstants.CATEGORY_ID, "" + categoryId);
        param.put(DTransferConstants.PAGE, "" + page);
        param.put(DTransferConstants.PAGE_SIZE, "" + pageSize);
        CommonRequest.getSearchedTracks(param, new IDataCallBack<SearchTrackList>() {
            @Override
            public void onSuccess(SearchTrackList searchTrackList) {
                mView.showSearchedTracksData(searchTrackList);
            }

            @Override
            public void onError(int i, String s) {
                mView.onError(i, s);
            }
        });
    }
}
