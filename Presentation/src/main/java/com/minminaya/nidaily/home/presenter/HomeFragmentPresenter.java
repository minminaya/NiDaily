package com.minminaya.nidaily.home.presenter;

import com.minminaya.library.util.Logger;
import com.minminaya.nidaily.OuterActivity;
import com.minminaya.nidaily.mvp.presenter.base.BasePresenter;

/** HomeFragment的Presenter
 * Created by Niwa on 2017/9/27.
 */

public class HomeFragmentPresenter extends BasePresenter<OuterActivity> {


    /**
     *  测试Mvp架构
     * */
    public void textMvp(){
        Logger.e("HomeFragmentPresenter","mvp测试通过");
    }
}
