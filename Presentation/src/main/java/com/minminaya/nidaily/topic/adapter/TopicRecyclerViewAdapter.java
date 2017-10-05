package com.minminaya.nidaily.topic.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.bumptech.glide.Glide;
import com.minminaya.data.http.model.topic.TopicItemModel;
import com.minminaya.library.util.Logger;
import com.minminaya.nidaily.App;
import com.minminaya.nidaily.C;
import com.minminaya.nidaily.R;
import com.minminaya.nidaily.topic.activity.ThemeItemActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * TopicFragment页的RecyclerView的Adapter
 * Created by Niwa on 2017/9/30.
 */

public class TopicRecyclerViewAdapter extends RecyclerView.Adapter<TopicRecyclerViewAdapter.ViewHolderA> {
    private List<TopicItemModel> topicItemModels;
    private FragmentManager mFragmentManager;

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.mFragmentManager = fragmentManager;
    }

    public List<TopicItemModel> getTopicItemModels() {
        return topicItemModels;
    }

    public void setTopicItemModels(List<TopicItemModel> topicItemModels) {
        this.topicItemModels = topicItemModels;
    }

    public TopicRecyclerViewAdapter() {
        Logger.d("TopicRecyclerViewAdapter", "TopicRecyclerViewAdapter 构造");
    }

    @Override
    public ViewHolderA onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic_recycler_view, parent, false);
        ViewHolderA viewHolderA = new ViewHolderA(view);
        return viewHolderA;
    }

    @Override
    public void onBindViewHolder(ViewHolderA holder, final int position) {
        if (topicItemModels != null) {
            holder.tvDescriptionRecyclerViewItem.setText(topicItemModels.get(0).getOthers().get(position).getDescription());
            holder.tvTopicName.setText(topicItemModels.get(0).getOthers().get(position).getName());
            Glide.with(App.getINSTANCE()).load(topicItemModels.get(0).getOthers().get(position).getThumbnail()).into(holder.imgTopicRecyclerViewItem);

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(App.getINSTANCE(), ThemeItemActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt(C.BundleKeyString.TOPIC_RECYCLER_VIEW_TO_THEME_ACTIVITY, topicItemModels.get(0).getOthers().get(position).getId());
                    intent.putExtras(bundle);
                    ActivityUtils.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return (topicItemModels != null) ? topicItemModels.get(0).getOthers().size() : 0;
    }

    class ViewHolderA extends RecyclerView.ViewHolder {
        @BindView(R.id.card_view_at_topic_recycler_view_item)
        CardView cardView;
        @BindView(R.id.tv_description_topic_recycler_view_item)
        TextView tvDescriptionRecyclerViewItem;
        @BindView(R.id.tv_topic_name)
        TextView tvTopicName;
        @BindView(R.id.img_topic_recycler_view_item)
        ImageView imgTopicRecyclerViewItem;

        ViewHolderA(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
