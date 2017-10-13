package com.minminaya.nidaily.about;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.minminaya.nidaily.R;
import com.minminaya.nidaily.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends BaseActivity {

    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.tv)
    TextView tv;

    @Override
    public int getContentView() {
        return R.layout.activity_about;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void setListeners() {

    }

    @Override
    public void bind() {
        tv.setText(AppUtils.getAppVersionName());
    }

    @Override
    public void unBind() {

    }
}
