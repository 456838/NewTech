package com.salton123.xm.mvp.business;

import com.salton123.mvp.presenter.BasePresenter;
import com.salton123.mvp.view.BaseView;
import com.ximalaya.ting.android.opensdk.model.album.AlbumList;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/19 10:18
 * Time: 10:18
 * Description:
 */
public interface AlbumListFmContract {
    interface View extends BaseView {

        void showAlbum(AlbumList list);

        void onError(int resCode, String errorMsg);
    }

    interface Presenter extends BasePresenter<View> {
        /**
         * @param tagName        分类下对应的专辑标签，不填则为热门分类
         * @param categoryId    分类ID，指定分类，为0时表示热门分类
         * @param calc_dimension 计算维度，现支持最火（1），最新（2），经典或播放最多（3）
         * @param page           返回第几页，必须大于等于1，不填默认为1
         * @param pageSize       每页多少条，默认20，最多不超过200
         */
        void getAlbumList(String tagName, int categoryId, String calc_dimension, int page, int pageSize);

    }
}
