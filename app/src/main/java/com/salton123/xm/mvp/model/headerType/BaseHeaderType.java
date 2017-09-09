package com.salton123.xm.mvp.model.headerType;

/**
 * User: newSalton@outlook.com
 * Date: 2017/8/14 11:27
 * ModifyTime: 11:27
 * Description:热门类型
 */
public class BaseHeaderType<T> {
    private int type  ;     //tracks=1,AlbumList=2
    private String typeName ;   //标题
    private T entity ;       //实体数据

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }
}
