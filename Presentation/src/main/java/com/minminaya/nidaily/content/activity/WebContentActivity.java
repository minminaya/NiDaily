package com.minminaya.nidaily.content.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.minminaya.data.http.model.content.ContentModel;
import com.minminaya.library.util.Logger;
import com.minminaya.nidaily.C;
import com.minminaya.nidaily.R;
import com.minminaya.nidaily.base.BaseActivity;
import com.minminaya.nidaily.manager.ZhihuContentManager;
import com.minminaya.nidaily.util.HtmlUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * 根据id加载相应的item详情
 * Created by Niwa on 2017/10/1.
 */

public class WebContentActivity extends BaseActivity {
    @BindView(R.id.web_view_web_content)
    WebView webViewWebContent;
    @BindView(R.id.img_at_web_content_activity)
    ImageView img;
    @BindView(R.id.tv_detail_title_at_web_content_activity)
    TextView tvDetailTitle;
    @BindView(R.id.tv_image_source_at_web_content_activity)
    TextView tvImageSource;

    private ContentModel contentModel;
    /**
     * 当前item的id
     */
    private int id = 0x0000;

    /**
     * Activity跳转，传入当前item的id
     *
     * @param context 上下文
     * @param id      home页中RecycleView的当前item的id
     */
    public static void lanuch(Context context, int id) {
        Intent mIntent = new Intent(context, WebContentActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mIntent.putExtra(C.ActivityLoadString.LOAD_CONTENT_ACTIVITY, id);
        context.startActivity(mIntent);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_web_content;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getIntExtra(C.ActivityLoadString.LOAD_CONTENT_ACTIVITY, -1);

            contentModel = (ContentModel) ZhihuContentManager.getInstance().getContentFromId(id);
            if (contentModel != null) {
                setViewData(contentModel);
            }
        }

    }

    @Override
    public void setListeners() {

    }

    @Override
    public void bind() {


    }

    @Override
    public void unBind() {
        //移除当前EventBus
        EventBus.getDefault().unregister(this);
    }

    /**
     * 接收来自HttpManager端EventBus的通知，然后重新读取本地数据，通知RecyclerView更新数据
     */
    @Subscribe(threadMode = ThreadMode.POSTING, priority = 56)
    public void getEventBusEvent(Integer index) {

        Logger.e("WebContentActivity", "getEventBusEvent");
        contentModel = (ContentModel) ZhihuContentManager.getInstance().getContentFromId(index);
        Logger.e("WebContentActivity", "getEventBusEvent：" + contentModel.getId());

        setViewData(contentModel);

        EventBus.getDefault().cancelEventDelivery(index);
    }

    /**
     * 将contentModel中数据设置到view中
     */
    private void setViewData(ContentModel contentModel) {

        Logger.e("WebContentActivity setViewData", "id:" + contentModel.getId());
        //webView中的内容
        String htmlData = HtmlUtil.createHtmlData(contentModel);
        webViewWebContent.loadData(htmlData, HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);

        Glide.with(WebContentActivity.this).load(contentModel.getImage()).into(img);

        tvDetailTitle.setText(contentModel.getTitle());

        tvImageSource.setText(contentModel.getImage_source());
    }
}

