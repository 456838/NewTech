package com.salton123.xm;

import com.salton123.base.ApplicationBase;
import com.salton123.util.FileUtils;
import com.salton123.util.log.MLog;
import com.ximalaya.ting.android.opensdk.util.BaseUtil;
import com.ximalaya.ting.android.opensdk.util.Logger;
import com.ximalaya.ting.android.sdkdownloader.XmDownloadManager;
import com.ximalaya.ting.android.sdkdownloader.http.RequestParams;
import com.ximalaya.ting.android.sdkdownloader.http.app.RequestTracker;
import com.ximalaya.ting.android.sdkdownloader.http.request.UriRequest;

import java.io.File;

import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.helper.ExceptionHandler;
import timber.log.Timber;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/19 14:35
 * Time: 14:35
 * Description:
 */
public class XmApplication extends ApplicationBase {
    public String TAG ="XmApplication";
    @Override
    public void onCreate() {
        super.onCreate();
        // 栈视图功能，大大降低Fragment的开发难度，建议在Application里初始化
        Fragmentation.builder()
                // 设置 栈视图 模式为 悬浮球模式   SHAKE: 摇一摇唤出   NONE：隐藏
                .stackViewMode(Fragmentation.BUBBLE)
                // ture时，遇到异常："Can not perform this action after onSaveInstanceState!"时，会抛出
                // false时，不会抛出，会捕获，可以在handleException()里监听到
                .debug(BuildConfig.DEBUG)
                // 线上环境时，可能会遇到上述异常，此时debug=false，不会抛出该异常（避免crash），会捕获
                // 建议在回调处上传至我们的Crash检测服务器
                .handleException(new ExceptionHandler() {
                    @Override
                    public void onException(Exception e) {
//                         以Bugtags为例子: 手动把捕获到的 Exception 传到 Bugtags 后台。
//                         Bugtags.sendException(e);
                    }
                })
                .install();
        Timber.plant(new Timber.DebugTree());

    }

    public void initXm(){
        MLog.initialize(XmConfig.XM_FILE_PATH+"log");
        String filePath =XmConfig.XM_FILE_PATH+"mp3";
        if(!FileUtils.isFolderExist(filePath)){
            boolean ret =  new File(filePath).mkdirs();
//            if (!ret) return;
//            new File(filePath).getParent().
        }
        System.out.println("地址是" + filePath);
        if (BaseUtil.isMainProcess(this)) {
            XmDownloadManager.Builder(this)
                    .maxDownloadThread(1)            // 最大的下载个数 默认为1 最大为3
                    .maxSpaceSize(Long.MAX_VALUE)    // 设置下载文件占用磁盘空间最大值，单位字节。不设置没有限制
                    .connectionTimeOut(15000)        // 下载时连接超时的时间 ,单位毫秒 默认 30000
                    .readTimeOut(15000)                // 下载时读取的超时时间 ,单位毫秒 默认 30000
                    .fifo(false)                    // 等待队列的是否优先执行先加入的任务. false表示后添加的先执行(不会改变当前正在下载的音频的状态) 默认为true
                    .maxRetryCount(3)                // 出错时重试的次数 默认2次
                    .progressCallBackMaxTimeSpan(1000)//  进度条progress 更新的频率 默认是800
                    .requestTracker(requestTracker)    // 日志 可以打印下载信息
                    .savePath(filePath)    // 保存的地址 会检查这个地址是否有效
                    .create();
        }
    }

    private RequestTracker requestTracker = new RequestTracker() {
        @Override
        public void onWaiting(RequestParams params) {
            Logger.log("TingApplication : onWaiting " + params);
            MLog.info(TAG,"onWaiting,RequestParams:"+params);
        }

        @Override
        public void onStart(RequestParams params) {
            Logger.log("TingApplication : onStart " + params);
            MLog.info(TAG,"onStart,RequestParams:"+params);
        }

        @Override
        public void onRequestCreated(UriRequest request) {
            Logger.log("TingApplication : onRequestCreated " + request);
            MLog.info(TAG,"onStart,UriRequest:"+request);
        }

        @Override
        public void onSuccess(UriRequest request, Object result) {
            Logger.log("TingApplication : onSuccess " + request + "   result = " + result);
            MLog.info(TAG,"onSuccess,UriRequest:"+request+",Object:"+result);
        }

        @Override
        public void onRemoved(UriRequest request) {
            Logger.log("TingApplication : onRemoved " + request);
            MLog.info(TAG,"onRemoved,UriRequest:"+request);
        }

        @Override
        public void onCancelled(UriRequest request) {
            Logger.log("TingApplication : onCanclelled " + request);
            MLog.info(TAG,"onCancelled,UriRequest:"+request);
        }

        @Override
        public void onError(UriRequest request, Throwable ex, boolean isCallbackError) {
            Logger.log("TingApplication : onError " + request + "   ex = " + ex + "   isCallbackError = " + isCallbackError);
            MLog.info(TAG,"onCancelled,UriRequest:"+request+",Throwable:"+ex+",isCallbackError"+isCallbackError);
        }

        @Override
        public void onFinished(UriRequest request) {
            Logger.log("TingApplication : onFinished " + request);
            MLog.info(TAG,"onFinished,UriRequest:"+request);
        }
    };
}
