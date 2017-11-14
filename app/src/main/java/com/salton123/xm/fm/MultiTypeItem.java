package com.salton123.xm.fm;

/**
 * User: newSalton@outlook.com
 * Date: 2017/7/28 20:57
 * ModifyTime: 20:57
 * Description:
 */
public class MultiTypeItem {
    public static final int TYPE_GUESS_WORD = 0;
    public static final int TYPE_BASKETBALL_GAME = 1;
    public static final int TYPE_JOY_SCRIPT = 2;
    public static final int TYPE_OTHER = 3;

    private int viewType;
    private Object item;

    public MultiTypeItem(int viewType, Object item) {
        this.viewType = viewType;
        this.item = item;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public Object getItem() {
        return item;
    }

    public void setItem(Object item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "MultiTypeItem{" +
                "viewType=" + viewType +
                ", item=" + item +
                '}';
    }
}
