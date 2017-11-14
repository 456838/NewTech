package com.salton123.xm.dialog;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.salton123.base.BaseSupportFragment;
import com.salton123.xm.R;
import com.salton123.xm.fm.BaseRecycerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * User: newSalton@outlook.com
 * Date: 2017/8/30 22:15
 * ModifyTime: 22:15
 * Description:
 */
public class DialogComponent extends BaseSupportFragment{

    private RecyclerView recyclerView;

    @Override
    public int GetLayout() {
        return R.layout.dialog_component;
    }

    @Override
    public void InitVariable(Bundle savedInstanceState) {

    }

    @Override
    public void InitViewAndData() {
        recyclerView = f(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setPadding(0,0,0,0);

        List<String> nameList = new ArrayList<>();
        for (int i = 0 ; i<10 ; i++){
            nameList.add("hello"+i);
        }
        recyclerView.setAdapter(new BaseRecycerViewAdapter(_mActivity,nameList) {
            @Override
            public RecyclerView.ViewHolder getCreateViewHolder(ViewGroup parent, int viewType) {
                return new NameVH(inflater().inflate(R.layout.vh_name_list,null));
            }

            @Override
            public void getBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            }
        });

    }

    @Override
    public void InitListener() {

    }

    class NameVH extends RecyclerView.ViewHolder{

        public NameVH(View itemView) {
            super(itemView);
        }
    }
}
