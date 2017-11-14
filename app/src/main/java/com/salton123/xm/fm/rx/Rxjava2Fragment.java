package com.salton123.xm.fm.rx;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.salton123.base.BaseSupportFragment;
import com.salton123.xm.R;
import com.salton123.xm.fm.config.DownloadInfo;
import com.salton123.xm.fm.config.DownloadManager;
import com.salton123.xm.fm.config.DownloadObserver;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * User: newSalton@outlook.com
 * Date: 2017/8/9 10:17
 * ModifyTime: 10:17
 * Description:
 */
public class Rxjava2Fragment extends BaseSupportFragment {
    private TextView tv_progress, tv_download_unzip;

    @Override
    public int GetLayout() {
        return R.layout.fm_rxjava;
    }

    @Override
    public void InitVariable(Bundle savedInstanceState) {

    }

    @Override
    public void InitViewAndData() {
        tv_download_unzip = f(R.id.tv_download_unzip);
        tv_progress = f(R.id.tv_progress);

    }

    @Override
    public void InitListener() {
        tv_download_unzip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDownloadAndUnzip();
            }
        });
    }

    private void startDownloadAndUnzip() {
        DownloadManager.getInstance().download("https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk", new DownloadObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                super.onSubscribe(d);
            }

            @Override
            public void onNext(@NonNull DownloadInfo info) {
                super.onNext(info);
                System.out.println("total:"+info.totalSize+"->current:"+info.currentSize);
                tv_progress.setText(info.currentSize * 100 /info.totalSize+"%");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                super.onError(e);
            }

            @Override
            public void onComplete() {
                super.onComplete();
            }
        });
    }




}
