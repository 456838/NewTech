package com.salton123.xm.mvp.business;

import android.support.v4.app.Fragment;
import android.util.Pair;

import com.google.gson.GsonBuilder;
import com.salton123.base.BaseSupportFragment;
import com.salton123.mvp.presenter.RxPresenter;
import com.salton123.mvp.util.RxUtil;
import com.salton123.util.log.MLog;
import com.salton123.xm.model.ApiMethod;
import com.salton123.xm.mvp.fm.TracksMultiTypeFragment;
import com.salton123.xm.mvp.fm.headerType.AlbumListFragment;
import com.salton123.xm.mvp.fm.headerType.SearchedTracksFragment;
import com.ximalaya.ting.android.opensdk.model.album.AlbumList;
import com.ximalaya.ting.android.opensdk.model.track.SearchTrackList;
import com.ximalaya.ting.android.opensdk.model.track.TrackList;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * User: newSalton@outlook.com
 * Date: 2017/9/8 17:29
 * ModifyTime: 17:29
 * Description:
 */
public class OneToNPresenter extends RxPresenter<OneToNContract.IView> implements OneToNContract.Presenter {
    public static final String TAG = "OneToNPresenter";

    @Override
    public void getProvinces() {

        if (mView instanceof OneToNContract.AlbumListIView) {
            mView.onProvinces();
        }

    }

    @Override
    public void getRadios() {

    }

    @Override
    public void getSchedules() {

    }

    @Override
    public void getAlbumList(String tagName, int categoryId, int calc_dimension, int page, int pageSize) {
//        Map<String, String> map = new HashMap<String, String>();
//        map.put(DTransferConstants.CATEGORY_ID, categoryId+"");
//        map.put(DTransferConstants.TAG_NAME, tagName);
//        map.put(DTransferConstants.CALC_DIMENSION, calc_dimension+"");
//        map.put(DTransferConstants.PAGE, page+"");
//        map.put(DTransferConstants.PAGE_SIZE, pageSize+"");
//        CommonRequest.getAlbumList(map, new IDataCallBack<AlbumList>() {
//            @Override
//            public void onSuccess(AlbumList albumList) {
////                MLog.debug(TAG,"[getAlbumList] onSuccess albumList="+albumList);
//                if (mView instanceof OneToNContract.AlbumListIView) {
//                    ((OneToNContract.AlbumListIView) mView).showAlbum(albumList);
//                }
//
//            }
//
//            @Override
//            public void onError(int i, String s) {
//                MLog.error(TAG,"[getAlbumList] onError code="+i+",msg="+s);
//                ((OneToNContract.AlbumListIView) mView).onShowAlbumError(i, s);
//            }
//        });

        ApiMethod.getAlbumList(categoryId, tagName, calc_dimension, page, pageSize).compose(RxUtil.<AlbumList>rxSchedulerHelper()).subscribe(new Consumer<AlbumList>() {
            @Override
            public void accept(AlbumList albumList) throws Exception {
                MLog.info(TAG, new GsonBuilder().setPrettyPrinting().create().toJson(albumList));
                if (mView instanceof OneToNContract.AlbumListIView) {
                    ((OneToNContract.AlbumListIView) mView).showAlbum(albumList);
                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
                ((OneToNContract.AlbumListIView) mView).onShowAlbumError(0, throwable.getMessage());
            }
        });
    }

    @Override
    public void getIndexFmData() {
        List<Pair<Fragment, String>> mData = new ArrayList<>();
        mData.add(new Pair<Fragment, String>(AlbumListFragment.newInstance(AlbumListFragment.class, "郭德纲"), "郭德纲"));
        mData.add(new Pair<Fragment, String>(BaseSupportFragment.newInstance(SearchedTracksFragment.class, "岳云鹏"), "岳云鹏"));
        mData.add(new Pair<Fragment, String>(AlbumListFragment.newInstance(AlbumListFragment.class, "高晓松"), "高晓松"));
        mData.add(new Pair<Fragment, String>(AlbumListFragment.newInstance(AlbumListFragment.class, "吴晓波"), "吴晓波"));
        mData.add(new Pair<Fragment, String>(TracksMultiTypeFragment.newInstance(TracksMultiTypeFragment.class,203355 ), "段子来了"));
        if (mView instanceof OneToNContract.IndexFmIView) {
            ((OneToNContract.IndexFmIView) mView).onIndexFmData(mData);
        }
    }

    @Override
    public void getSearchedTracks(String keyword, int categoryId, int calc_dimension, int page, int pageSize) {
        MLog.info(TAG, "keyword=" + keyword + ",categoryId=" + categoryId + ",calc_dimension=" + calc_dimension + ",page=" + page + ",pageSize=" + pageSize);
        ApiMethod.getSearchedTracks(keyword, calc_dimension, categoryId, page, pageSize).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<SearchTrackList>() {
            @Override
            public void accept(SearchTrackList searchTrackList) throws Exception {
                if (mView instanceof OneToNContract.SearchedTracksFmIView) {
                    ((OneToNContract.SearchedTracksFmIView) mView).onSearchedTracksData(searchTrackList);
                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if (mView instanceof OneToNContract.SearchedTracksFmIView) {
                    ((OneToNContract.SearchedTracksFmIView) mView).onSearchedTracksError(0, throwable.getMessage());
                }
            }
        });
    }

    @Override
    public void getTracks(long album_id, String sort, int page, int pageSize) {
        ApiMethod.getTracks(album_id + "", sort, page, pageSize).compose(RxUtil.<TrackList>rxSchedulerHelper()).subscribe(new Consumer<TrackList>() {
            @Override
            public void accept(TrackList trackList) throws Exception {
                if (mView instanceof OneToNContract.TracksFmIView) {
                    ((OneToNContract.TracksFmIView) mView).showTracks(trackList);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
                if (mView instanceof OneToNContract.TracksFmIView) {
                    ((OneToNContract.TracksFmIView) mView).onShowTracksError(0, throwable.getMessage());
                }
            }
        });
    }

}
