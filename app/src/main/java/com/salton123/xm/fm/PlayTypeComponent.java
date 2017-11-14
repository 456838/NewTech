package com.salton123.xm.fm;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.salton123.base.BaseSupportFragment;
import com.salton123.xm.R;
import com.salton123.xm.fm.playtype.BasketballGameItem;
import com.salton123.xm.fm.playtype.GuessWordItem;
import com.salton123.xm.fm.playtype.JoyScriptItem;

import java.util.ArrayList;
import java.util.List;

/**
 * User: newSalton@outlook.com
 * Date: 2017/7/27 22:56
 * ModifyTime: 22:56
 * Description:
 */
public class PlayTypeComponent extends BaseSupportFragment {

    private RecyclerView recycler;
    private MultiTypeAdapter mAdapter;
    private List<MultiTypeItem> list;

    @Override
    public int GetLayout() {
        return R.layout.component_video_play_type;
    }

    @Override
    public void InitVariable(Bundle savedInstanceState) {
        list = new ArrayList<>();
    }

    @Override
    public void InitViewAndData() {
        recycler = f(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(_mActivity, LinearLayoutManager.HORIZONTAL, false));
//        recycler.setLayoutManager(new GridLayoutManager(_mActivity,3));

        mAdapter = new MultiTypeAdapter(_mActivity, list);
        recycler.setAdapter(mAdapter);
        list.add(new MultiTypeItem(MultiTypeItem.TYPE_BASKETBALL_GAME, new BasketballGameItem("欢乐篮球", R.drawable.ic_launcher, 1, 5)));
        list.add(new MultiTypeItem(MultiTypeItem.TYPE_GUESS_WORD, new GuessWordItem("欢乐猜词", R.drawable.default_pic_load, 5)));
        list.add(new MultiTypeItem(MultiTypeItem.TYPE_BASKETBALL_GAME, new BasketballGameItem("欢乐篮球", R.drawable.ic_launcher, 1, 5)));
        list.add(new MultiTypeItem(MultiTypeItem.TYPE_JOY_SCRIPT, new JoyScriptItem("欢乐剧本", R.drawable.ic_launcher, 5)));
        list.add(new MultiTypeItem(MultiTypeItem.TYPE_GUESS_WORD, new GuessWordItem("欢乐猜词", R.drawable.default_pic_load, 5)));
        mAdapter.addAll(list);
        int size = mAdapter.getItemCount();
        if (size <= 3) {
            recycler.setLayoutManager(new GridLayoutManager(_mActivity, size));
        } else {
            recycler.setLayoutManager(new LinearLayoutManager(_mActivity, LinearLayoutManager.HORIZONTAL, false));
        }
//        int span = (int) ((screenWidth - size * ScreenUtils.dpToPx(_mActivity, 100)) / size);
        recycler.addItemDecoration(new SpaceItemDecoration(50));
        mAdapter.setOnItemClickListener(new BaseRecycerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view, RecyclerView.ViewHolder vh) {
                MultiTypeItem item = mAdapter.getData().get(position);
                if (item.getItem() instanceof BasketballGameItem) {
                    toast("BasketballGameItem,postion:"+position);
                }else if(item.getItem() instanceof GuessWordItem){
                    toast("GuessWordItem,postion:"+position);
                }
                else if(item.getItem() instanceof JoyScriptItem){
                    toast("JoyScriptItem,postion:"+position);
                }
            }
        });
    }

    @Override
    public void InitListener() {

    }

    class SpaceItemDecoration extends RecyclerView.ItemDecoration {
        int mSpace;

        /**
         * Retrieve any offsets for the given item. Each field of <code>outRect</code> specifies
         * the number of pixels that the item view should be inset by, similar to padding or margin.
         * The default implementation sets the bounds of outRect to 0 and returns.
         * <p>
         * <p>
         * If this ItemDecoration does not affect the positioning of item views, it should set
         * all four fields of <code>outRect</code> (left, top, right, bottom) to zero
         * before returning.
         * <p>
         * <p>
         * If you need to access Adapter for additional data, you can call
         * {@link RecyclerView#getChildAdapterPosition(View)} to get the adapter id of the
         * View.
         *
         * @param outRect Rect to receive the output.
         * @param view    The child view to decorate
         * @param parent  RecyclerView this ItemDecoration is decorating
         * @param state   The current state of RecyclerView.
         */
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.left = mSpace;
            outRect.right = mSpace;
            outRect.bottom = mSpace;
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = mSpace;
            }

        }

        public SpaceItemDecoration(int space) {
            this.mSpace = space;
        }
    }
}
