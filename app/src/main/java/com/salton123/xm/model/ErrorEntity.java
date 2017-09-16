package com.salton123.xm.model;

import com.google.gson.Gson;

/**
 * User: newSalton@outlook.com
 * Date: 2017/9/9 21:09
 * ModifyTime: 21:09
 * Description:
 */
public class ErrorEntity {

    int code;
    String message ;

    public ErrorEntity(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
