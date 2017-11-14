package com.salton123.xm;

import android.app.Activity;
import android.app.Application;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.IntRange;

import com.salton123.util.log.MLog;
import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.player.XmPlayerManager;
import com.ximalaya.ting.android.opensdk.player.appnotification.XmNotificationCreater;
import com.ximalaya.ting.android.opensdk.util.BaseUtil;
import com.ximalaya.ting.android.sdkdownloader.XmDownloadManager;
import com.ximalaya.ting.android.sdkdownloader.http.app.RequestTracker;

/**
 * User: newSalton@outlook.com
 * Date: 2017/9/9 19:52
 * ModifyTime: 19:52
 * Description: 喜马拉雅初始化器
 */
public class XmlyInitializer {
    private static final String TAG = "XmlyInitializer";
    private static XmlyInitializer sXmlyInitializer = null;
    private Application sApplication;
    private XmDownloadManager.Builder mBuilder;
    private XmDownloadManager xmlyDownloadManager;
    private CommonRequest mCommonRequest;

    /**
     * 单例模式私有化实例
     */
    private XmlyInitializer() {

    }

    /**
     * double check lock
     *
     * @return 实例
     */
    public static XmlyInitializer getInstance() {
        if (sXmlyInitializer == null) {
            synchronized (XmlyInitializer.class) {
                if (sXmlyInitializer == null) {
                    sXmlyInitializer = new XmlyInitializer();
                }
            }
        }
        return sXmlyInitializer;
    }

    /**
     * 初始化
     *
     * @param application
     * @return
     */
    public XmlyInitializer init(Application application) {
        if (application == null) throw new ExceptionInInitializerError("初始化喜马拉雅服务失败！");
        else {
            sApplication = application;
            mBuilder = XmDownloadManager.Builder(application);
            setupRequest(application);
        }
        return this;
    }

    /**
     * 设置请求
     * @param context
     */
    private void setupRequest(Context context){
        mCommonRequest = CommonRequest.getInstanse();
        mCommonRequest.init(sApplication, XmConfig.APP_SECRET);
    }

    /**
     * 指定下载线程
     *
     * @param maxDownloadThreadCount 线程数1-3
     * @return
     */
    public XmlyInitializer maxDownloadThread(@IntRange(from = 1L, to = 3L) int maxDownloadThreadCount) {
        if (BuildConfig.DEBUG) MLog.info(TAG,"maxDownloadThread="+maxDownloadThreadCount);
        mBuilder.maxDownloadThread(maxDownloadThreadCount);
        return this;
    }

    /**
     * 设置下载文件占用磁盘空间最大值，单位字节。不设置没有限制
     *
     * @param maxSpaceSize
     * @return
     */
    public XmlyInitializer maxSpaceSize(long maxSpaceSize) {
        if (BuildConfig.DEBUG) MLog.info(TAG,"maxSpaceSize="+maxSpaceSize);
        mBuilder.maxSpaceSize(maxSpaceSize);
        return this;
    }

    /**
     * 下载时连接超时的时间 ,单位毫秒 默认 30000
     *
     * @param connectionTimeOut
     * @return
     */
    public XmlyInitializer connectionTimeOut(int connectionTimeOut) {
        if (BuildConfig.DEBUG) MLog.info(TAG,"connectionTimeOut="+connectionTimeOut);
        mBuilder.connectionTimeOut(connectionTimeOut);
        return this;
    }

    /**
     * 保存的地址 会检查这个地址是否有效
     *
     * @param savePath
     * @return
     */
    public XmlyInitializer savePath(String savePath) {
        if (BuildConfig.DEBUG) MLog.info(TAG,"savePath="+savePath);
        mBuilder.savePath(savePath);
        return this;
    }

    /**
     * // 下载时读取的超时时间 ,单位毫秒 默认 30000
     *
     * @param readTimeOut
     * @return
     */
    public XmlyInitializer readTimeOut(int readTimeOut) {
        if (BuildConfig.DEBUG) MLog.info(TAG,"readTimeOut="+readTimeOut);
        mBuilder.readTimeOut(readTimeOut);
        return this;
    }

    /**
     * 出错时重试的次数 默认2次
     *
     * @param maxRetryCount
     * @return
     */
    public XmlyInitializer maxRetryCount(int maxRetryCount) {
        if (BuildConfig.DEBUG) MLog.info(TAG,"maxRetryCount="+maxRetryCount);
        mBuilder.maxRetryCount(maxRetryCount);
        return this;
    }

    /**
     * 日志 可以打印下载信息
     *
     * @param requestTracker
     * @return
     */
    public XmlyInitializer requestTracker(RequestTracker requestTracker) {
        if (BuildConfig.DEBUG) MLog.info(TAG,"requestTracker="+requestTracker);
        mBuilder.requestTracker(requestTracker);
        return this;
    }

    /**
     * 进度条progress 更新的频率 默认是800
     *
     * @param progressCallBackMaxTimeSpan
     * @return
     */
    public XmlyInitializer progressCallBackMaxTimeSpan(int progressCallBackMaxTimeSpan) {
        if (BuildConfig.DEBUG) MLog.info(TAG,"progressCallBackMaxTimeSpan="+progressCallBackMaxTimeSpan);
        mBuilder.progressCallBackMaxTimeSpan(progressCallBackMaxTimeSpan);
        return this;
    }

    /**
     * 等待队列的是否优先执行先加入的任务. false表示后添加的先执行(不会改变当前正在下载的音频的状态) 默认为true
     *
     * @param fifo
     * @return
     */
    public XmlyInitializer fifo(boolean fifo) {
        if (BuildConfig.DEBUG) MLog.info(TAG,"fifo="+fifo);
        mBuilder.fifo(fifo);
        return this;
    }

    public XmlyInitializer create() {
        if (BuildConfig.DEBUG) MLog.info(TAG,"create XmDownloadManager");
        xmlyDownloadManager = mBuilder.create();
        return this;
    }

    public XmDownloadManager getDownloader(){
        if (xmlyDownloadManager==null) throw new ExceptionInInitializerError("请先调用create方法再获取下载管理器！");
        return xmlyDownloadManager;
    }

    public static final String ACTION_NAME = "com.salton123.xm.Action_Close";

    /**
     * 注册广播
     * @param clzT
     * @param <T>
     */
    public <T extends BroadcastReceiver,F extends Activity>void registerReceiver(Class<T> clzT,Class<F> clzF){
        if(BaseUtil.getCurProcessName(sApplication).contains(":player")) {
            XmNotificationCreater instanse = XmNotificationCreater.getInstanse(sApplication);
            instanse.setNextPendingIntent(null);
            instanse.setPrePendingIntent(null);
            instanse.setStartOrPausePendingIntent(null);
            Intent intent = new Intent(ACTION_NAME);
            intent.setClass(sApplication, clzT);
            PendingIntent broadcast = PendingIntent.getBroadcast(sApplication, 0, intent, 0);
            instanse.setClosePendingIntent(broadcast);
            instanse.initNotification(sApplication,clzF);
        }
    }

    public static void unInitialize(){
        XmPlayerManager.release();
        CommonRequest.release();
    }


}
