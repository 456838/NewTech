package com.salton123.xm.model;

import com.salton123.util.log.MLog;
import com.salton123.xm.model.ErrorEntity;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.AlbumList;
import com.ximalaya.ting.android.opensdk.model.album.SearchAlbumList;
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
import io.reactivex.annotations.NonNull;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/21 16:18
 * Time: 16:18
 * Description:
 */
public class ApiMethod {
    private static final String TAG = "ApiMethod";

    /**
     * @return 获取喜马拉雅内容分类
     */
    public static Observable<CategoryList> getCategories() {
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
                        emitter.onError(new Exception(new ErrorEntity(code,message).toString()));
                    }
                });
            }
        });
    }

    /**
     * 获取专辑标签或者声音标签
     * category_id 	Int 	是 	分类ID，指定分类，为0时表示热门分类
     * type 	Int 	是 	指定查询的是专辑标签还是声音标签，0-专辑标签，1-声音标签
     * @param cagetoryId
     * @param type
     * @return
     */
    public static Observable<TagList> getTags(final long cagetoryId , final int type) {
        return Observable.create(new ObservableOnSubscribe<TagList>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<TagList> emitter) throws Exception {
                Map<String, String> map = new HashMap<String, String>();
                map.put(DTransferConstants.CATEGORY_ID, cagetoryId + "");
                map.put(DTransferConstants.TYPE, type + "");
                CommonRequest.getTags(map, new IDataCallBack<TagList>() {
                    @Override
                    public void onSuccess(TagList tagList) {
                        emitter.onNext(tagList);
                    }

                    @Override
                    public void onError(int code, String message) {
                        emitter.onError(new Exception(new ErrorEntity(code,message).toString()));
                    }
                });
            }
        });
    }

    /**
     * 根据分类和标签获取某个分类某个标签下的专辑列表（最火/最新/最多播放）
     * @param category_id 分类ID，指定分类，为0时表示热门分类
     * @param tag_name 分类下对应的专辑标签，不填则为热门分类
     * @param calc_dimension 计算维度，现支持最火（1），最新（2），经典或播放最多（3）
     * @param page 返回第几页，必须大于等于1，不填默认为1
     * @param count 每页多少条，默认20，最多不超过200
     * @return
     */
    public static Observable<AlbumList> getAlbumList(final int category_id, final String tag_name
            , final int calc_dimension, final int page , final int count) {
        return Observable.create(new ObservableOnSubscribe<AlbumList>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<AlbumList> emitter) throws Exception {
                Map<String, String> map = new HashMap<String, String>();
                map.put(DTransferConstants.CATEGORY_ID, category_id+"");
                map.put(DTransferConstants.TAG_NAME, tag_name);
                map.put(DTransferConstants.CALC_DIMENSION, calc_dimension+"");
                map.put(DTransferConstants.PAGE, page+"");
                map.put(DTransferConstants.PAGE_SIZE, count+"");
                CommonRequest.getAlbumList(map, new IDataCallBack<AlbumList>() {
                    @Override
                    public void onSuccess(AlbumList albumList) {
                        MLog.debug(TAG,"[getAlbumList] onSuccess albumList="+albumList);
                        emitter.onNext(albumList);

                    }

                    @Override
                    public void onError(int code, String message) {
                        MLog.error(TAG,"[getAlbumList] onError code="+code+",msg="+message);
                        emitter.onError(new Exception(new ErrorEntity(code,message).toString()));
                    }
                });
            }
        });
    }

    /**
     * 专辑浏览，根据专辑ID获取专辑下的声音列表
     * album_id 	Int 	是 	专辑ID
     * sort 	String 	否 	sort String 否 “asc”表示喜马拉雅正序，”desc”表示喜马拉雅倒序，”time_asc”表示时间升序，”time_desc”表示时间降序，默认为”asc”
     * page 	Int 	否 	当前第几页，不填默认为1
     * count 	Int 	否 	每页多少条，默认20，最多不超过200
     * @param albumId
     * @param sort
     * @param page
     * @return
     */
    public static Observable<TrackList> getTracks(final String albumId, final String sort, final int page,final int pageSize) {
        return Observable.create(new ObservableOnSubscribe<TrackList>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<TrackList> emitter) throws Exception {
                Map<String, String> map = new HashMap<String, String>();
                map.put(DTransferConstants.ALBUM_ID, albumId);
                map.put(DTransferConstants.SORT, sort);
                map.put(DTransferConstants.PAGE,page + "");
                map.put(DTransferConstants.PAGE_SIZE, pageSize+"");
                CommonRequest.getTracks(map, new IDataCallBack<TrackList>() {

                    @Override
                    public void onSuccess(TrackList trackList) {
                        emitter.onNext(trackList);
                    }

                    @Override
                    public void onError(int code, String message) {
                        emitter.onError(new Exception(new ErrorEntity(code,message).toString()));
                    }
                });
            }

        });
    }

    /**
     * 搜索专辑
     * q 	String 	是 	搜索关键词
     * category_id 	Int 	否 	分类ID，不填或者为0检索全库
     * calc_dimension 	Int 	否 	排序条件：2-最新，3-最多播放，4-最相关（默认）
     * page 	Int 	否 	返回第几页，必须大于等于1，不填默认为1
     * count 	Int 	否 	每页多少条，默认20，最多不超过200
     *
     * @param keyword 搜索关键词
     * @param category  分类ID，不填或者为0检索全库
     * @param page 返回第几页，必须大于等于1，不填默认为1
     * @return
     */
    public static Observable<SearchAlbumList> getSearchedAlbums(final String keyword
            , final String category, final int calcDimension, final int page, final int count){
        return Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter emitter) throws Exception {
                Map<String, String> map = new HashMap<String, String>();
                map.put(DTransferConstants.SEARCH_KEY, keyword);
                map.put(DTransferConstants.CATEGORY_ID, category);
                map.put(DTransferConstants.CALC_DIMENSION, calcDimension+"");
                map.put(DTransferConstants.PAGE, page+"");
                map.put(DTransferConstants.PAGE_SIZE, count+"");
                CommonRequest.getSearchedAlbums(map, new IDataCallBack<SearchAlbumList>(){

                    @Override
                    public void onSuccess(SearchAlbumList searchAlbumList) {

                        emitter.onNext(searchAlbumList);
                    }

                    @Override
                    public void onError(int code, String message) {
                        emitter.onError(new Exception(new ErrorEntity(code,message).toString()));
                    }
                });
            }
        });
    }


    /**
     *
     * 搜索声音
     * @param keyword 搜索关键词
     * @param  calc_demension 排序条件：2-最新，3-最多播放，4-最相关（默认）
     * @param category_id  分类ID，不填或者为0检索全库
     * @param page 返回第几页，必须大于等于1，不填默认为1
     * @param  count 每页多少条，默认20，最多不超过200
     * @return
     */
    public static Observable<SearchTrackList> getSearchedTracks(final String keyword, final int calc_demension, final int category_id, final int page, final int count){
        return Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter emitter) throws Exception {
                Map<String, String> map = new HashMap<String, String>();
                map.put(DTransferConstants.SEARCH_KEY, keyword);
                map.put(DTransferConstants.CALC_DIMENSION, calc_demension+"");
                map.put(DTransferConstants.CATEGORY_ID, category_id+"");
                map.put(DTransferConstants.PAGE, page + "");
                map.put(DTransferConstants.PAGE_SIZE, count + "");
                CommonRequest.getSearchedTracks(map, new IDataCallBack<SearchTrackList>() {
                    @Override
                    public void onSuccess(SearchTrackList searchTrackList) {
                        MLog.debug(TAG,"[getSearchedTracks] onSuccess searchTrackList="+searchTrackList);
                        emitter.onNext(searchTrackList);
                    }

                    @Override
                    public void onError(int code, String message) {
                        MLog.info(TAG,"[getSearchedTracks] onError code="+code+",msg= "+message);
                        emitter.onError(new Exception(new ErrorEntity(code,message).toString()));
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
                    public void onError(int code, String message) {
                        emitter.onError(new Exception(new ErrorEntity(code,message).toString()));
                    }
                });
            }
        });
    }
}
