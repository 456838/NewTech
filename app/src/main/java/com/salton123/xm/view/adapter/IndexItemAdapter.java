package com.salton123.xm.view.adapter;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.salton123.xm.model.MultiSearchSection;

import java.util.List;

/**
 * Created by salton on 2017/8/13.
 */

public class IndexItemAdapter extends BaseSectionQuickAdapter<MultiSearchSection,BaseViewHolder>{

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId      The layout resource id of each item.
     * @param sectionHeadResId The section head layout id for each item
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public IndexItemAdapter(int layoutResId, int sectionHeadResId, List<MultiSearchSection> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, MultiSearchSection item) {
//        helper.setText(R.id.)
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiSearchSection item) {

    }
}