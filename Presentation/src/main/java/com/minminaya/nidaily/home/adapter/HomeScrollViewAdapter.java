package com.minminaya.nidaily.home.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.minminaya.data.http.model.home.TopStoriesBean;
import com.minminaya.nidaily.App;
import com.minminaya.nidaily.R;
import com.minminaya.nidaily.content.activity.WebContentActivity;

import java.util.List;

/**
 * home页headview的适配器
 * Created by niwa on 17/10/11.
 */
public class HomeScrollViewAdapter extends PagerAdapter {

    private List<TopStoriesBean> topStoriesBeans = null;

    public List<TopStoriesBean> getTopStoriesBean() {
        return topStoriesBeans;
    }

    public void setTopStoriesBean(List<TopStoriesBean> topStoriesBeans) {
        this.topStoriesBeans = topStoriesBeans;
    }

    public HomeScrollViewAdapter() {
    }

    @Override
    public int getCount() {
        return topStoriesBeans == null ? 0 : topStoriesBeans.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(container.getContext()).inflate(R.layout.view_head, null);
        TextView textView = (TextView) relativeLayout.findViewById(R.id.tv_description_head_view);
        ImageView imageView = (ImageView) relativeLayout.findViewById(R.id.img_head_view);

        if (topStoriesBeans != null) {
            //填充数据
            textView.setText(topStoriesBeans.get(position).getTitle());
            Glide.with(App.getINSTANCE()).load(topStoriesBeans.get(position).getImage()).into(imageView);
        }

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebContentActivity.lanuch(App.getINSTANCE(), topStoriesBeans.get(position).getId());
            }
        });

        container.addView(relativeLayout);

        return relativeLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        RelativeLayout view = (RelativeLayout) object;
        container.removeView(view);
    }
}