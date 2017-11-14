package com.yy.mobile.qupaishenqu.ui.camera.basketball.model.playtype;

/**
 * User: newSalton@outlook.com
 * Date: 2017/7/27 15:59
 * ModifyTime: 15:59
 * Description:
 */
public class JoyScriptItem extends BasePlayType implements IPlayType {
    public int remindCount; //剩余可玩次数

    public JoyScriptItem(String name, int resId, int remindCount) {
        this.name = name;
        this.resId = resId;
        this.remindCount = remindCount;
    }
}
