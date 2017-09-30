package com.minminaya.nidaily.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.minminaya.nidaily.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * HomeFragment页的RecyclerView的Adapter
 * Created by Niwa on 2017/9/30.
 */

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolderA> {


    @Override
    public ViewHolderA onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_recycler_view, parent, false);
        ViewHolderA viewHolderA = new ViewHolderA(view);
        return viewHolderA;
    }

    @Override
    public void onBindViewHolder(ViewHolderA holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class ViewHolderA extends RecyclerView.ViewHolder {

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
