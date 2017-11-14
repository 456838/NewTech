package com.salton123.xm.fm;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.salton123.xm.R;
import com.salton123.xm.fm.playtype.BasketballGameItem;
import com.salton123.xm.fm.playtype.EmptyItem;
import com.salton123.xm.fm.playtype.GuessWordItem;
import com.salton123.xm.fm.playtype.JoyScriptItem;
import com.salton123.xm.fm.vh.BasketballGameVH;
import com.salton123.xm.fm.vh.EmptyVH;
import com.salton123.xm.fm.vh.GuessWordVH;
import com.salton123.xm.fm.vh.JoyScriptVH;

import java.util.List;

/**
 * User: newSalton@outlook.com
 * Date: 2017/7/28 10:06
 * ModifyTime: 10:06
 * Description:
 */
public class MultiTypeAdapter extends BaseRecycerViewAdapter<MultiTypeItem,RecyclerView.ViewHolder> {
    public MultiTypeAdapter(Context context, List<MultiTypeItem> list) {
        super(context, list);
    }

    public MultiTypeAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(final int position) {
        return list.get(position).getViewType();
    }

    @Override
    public RecyclerView.ViewHolder getCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());        //这里有性能问题，稍后处理
        switch (viewType) {
            case MultiTypeItem.TYPE_GUESS_WORD:
                return new GuessWordVH(inflater.inflate(R.layout.item_play_type_guess_word, parent, false));
            case MultiTypeItem.TYPE_JOY_SCRIPT:
                return new JoyScriptVH(inflater.inflate(R.layout.item_play_type_joy_script, parent, false));
            case MultiTypeItem.TYPE_BASKETBALL_GAME:
                return new BasketballGameVH(inflater.inflate(R.layout.item_play_type_basketball_game, parent, false));
            default:
                return new EmptyVH(inflater.inflate(R.layout.item_play_type_empty, parent, false));
        }
    }

    @Override
    public void getBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    mClickListener.onItemClick(position, v, holder);
                }
            }
        });
        if (holder instanceof GuessWordVH) {
            GuessWordItem item = (GuessWordItem) list.get(position).getItem();
            GuessWordVH vh = (GuessWordVH) holder;
            vh.tv_name.setText(item.name);
            vh.iv_icon.setImageResource(item.resId);
            vh.tv_remind_count.setText("今日还可拍摄:" + item.remindCount + "张");
        } else if (holder instanceof JoyScriptVH) {
            JoyScriptItem item = (JoyScriptItem) list.get(position).getItem();
            JoyScriptVH vh = (JoyScriptVH) holder;
            vh.tv_name.setText(item.name);
            vh.iv_icon.setImageResource(item.resId);
            vh.tv_remind_count.setText("今天还可玩:" + item.remindCount + "次");
        } else if (holder instanceof BasketballGameVH) {
            BasketballGameItem item = (BasketballGameItem) list.get(position).getItem();
            BasketballGameVH vh = (BasketballGameVH) holder;
            vh.tv_name.setText(item.name);
            vh.iv_icon.setImageResource(item.resId);
            vh.tv_remind_count.setText("篮球还可玩:" + item.remindCount + "次");
        } else if (holder instanceof EmptyVH) {
            EmptyItem item = (EmptyItem) list.get(position).getItem();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
