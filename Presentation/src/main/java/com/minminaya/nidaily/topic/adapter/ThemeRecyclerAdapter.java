package com.minminaya.nidaily.topic.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.minminaya.data.http.model.topic.StoriesBean;
import com.minminaya.data.http.model.topic.ThemeItemModel;
import com.minminaya.library.util.Logger;
import com.minminaya.nidaily.App;
import com.minminaya.nidaily.R;
import com.minminaya.nidaily.content.activity.WebContentActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/** ThemeItemActivity下RecyclerView的adapter
 * Created by Niwa on 2017/10/5.
 */

public class ThemeRecyclerAdapter extends RecyclerView.Adapter<ThemeRecyclerAdapter.ViewHolderA> {
    private List<ThemeItemModel> themeItemModels;

    public List<ThemeItemModel> getTopicItemModels() {
        return themeItemModels;
    }

    public void setTopicItemModels(List<ThemeItemModel> themeItemModels) {
        this.themeItemModels = themeItemModels;
    }

    public ThemeRecyclerAdapter() {
        Logger.d("ThemeRecyclerAdapter", "ThemeRecyclerAdapter 构造");
    }

    @Override
    public ViewHolderA onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_theme_recycler_view, parent, false);
        ViewHolderA viewHolderA = new ViewHolderA(view);
        return viewHolderA;
    }

    @Override
    public void onBindViewHolder(ViewHolderA holder, final int position) {
        if (themeItemModels != null) {
            final List<StoriesBean> stories = themeItemModels.get(0).getStories();
            if (stories.get(position).getImages() != null) {
                holder.tvTitleThemeRecyclerViewItemForNoImg.setVisibility(View.GONE);

                holder.imgThemeRecyclerViewItem.setVisibility(View.VISIBLE);
                holder.tvTitleThemeRecyclerViewItem.setVisibility(View.VISIBLE);

                Glide.with(App.getINSTANCE()).load(stories.get(position).getImages().get(0)).into(holder.imgThemeRecyclerViewItem);
                holder.tvTitleThemeRecyclerViewItem.setText(stories.get(position).getTitle());
            } else {
                holder.tvTitleThemeRecyclerViewItemForNoImg.setText(stories.get(position).getTitle());
            }

            holder.cardViewAtTopicRecyclerViewItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WebContentActivity.lanuch(App.getINSTANCE(),stories.get(position).getId());
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return (themeItemModels != null) ? themeItemModels.get(0).getStories().size() : 0;
    }

    class ViewHolderA extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title_theme_recycler_view_item)
        TextView tvTitleThemeRecyclerViewItem;
        @BindView(R.id.tv_title_theme_recycler_view_item_for_no_img)
        TextView tvTitleThemeRecyclerViewItemForNoImg;
        @BindView(R.id.img_theme_recycler_view_item)
        ImageView imgThemeRecyclerViewItem;
        @BindView(R.id.card_view_at_topic_recycler_view_item)
        CardView cardViewAtTopicRecyclerViewItem;

        ViewHolderA(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
