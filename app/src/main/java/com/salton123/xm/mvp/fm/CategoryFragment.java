package com.salton123.xm.mvp.fm;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.google.gson.GsonBuilder;
import com.salton123.base.BaseSupportFragment;
import com.salton123.util.LogUtils;
import com.salton123.xm.R;
import com.salton123.xm.model.ApiMethod;
import com.ximalaya.ting.android.opensdk.model.tag.TagList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * User: newSalton@outlook.com
 * Date: 2017/8/10 21:28
 * ModifyTime: 21:28
 * Description:
 */
public class CategoryFragment extends BaseSupportFragment {
    @Override
    public int GetLayout() {
        return R.layout.fm_category;
    }

    @Override
    public void InitVariable(Bundle savedInstanceState) {
        LogUtils.e("bb","hello");
        Log.e("aa","hello");
        ApiMethod.test().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<TagList>() {
            @Override
            public void accept(TagList tagList) throws Exception {
                String jsonStr = new GsonBuilder().setPrettyPrinting().create().toJson(tagList);
                Log.e("aa","accept："+jsonStr);
                ((WebView)f(R.id.webView)).loadData(jsonStr,"json","utf-8");
            }
        });
    }

    @Override
    public void InitViewAndData() {

    }

    @Override
    public void InitListener() {

    }
}
