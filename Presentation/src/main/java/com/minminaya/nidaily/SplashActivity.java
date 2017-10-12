package com.minminaya.nidaily;

import android.content.Intent;
import android.os.Bundle;

import com.minminaya.nidaily.base.BaseActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * SplashActivity起始页
 * Created by Niwa on 2017/10/12.
 */

public class SplashActivity extends BaseActivity {

    private Disposable disposable;

    @Override
    public int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void setListeners() {

    }

    @Override
    public void bind() {
        disposable = Observable.timer(1500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        SplashActivity.this.finish();
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    }
                });
    }

    @Override
    public void unBind() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
