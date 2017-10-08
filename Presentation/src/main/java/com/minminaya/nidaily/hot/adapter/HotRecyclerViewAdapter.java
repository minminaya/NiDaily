package com.minminaya.nidaily.hot.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.minminaya.data.http.model.home.StoriesBean;
import com.minminaya.data.http.model.hot.RecentBean;
import com.minminaya.library.util.Logger;
import com.minminaya.nidaily.App;
import com.minminaya.nidaily.R;
import com.minminaya.nidaily.content.activity.WebContentActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * HomeFragment页的RecyclerView的Adapter
 * Created by Niwa on 2017/10/08.
 */

public class HotRecyclerViewAdapter extends RecyclerView.Adapter<HotRecyclerViewAdapter.ViewHolderA> {
    private List<RecentBean> recentBeanList;

    public List<RecentBean> getRecentBeanList() {
        return recentBeanList;
    }

    public void setRecentBeanList(List<RecentBean> recentBeanList) {
        this.recentBeanList = recentBeanList;
    }

    public HotRecyclerViewAdapter() {
        Logger.d("HotRecyclerViewAdapter", "HotRecyclerViewAdapter构造");
    }

    @Override
    public ViewHolderA onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_recycler_view, parent, false);
        ViewHolderA viewHolderA = new ViewHolderA(view);
        return viewHolderA;
    }

    @Override
    public void onBindViewHolder(ViewHolderA holder, final int position) {
        if (recentBeanList != null) {
            holder.tvHomeRecyclerViewItem.setText(recentBeanList.get(position).getTitle());
            Glide.with(App.getINSTANCE()).load(recentBeanList.get(position).getThumbnail()).into(holder.imgHomeRecyclerViewItem);
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WebContentActivity.lanuch(App.getINSTANCE(), recentBeanList.get(position).getNews_id());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return (recentBeanList != null) ? recentBeanList.size() : 0;
    }

    class ViewHolderA extends RecyclerView.ViewHolder {
        @BindView(R.id.card_view_at_home_recycler_view_item)
        CardView cardView;
        @BindView(R.id.tv_home_recycler_view_item)
        TextView tvHomeRecyclerViewItem;
        @BindView(R.id.img_home_recycler_view_item)
        ImageView imgHomeRecyclerViewItem;

        ViewHolderA(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
