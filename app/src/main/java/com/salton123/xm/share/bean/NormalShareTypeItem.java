package com.salton123.xm.share.bean;

/**
 * User: newSalton@outlook.com
 * Date: 2017/8/2 21:49
 * ModifyTime: 21:49
 * Description:
 */
public class NormalShareTypeItem extends BaseShareItem {

    public String name="李四";
    public String title ="欢乐飚戏";


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "NormalShareTypeItem{" +
                "name='" + name + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
