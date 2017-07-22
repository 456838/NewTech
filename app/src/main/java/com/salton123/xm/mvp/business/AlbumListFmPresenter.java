package com.salton123.xm.mvp.business;

import com.salton123.mvp.presenter.RxPresenter;
import com.ximalaya.ting.android.opensdk.constants.DTransferConstants;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.IDataCallBack;
import com.ximalaya.ting.android.opensdk.model.album.AlbumList;

import java.util.HashMap;
import java.util.Map;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/19 10:34
 * Time: 10:34
 * Description:
 */
public class AlbumListFmPresenter extends RxPresenter<AlbumListFmContract.View> implements AlbumListFmContract.Presenter {

    @Override
    public void getAlbumList(String tagName, int categoryId, String calc_dimension, int page, int pageSize) {
        Map<String, String> map = new HashMap<String, String>();
        map.put(DTransferConstants.CATEGORY_ID, categoryId+"");
        map.put(DTransferConstants.TAG_NAME, tagName);
        map.put(DTransferConstants.CALC_DIMENSION, calc_dimension);
        map.put(DTransferConstants.PAGE, page+"");
        map.put(DTransferConstants.PAGE_SIZE, pageSize+"");
        CommonRequest.getAlbumList(map, new IDataCallBack<AlbumList>() {
            @Override
            public void onSuccess(AlbumList albumList) {
                mView.showAlbum(albumList);
            }

            @Override
            public void onError(int i, String s) {
                mView.onError(i, s);
            }
        });
    }
}
