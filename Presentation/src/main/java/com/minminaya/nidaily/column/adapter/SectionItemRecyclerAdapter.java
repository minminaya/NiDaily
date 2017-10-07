package com.minminaya.nidaily.column.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.minminaya.data.http.model.column.SectionItemModel;
import com.minminaya.data.http.model.column.StoriesBean;
import com.minminaya.library.util.Logger;
import com.minminaya.nidaily.App;
import com.minminaya.nidaily.R;
import com.minminaya.nidaily.content.activity.WebContentActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * SectionItemActivity下RecyclerView的adapter
 * Created by Niwa on 2017/10/7.
 */

public class SectionItemRecyclerAdapter extends RecyclerView.Adapter<SectionItemRecyclerAdapter.ViewHolderA> {
    private List<SectionItemModel> sectionItemModels;

    public List<SectionItemModel> getSectionItemModels() {
        return sectionItemModels;
    }

    public void setSectionItemModels(List<SectionItemModel> sectionItemModels) {
        this.sectionItemModels = sectionItemModels;
    }

    public SectionItemRecyclerAdapter() {
        Logger.d("SectionItemRecyclerAdapter", "SectionItemRecyclerAdapter 构造");
    }

    @Override
    public ViewHolderA onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_section_recycyler_view, parent, false);
        ViewHolderA viewHolderA = new ViewHolderA(view);
        return viewHolderA;
    }

    @Override
    public void onBindViewHolder(ViewHolderA holder, final int position) {
        if (sectionItemModels != null) {

            final List<StoriesBean> stories = sectionItemModels.get(0).getStories();
            holder.tvTitle.setText(stories.get(position).getTitle());
            holder.tvDisplayDate.setText(stories.get(position).getDisplay_date());
            Glide.with(App.getINSTANCE()).load(stories.get(position).getImages().get(0)).into(holder.img);

            holder.cardViewAtTopicRecyclerViewItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WebContentActivity.lanuch(App.getINSTANCE(), stories.get(position).getId());
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return (sectionItemModels != null) ? sectionItemModels.get(0).getStories().size() : 0;
    }

    class ViewHolderA extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.img_section)
        ImageView img;
        @BindView(R.id.tv_display_date)
        TextView tvDisplayDate;
        @BindView(R.id.card_view_at_topic_recycler_view_item)
        CardView cardViewAtTopicRecyclerViewItem;

        ViewHolderA(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
