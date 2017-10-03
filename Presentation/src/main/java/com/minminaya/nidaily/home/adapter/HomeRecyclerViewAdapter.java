package com.minminaya.nidaily.home.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.minminaya.data.http.model.home.BeforeModel;
import com.minminaya.data.http.model.home.StoriesBean;
import com.minminaya.library.util.Logger;
import com.minminaya.nidaily.App;
import com.minminaya.nidaily.R;
import com.minminaya.nidaily.content.activity.WebContentActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * HomeFragment页的RecyclerView的Adapter
 * Created by Niwa on 2017/9/30.
 */

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolderA> {
    private List<StoriesBean> storiesBeanList;

    public List<StoriesBean> getStoriesBeanList() {
        return storiesBeanList;
    }

    public void setStoriesBeanList(List<StoriesBean> storiesBeanList) {
        this.storiesBeanList = storiesBeanList;
    }

    public HomeRecyclerViewAdapter() {
        Logger.d("HomeRecyclerViewAdapter", "HomeRecyclerViewAdapter构造");
    }

    @Override
    public ViewHolderA onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_recycler_view, parent, false);
        ViewHolderA viewHolderA = new ViewHolderA(view);
        return viewHolderA;
    }

    @Override
    public void onBindViewHolder(ViewHolderA holder, final int position) {
        if (storiesBeanList != null) {
            holder.tvHomeRecyclerViewItem.setText(storiesBeanList.get(position).getTitle());
            Glide.with(App.getINSTANCE()).load(storiesBeanList.get(position).getImages().get(0)).into(holder.imgHomeRecyclerViewItem);
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WebContentActivity.lanuch(App.getINSTANCE(), storiesBeanList.get(position).getId());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return (storiesBeanList != null) ? storiesBeanList.size() : 0;
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
