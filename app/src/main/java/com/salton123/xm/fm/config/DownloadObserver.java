package com.salton123.xm.fm.config;


import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * User: newSalton@outlook.com
 * Date: 2017/8/9 15:22
 * ModifyTime: 15:22
 * Description:
 */
public abstract class DownloadObserver implements Observer<DownloadInfo> {
    Disposable d ;
    DownloadInfo info ;
    @Override
    public void onSubscribe(@NonNull Disposable d) {
        this.d = d ;
    }

    @Override
    public void onNext(@NonNull DownloadInfo info) {
        this.info = info ;
    }

    @Override
    public void onError(@NonNull Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
