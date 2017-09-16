package com.salton123.xm.model;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * User: newSalton@outlook.com
 * Date: 2017/8/14 10:11
 * ModifyTime: 10:11
 * Description:
 */
public class MultiSearchSection extends SectionEntity<MultiSearchBean> {
    private boolean isMore ;
    public MultiSearchSection(boolean isHeader, String header,boolean isMore) {
        super(isHeader, header);
    }


    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
    }
}
