package com.salton123.xm.fm.playtype;

/**
 * User: newSalton@outlook.com
 * Date: 2017/7/27 20:16
 * ModifyTime: 20:16
 * Description:
 */
public class BasketballGameItem extends BasePlayType implements IPlayType {

    public int status;
    public int remindCount;

    public BasketballGameItem(String name, int resId, int status, int remindCount) {
        this.name = name;
        this.resId = resId;
        this.status = status;
        this.remindCount = remindCount;
    }

    @Override
    public String toString() {
        return "BasketballGameItem{" +
                "name='" + name + '\'' +
                ", resId=" + resId +
                ", status=" + status +
                ", remindCount=" + remindCount +
                '}';
    }

}
