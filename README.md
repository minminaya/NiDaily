# NiDaily
使用知乎日报API创建的的NiDaily，基于MVP模式

---

# 简书同步

[基于知乎日报API的NiDaily--（1）](http://www.jianshu.com/p/0cbc99412f1f)

[基于知乎日报API的NiDaily--（2）--分析](http://www.jianshu.com/p/8f02133dcfe2)

---

# 预览

<div align="center">
<img src="http://upload-images.jianshu.io/upload_images/3515789-676ff7c15986ba5f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240" height="384" width="216" >
<img src="http://upload-images.jianshu.io/upload_images/3515789-0355780fcbdd2f9e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240" height="384" width="216" >
<img src="http://upload-images.jianshu.io/upload_images/3515789-39723ccd5616ba05.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240" height="384" width="216" >
<img src="http://upload-images.jianshu.io/upload_images/3515789-43b37c90654db5d8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240" height="384" width="216" >
 </div>
 
<div align="center">
<img src="http://upload-images.jianshu.io/upload_images/3515789-a77e6ebee31a5df5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240" height="384" width="216" >
<img src="http://upload-images.jianshu.io/upload_images/3515789-91083e26e98a76ee.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240" height="384" width="216" >
<img src="http://upload-images.jianshu.io/upload_images/3515789-580526a96dcf3d4e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240" height="384" width="216" >
 <img src="http://upload-images.jianshu.io/upload_images/3515789-6e55c0919f770e94.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240" height="384" width="216" >
 </div>
 
---

# 下面是模拟器的gif

<div align="left">
<img src="http://upload-images.jianshu.io/upload_images/3515789-17abf1db7312d3dc.gif?imageMogr2/auto-orient/strip" height="576" width="324" >
<img src="http://upload-images.jianshu.io/upload_images/3515789-f69b0b74e31a7bad.gif?imageMogr2/auto-orient/strip" height="384" width="216" >
<img src="http://upload-images.jianshu.io/upload_images/3515789-578b132dcdb77b90.gif?imageMogr2/auto-orient/strip" height="384" width="216" >

</div>

<div align="left">
<img src="http://upload-images.jianshu.io/upload_images/3515789-d8e7cc634c7b8d93.gif?imageMogr2/auto-orient/strip" height="384" width="216" >
<img src="http://upload-images.jianshu.io/upload_images/3515789-15cfe64b2acedb83.gif?imageMogr2/auto-orient/strip" height="384" width="216" >
 </div>

---

# 分析

该项目最关健的地方在于缓存系统，架构图如下


![缓存架构](http://upload-images.jianshu.io/upload_images/3515789-e54ec318c148fe94.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



#基本思路
- 1.View层（比如fragment）中需要数据，然后请求presenter
- 2.presenter向Data Module请求数据
- 3.Data Module先判断本地缓存中有没有所需要的数据
- 4.如果本地缓存中有数据，则返回给presenter
- 5.如果本地缓存无数据，则请求网络下载，并通知presenter
- 6.presenter得到数据后控制View层显示数据

> 有个关键的地方，这里是有EventBus来进行Data Module和presenter的通信的

---
#例子

**1 HotFragment**

```
public class HotFragment extends BaseFragment implements MvpView {
 @Override
    public void bind() {
        .....
    //向presenter请求数据  
    hotFragmentPresenter.getEventBusEvent(C.EventBusString.HOT_CACHE_ITEM_DOWNLOAD_SUCCESSFUL);


    }
}
```

---


**2 HotFragmentPresenter**
```
public class HotFragmentPresenter extends BasePresenter<HotFragment> {

  ....
    /**1 可以被view调用请求数据
     * 2 接收来自HttpManager端EventBus的通知，然后重新读取本地数据，通知RecyclerView更新数据
     */
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 1)
    public void getEventBusEvent(Integer eventIndex) {
        switch (eventIndex) {
            case C.EventBusString.HOT_CACHE_ITEM_DOWNLOAD_SUCCESSFUL:
                Logger.e("ZhihuContentManager", "getEventBusEvent");
                //向本地缓存管理器请求数据
                hotModel = (HotModel) ZhihuContentManager.getInstance().getHotData(DateUtils.getBeforeDayTime(dateIndex));
                if (hotModel != null) {
                    Logger.e("ZhihuContentManager", "getEventBusEvent：" + hotModel.getRecent().get(0).getTitle());
                    //通知view（这里是HotFragment）更新view数据
                    getMvpView().notifyRecyvlerViewAdapter();

                } else {
                    Logger.e("ZhihuContentManager", "hotModel为空");
                }
                break;
        }
    }

....
}

```

---

**3 ZhihuContentManager**

```
public class ZhihuContentManager {
     private static final ZhihuContentManager mZhihuContentManager = new ZhihuContentManager();
     
     public Object getHotData(String date) {
        //首先读取缓存中的数据
        Object object = CacheAtFileManage.getObjectAtFile(C.CacheFileString.HOT_CACHE_FILE_NAME_DATE_IS + date);
        //如果不为空，则使用，为空，则重新访问网络下载到缓存
        if (object != null) {
            Logger.e("ZhihuContentManager", "object不为空");
            return object;
        } else {
            //请求网络
            HttpManager.getInstance().loadHotModel(date);
        }
        return null;
    }
}
```

**4 HttpManager**

```

    private static final HttpManager httpManager = new HttpManager();
    /**
     * 连接网络获取HotModel数据
     */
     public void loadHotModel(String date) {
        this.hotModelDate = date;
        NetWorkForRestApi.getZhihuApi()
                .loadHotModel()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(hotModelObserver);
    }
```

按顺序1234，注释很清楚了，34俩个Manager设计成static final的，利于访问

---

#开源地址
[NiDaily](https://github.com/minminaya/NiDaily)


# License

Apache License 2.0
