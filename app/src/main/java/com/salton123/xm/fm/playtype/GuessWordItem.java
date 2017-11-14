package com.salton123.xm.fm.playtype;

/**
 * User: newSalton@outlook.com
 * Date: 2017/7/27 20:21
 * ModifyTime: 20:21
 * Description:
 */
public class GuessWordItem extends BasePlayType implements IPlayType {
    public int remindCount; //剩余可玩次数

    public GuessWordItem(String name, int resId, int remindCount) {
        this.name = name;
        this.resId = resId;
        this.remindCount = remindCount;
    }
}