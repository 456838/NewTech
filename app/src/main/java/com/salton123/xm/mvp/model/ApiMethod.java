package com.salton123.xm.mvp.model;

import com.google.gson.GsonBuilder;
import com.salton123.callback.HttpResponseHandler;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.AlbumList;
import com.ximalaya.ting.android.opensdk.model.album.BatchAlbumList;
import com.ximalaya.ting.android.opensdk.model.album.SearchAlbumList;
import com.ximalaya.ting.android.opensdk.model.category.Category;
import com.ximalaya.ting.android.opensdk.model.category.CategoryList;
import com.ximalaya.ting.android.opensdk.model.live.radio.RadioList;
import com.ximalaya.ting.android.opensdk.model.tag.TagList;
import com.ximalaya.ting.android.opensdk.model.track.SearchTrackList;
import com.ximalaya.ting.android.opensdk.model.track.TrackList;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

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

    public static Observable<CategoryList> getCategory() {
        return Observable.create(new ObservableOnSubscribe<CategoryList>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<CategoryList> emitter) throws Exception {
                Map<String, String> map = new HashMap<String, String>();        //不需要参数
                CommonRequest.getCategories(map, new IDataCallBack<CategoryList>() {
                    @Override
                    public void onSuccess(CategoryList categoryList) {
                        emitter.onNext(categoryList);
                    }

                    @Override
                    public void onError(int code, String message) {

                        emitter.onError(new Exception(message));
                    }
                });
            }
        });
    }

    public static Observable<TagList> getTag(final long cagetoryId) {
        return Observable.create(new ObservableOnSubscribe<TagList>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<TagList> emitter) throws Exception {
                Map<String, String> map = new HashMap<String, String>();
                map.put(DTransferConstants.CATEGORY_ID, cagetoryId + "");
                map.put(DTransferConstants.TYPE, 0 + "");
                CommonRequest.getTags(map, new IDataCallBack<TagList>() {

                    @Override
                    public void onSuccess(TagList tagList) {
                        emitter.onNext(tagList);
                    }

                    @Override
                    public void onError(int i, String message) {

                        emitter.onError(new Exception(message));
                    }
                });
            }
        });
    }

    public static Observable<Category> getFrom(final CategoryList categoryList) {
        if (categoryList==null ||categoryList.getCategories()==null)return null ;
        return Observable.fromArray(categoryList.getCategories().toArray(new Category[categoryList.getCategories().size()]));
    }

    public static Observable<TagList> test() {
        return getCategory().flatMap(new Function<CategoryList, ObservableSource<Category>>() {
            @Override
            public ObservableSource<Category> apply(@NonNull CategoryList categoryList) throws Exception {
                return getFrom(categoryList);
            }
        }).flatMap(new Function<Category, ObservableSource<TagList>>() {
            @Override
            public ObservableSource<TagList> apply(@NonNull Category category) throws Exception {
                return getTag(category.getId());
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
        return Observable.create(new ObservableOnSubscribe<AlbumList>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<AlbumList> emitter) throws Exception {
                Map<String, String> map = new HashMap<String, String>();
                map.put(DTransferConstants.CATEGORY_ID, "12");
                map.put(DTransferConstants.TAG_NAME, "郭德纲");
                map.put(DTransferConstants.CALC_DIMENSION, "2");
                CommonRequest.getAlbumList(map, new IDataCallBack<AlbumList>() {
                    @Override
                    public void onSuccess(AlbumList albumList) {
                        if (albumList != null) {
                            emitter.onNext(albumList);
                        }
                    }

                    @Override
                    public void onError(int i, String s) {
                        emitter.onError(new Exception(s));
                    }
                });
            }
        });
    }

    public Observable<TrackList> getObservableTracks(final AlbumList albumList) {
        return Observable.create(new ObservableOnSubscribe<TrackList>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<TrackList> emitter) throws Exception {
                Map<String, String> map = new HashMap<String, String>();
                map.put(DTransferConstants.ALBUM_ID, albumList.getAlbums().get(0).getId() + "");
                map.put(DTransferConstants.SORT, "asc");
                map.put(DTransferConstants.PAGE, 1 + "");
                CommonRequest.getTracks(map, new IDataCallBack<TrackList>() {

                    @Override
                    public void onSuccess(TrackList trackList) {
                        emitter.onNext(trackList);
                    }

                    @Override
                    public void onError(int i, String s) {
                        emitter.onError(new Exception(s));
                    }
                });
            }

        });
    }

    /**
     * 搜索专辑
     * @param keyword 搜索关键词
     * @param category  分类ID，不填或者为0检索全库
     * @param page 返回第几页，必须大于等于1，不填默认为1
     * @return
     */
    public static Observable<SearchAlbumList> getSearchedAlbums(final String keyword, final String category, final int page){
        return Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter emitter) throws Exception {
                Map<String, String> map = new HashMap<String, String>();
                map.put(DTransferConstants.SEARCH_KEY, keyword);
                map.put(DTransferConstants.CATEGORY_ID, category);
                map.put(DTransferConstants.PAGE, page+"");
                CommonRequest.getSearchedAlbums(map, new IDataCallBack<SearchAlbumList>(){

                    @Override
                    public void onSuccess(SearchAlbumList searchAlbumList) {

                        emitter.onNext(searchAlbumList);
                    }

                    @Override
                    public void onError(int i, String s) {
                        emitter.onNext(new Exception(s));
                    }
                });
            }
        });
    }


    /**
     *
     * 搜索声音
     * @param keyword 搜索关键词
     * @param category  分类ID，不填或者为0检索全库
     * @param page 返回第几页，必须大于等于1，不填默认为1
     * @return
     */
    public static Observable<SearchTrackList> getSearchedTracks(final String keyword, final String category, final int page){
        return Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter emitter) throws Exception {
                Map<String, String> map = new HashMap<String, String>();
                map.put(DTransferConstants.SEARCH_KEY, keyword);
                map.put(DTransferConstants.CATEGORY_ID, category);
                map.put(DTransferConstants.PAGE, page + "");
                CommonRequest.getSearchedTracks(map, new IDataCallBack<SearchTrackList>() {
                    @Override
                    public void onSuccess(SearchTrackList searchTrackList) {
                        emitter.onNext(searchTrackList);
                    }

                    @Override
                    public void onError(int i, String s) {
                        emitter.onNext(new Exception(s));
                    }
                });
            }
        });
    }

    /**
     *
     * 搜索声音
     * @param keyword 搜索关键词
     * @param category  分类ID，不填或者为0检索全库
     * @param page 返回第几页，必须大于等于1，不填默认为1
     * @return
     */
    public static Observable<RadioList> getSearchedRadios(final String keyword, final String category, final int page){
        return Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter emitter) throws Exception {
                Map<String, String> map = new HashMap<String, String>();
                map.put(DTransferConstants.SEARCH_KEY, keyword);
                map.put(DTransferConstants.CATEGORY_ID, category);
                map.put(DTransferConstants.PAGE, page + "");
                CommonRequest.getSearchedRadios(map, new IDataCallBack<RadioList>() {
                    @Override
                    public void onSuccess(RadioList radioList) {
                        emitter.onNext(radioList);
                    }

                    @Override
                    public void onError(int i, String s) {
                        emitter.onNext(new Exception(s));
                    }
                });
            }
        });
    }

    public static Observable<MultiSearchBean> multiSearch(String keyword){
        return getSearchedAlbums(keyword,"0",1).zipWith(getSearchedRadios(keyword,"0",1), new BiFunction<SearchAlbumList, RadioList, MultiSearchBean>() {
            @Override
            public MultiSearchBean apply(@NonNull SearchAlbumList searchAlbumList, @NonNull RadioList radioList) throws Exception {
                MultiSearchBean bean = new MultiSearchBean();
                bean.radioList =radioList;
                bean.searchAlbumList=searchAlbumList ;
                return bean;
            }
        }).zipWith(getSearchedTracks("keyword", "0", 1), new BiFunction<MultiSearchBean, SearchTrackList, MultiSearchBean>() {
            @Override
            public MultiSearchBean apply(@NonNull MultiSearchBean multiSearchBean, @NonNull SearchTrackList searchTrackList) throws Exception {
                    multiSearchBean.searchTrackList=searchTrackList;
                return multiSearchBean;
            }
        });
    }









}
