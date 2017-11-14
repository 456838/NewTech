package com.salton123.xm.share.bean;

import java.util.Arrays;
import java.util.List;

/**
 * User: newSalton@outlook.com
 * Date: 2017/8/2 21:50
 * ModifyTime: 21:50
 * Description:
 */
public class StarShareTypeItem extends BaseShareItem {
    public String name = "张三";
    public String title = "欢乐篮球";
    public List<String> starNameList = Arrays.asList(getstarNameArr());

    private String[] getstarNameArr() {
        return new String[]{
                "迪丽热巴", "陈赫", "邓超", "大黑牛"
        };
    }

    @Override
    public String toString() {
        return "StarShareTypeItem{" +
                "name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", starNameList=" + starNameList +
                '}';
    }
}
