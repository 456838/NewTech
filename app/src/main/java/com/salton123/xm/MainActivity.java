package com.salton123.xm;

import android.os.Bundle;

import com.salton123.base.BaseSupportActivity;
import com.salton123.base.BaseSupportFragment;
import com.salton123.xm.fm.PlayTypeComponent;


/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/19 18:15
 * Time: 18:15
 * Description:
 */
public class MainActivity extends BaseSupportActivity {



    @Override
    public int GetLayout() {
        return R.layout.fm_container;
    }

    @Override
    public void InitVariable(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            loadRootFragment(R.id.fl_container, BaseSupportFragment.newInstance(PlayTypeComponent.class));
        }
    }

    @Override
    public void InitViewAndData() {

    }


    @Override
    public void InitListener() {
        checkPermission();
    }

    private void checkPermission() {
//        RxPermissions rxPermissions = new RxPermissions(this);
//        rxPermissions.setLogging(true);
//        rxPermissions.ensureEach(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
//                .subscribe(new Subscriber<Permission>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(Permission permission) {
//                        if (permission.name.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE) && permission.granted) {
//                            ApplicationBase.<XmApplication>getInstance().initXm();
//                        }
//                    }
//                });
    }

}
