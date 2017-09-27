package com.minminaya.nidaily.mvp.presenter.base;

/**
 * presenter与Activity接入的几个方法
 * Created by Niwa on 2017/09/27
 */

public interface IPresenter<T> {
    void attachView(T view);

    void detachView(T view);

    void onDestroyed();

    boolean isAttachView(T view);
    //当传入Activity的context时，在presenter中用此方法获取到Activity的上下文，以控制Activity中的方法
    T getMvpView();
}