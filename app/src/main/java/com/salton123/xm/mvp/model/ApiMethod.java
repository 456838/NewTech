package com.salton123.xm.mvp.model;

import com.google.gson.GsonBuilder;
import com.salton123.callback.HttpResponseHandler;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.AlbumList;
import com.ximalaya.ting.android.opensdk.model.album.BatchAlbumList;
import com.ximalaya.ting.android.opensdk.model.category.CategoryList;
import com.ximalaya.ting.android.opensdk.model.tag.TagList;
import com.ximalaya.ting.android.opensdk.model.track.TrackList;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/21 16:18
 * Time: 16:18
 * Description:
 */
public class ApiMethod {

    public void getBatch(final HttpResponseHandler<String> responseHandler) {
        String album_ids = "12,1010";
        Map<String, String> map = new HashMap<String, String>();
        map.put(DTransferConstants.ALBUM_IDS, album_ids);
        CommonRequest.getBatch(map, new IDataCallBack<BatchAlbumList>() {

            @Override
            public void onSuccess(BatchAlbumList batchAlbumList) {
                responseHandler.onSuccess(new GsonBuilder().setPrettyPrinting().create().toJson(batchAlbumList));
            }

            @Override
            public void onError(int i, String s) {
                responseHandler.onFailure(s);
            }
        });
    }

    public void getCategories(final HttpResponseHandler<String> responseHandler) {
        Map<String, String> map = new HashMap<String, String>();
        CommonRequest.getCategories(map, new IDataCallBack<CategoryList>() {
            @Override
            public void onSuccess(CategoryList object) {
                responseHandler.onSuccess(new GsonBuilder().setPrettyPrinting().create().toJson(object));
            }

            @Override
            public void onError(int code, String message) {
                responseHandler.onFailure(message);
            }
        });
    }

    //获取专辑标签或者声音标签
    public void getTags(final HttpResponseHandler<String> responseHandler) {

        Map<String, String> map = new HashMap<String, String>();
        map.put(DTransferConstants.CATEGORY_ID, 12 + "");
        map.put(DTransferConstants.TYPE, 0 + "");
        CommonRequest.getTags(map, new IDataCallBack<TagList>() {

            @Override
            public void onSuccess(TagList tagList) {
                responseHandler.onSuccess(new GsonBuilder().setPrettyPrinting().create().toJson(tagList));
            }

            @Override
            public void onError(int i, String s) {
                responseHandler.onFailure(s);
            }
        });
    }

    //根据分类和标签获取某个分类某个标签下的专辑列表（最火/最新/最多播放）
    public void getAlbumList(final HttpResponseHandler<String> responseHandler) {
        Map<String, String> map = new HashMap<String, String>();
        map.put(DTransferConstants.CATEGORY_ID, "12");
        map.put(DTransferConstants.TAG_NAME, "郭德纲");
        map.put(DTransferConstants.CALC_DIMENSION, "2");
        CommonRequest.getAlbumList(map, new IDataCallBack<AlbumList>() {
            @Override
            public void onSuccess(AlbumList albumList) {
                responseHandler.onSuccess(new GsonBuilder().setPrettyPrinting().create().toJson(albumList));
            }

            @Override
            public void onError(int i, String s) {
                responseHandler.onFailure(s);
            }
        });
    }

    //专辑浏览，根据专辑ID获取专辑下的声音列表
    public void getTracks(final HttpResponseHandler<String> responseHandler) {
        Map<String, String> map = new HashMap<String, String>();
        map.put(DTransferConstants.ALBUM_ID, 12 + "");
//        map.put(DTransferConstants.SORT, "asc");
        map.put(DTransferConstants.PAGE, 1 + "");
        CommonRequest.getTracks(map, new IDataCallBack<TrackList>() {

            @Override
            public void onSuccess(TrackList trackList) {
                responseHandler.onSuccess(new GsonBuilder().setPrettyPrinting().create().toJson(trackList));
            }

            @Override
            public void onError(int i, String s) {
                responseHandler.onFailure(s);
            }
        });
    }

    public Observable<AlbumList> getObservableAlbum() {
        return Observable.create(new Observable.OnSubscribe<AlbumList>() {
            @Override
            public void call(final Subscriber<? super AlbumList> subscriber) {
                Map<String, String> map = new HashMap<String, String>();
                map.put(DTransferConstants.CATEGORY_ID, "12");
                map.put(DTransferConstants.TAG_NAME, "郭德纲");
                map.put(DTransferConstants.CALC_DIMENSION, "2");
                CommonRequest.getAlbumList(map, new IDataCallBack<AlbumList>() {
                    @Override
                    public void onSuccess(AlbumList albumList) {
                        if (albumList != null) {
                            subscriber.onNext(albumList);
                        }
                    }

                    @Override
                    public void onError(int i, String s) {
                        subscriber.onError(new Exception(s));
                    }
                });
            }
        });
    }

    public Observable<TrackList> getObservableTracks(final AlbumList albumList) {
        return Observable.create(new Observable.OnSubscribe<TrackList>() {
            @Override
            public void call(final Subscriber<? super TrackList> subscriber) {
                Map<String, String> map = new HashMap<String, String>();
                map.put(DTransferConstants.ALBUM_ID, albumList.getAlbums().get(0).getId() + "");
                map.put(DTransferConstants.SORT, "asc");
                map.put(DTransferConstants.PAGE, 1 + "");
                CommonRequest.getTracks(map, new IDataCallBack<TrackList>() {

                    @Override
                    public void onSuccess(TrackList trackList) {
                        subscriber.onNext(trackList);
                    }

                    @Override
                    public void onError(int i, String s) {
                        subscriber.onError(new Exception(s));
                    }
                });
            }
        });
    }

    public void getAlbumListAndTracks(final HttpResponseHandler<String> responseHandler) {
        getObservableAlbum().flatMap(new Func1<AlbumList, Observable<?>>() {
            @Override
            public Observable<?> call(AlbumList albumList) {
                return getObservableTracks(albumList);
            }
        }).subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {

            }
        });
    }

}
