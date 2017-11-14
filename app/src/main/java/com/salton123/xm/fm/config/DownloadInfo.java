package com.salton123.xm.fm.config;

/**
 * User: newSalton@outlook.com
 * Date: 2017/8/9 15:14
 * ModifyTime: 15:14
 * Description:
 */
public class DownloadInfo {

    public static final long TOTAL_ERROR = -1;
    public String url ;
    public long totalSize ;
    public long currentSize ;
    public String fileName ;

    public DownloadInfo(String url) {
        this.url = url;
    }
}
