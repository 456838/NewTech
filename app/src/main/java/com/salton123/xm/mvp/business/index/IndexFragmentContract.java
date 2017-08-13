package com.salton123.xm.mvp.business.index;

import com.salton123.mvp.presenter.BasePresenter;
import com.salton123.mvp.view.BaseView;

import java.util.List;

/**
 * Created by salton on 2017/8/13.
 */

public class IndexFragmentContract  {
    public interface View extends BaseView {
        void setData(List<String> dataList);
        void setError(String msg);
    }

    interface Presenter extends BasePresenter<View> {
        public void getData();
    }

}
