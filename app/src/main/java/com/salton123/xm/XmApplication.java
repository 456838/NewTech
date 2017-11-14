package com.salton123.xm;

import com.salton123.base.ApplicationBase;
import com.salton123.util.FileUtils;
import com.salton123.util.log.MLog;

import java.io.File;

import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.helper.ExceptionHandler;

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
    }


}
