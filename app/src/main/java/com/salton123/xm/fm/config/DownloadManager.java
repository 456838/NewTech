package com.salton123.xm.fm.config;

import android.support.annotation.NonNull;

import org.xutils.common.util.IOUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * User: newSalton@outlook.com
 * Date: 2017/8/9 15:15
 * ModifyTime: 15:15
 * Description:
 */
public class DownloadManager {

    private static final String PATH = "/sdcard/test/";
    //理解AtomicReference
    private static final AtomicReference<DownloadManager> INSTANCE = new AtomicReference<>();

    private Hashtable<String, Call> downCalls;  //下载的请求
    private OkHttpClient mClient;

    /**
     * 获取单例
     *
     * @return
     */
    public static DownloadManager getInstance() {
        for (; ; ) {
            DownloadManager current = INSTANCE.get();
            if (current != null) return current;
            else {
                current = new DownloadManager();
                INSTANCE.set(current);
            }
        }
    }

    private DownloadManager() {
        downCalls = new Hashtable<>();
        mClient = new OkHttpClient.Builder().build();
    }

    public void download(String url, DownloadObserver downloadObserver) {
        Observable.just(url).filter(new Predicate<String>() {
            @Override
            public boolean test(@NonNull String s) throws Exception {
                if (downCalls.containsKey(s)) return true;
                return false;
            }
        }).flatMap(new Function<String, ObservableSource<DownloadInfo>>() {
            @Override
            public ObservableSource<DownloadInfo> apply(@NonNull String s) throws Exception {
                return Observable.just(createInfo(s));
            }
        }).map(new Function<DownloadInfo, DownloadInfo>() {
            @Override
            public DownloadInfo apply(@NonNull DownloadInfo downloadInfo) throws Exception {
                return getRealFileName(downloadInfo);
            }
        }).flatMap(new Function<DownloadInfo, ObservableSource<DownloadInfo>>() {
            @Override
            public ObservableSource<DownloadInfo> apply(@NonNull DownloadInfo downloadInfo) throws Exception {
                return Observable.create(new DownloadSubscribe(downloadInfo));
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(downloadObserver);
    }

    /**
     * 初始化下载信息
     * @param url
     * @return
     */
    private DownloadInfo createInfo(String url) {
        DownloadInfo info = new DownloadInfo(url);
        info.totalSize = getContentLength(url);
        info.fileName = url.substring(url.lastIndexOf("/"));
        return info;
    }

    /**
     * 获取下载文件的长度
     * @param url
     * @return
     */
    private long getContentLength(String url) {
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = mClient.newCall(request).execute();
            if (response != null && response.isSuccessful()) {
                long contentLength = response.body().contentLength();

                return contentLength == 0 ? DownloadInfo.TOTAL_ERROR : contentLength;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return DownloadInfo.TOTAL_ERROR;
    }

    /**
     * 返回文件名
     * @param info
     * @return
     */
    private DownloadInfo getRealFileName(DownloadInfo info) {
        String fileName = info.fileName;
        long downloadLength = 0;
        long contentLength = info.totalSize;
        File file = new File(PATH, info.fileName);
        if (file.exists()) {
            downloadLength = file.length();
        }
        //之前下载过的，需要重新来一个文件
        //之前下载过,需要重新来一个文件
        int i = 1;
        while (downloadLength >= contentLength) {
            int dotIndex = fileName.lastIndexOf(".");
            String fileNameOther;
            if (dotIndex == -1) {
                fileNameOther = fileName + "(" + i + ")";
            } else {
                fileNameOther = fileName.substring(0, dotIndex)
                        + "(" + i + ")" + fileName.substring(dotIndex);
            }
            File newFile = new File(PATH, fileNameOther);
            file = newFile;
            downloadLength = newFile.length();
            i++;
        }
        //设置改变过的文件名/大小
        info.currentSize = downloadLength;
        info.fileName = file.getName();
        return info;
    }

    /**
     * 文件下载实体
     */
    private class DownloadSubscribe implements ObservableOnSubscribe<DownloadInfo> {
        DownloadInfo info;

        public DownloadSubscribe(DownloadInfo info) {
            this.info = info;
        }

        @Override
        public void subscribe(@NonNull ObservableEmitter<DownloadInfo> e) throws Exception {
            String url = info.url;
            e.onNext(info);
            Request request = new Request.Builder()
                    .addHeader("RANGE", "bytes=" + info.currentSize + "-" + info.totalSize)
                    .url(info.url)
                    .build();
            Call call = mClient.newCall(request);
            downCalls.put(info.url, call);   //将请求存起来
            Response response = call.execute();
            File file = new File(PATH, info.fileName);
            InputStream inputStream = null;
            FileOutputStream fos = null;
            try {
                inputStream = response.body().byteStream();
                fos = new FileOutputStream(file, true);
                byte[] buffer = new byte[2048]; //缓冲数组
                int len;
                while ((len = inputStream.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                    info.currentSize += len;
                    e.onNext(info);
                }
                fos.flush();
                downCalls.remove(info.url);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                IOUtil.closeQuietly(inputStream);
                IOUtil.closeQuietly(fos);
            }
            e.onComplete();
        }
    }
}
