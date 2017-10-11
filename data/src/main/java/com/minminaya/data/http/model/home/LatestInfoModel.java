package com.minminaya.data.http.model.home;

import java.io.Serializable;
import java.util.List;

/**
 * latest的model
 * Created by Niwa on 2017/10/11.
 */

public class LatestInfoModel implements Serializable {
    private static final long serialVersionUID = 156465456464998899L;

    /**
     * date : 20171011
     * stories : [{"images":["https://pic4.zhimg.com/v2-7d67b0f68f0da5fd9309394e993712cf.jpg"],"type":0,"id":9646672,"ga_prefix":"101114","title":"你所知道的情侣们，是不是出轨后还是选择了不分开？"},{"images":["https://pic3.zhimg.com/v2-5a3f35301350584bd3a8b2c133cf5a86.jpg"],"type":0,"id":9649498,"ga_prefix":"101113","title":"给 doc 加了个「小尾巴」的 Office 2007 ，正式和你挥手说再见"},{"images":["https://pic2.zhimg.com/v2-d99a7bf92ee91d067f3306ca4451e5b9.jpg"],"type":0,"id":9630949,"ga_prefix":"101112","title":"大误 · 神父、赌徒、中国队"},{"images":["https://pic4.zhimg.com/v2-d753cce2a5aa738fb31344f51d75ba73.jpg"],"type":0,"id":9649478,"ga_prefix":"101111","title":"鹿晗的粉丝帝国：我们只听、只信、只看鹿晗"},{"images":["https://pic1.zhimg.com/v2-faf132732d2c1dccd0b6547d24bc1248.jpg"],"type":0,"id":9646646,"ga_prefix":"101110","title":"做脑外科手术时，病人的大脑会像豆腐似的被切来切去吗？"},{"images":["https://pic3.zhimg.com/v2-eedb6a190966b4dc2c119a41df278066.jpg"],"type":0,"id":9648236,"ga_prefix":"101109","title":"为什么有的人就是不吃香菜？没办法，基因里写好的"},{"images":["https://pic3.zhimg.com/v2-3260ae65d2a48e46e3856a3bc219e54a.jpg"],"type":0,"id":9646561,"ga_prefix":"101108","title":"- 我坐地铁，怎么还没你在上面到得快？\r\n- 因为这是日本呀"},{"images":["https://pic3.zhimg.com/v2-c59fcb7abccaf8181b094d02c8d163a6.jpg"],"type":0,"id":9648003,"ga_prefix":"101107","title":"中国航天员为什么都是军人，而不是博士、科学家等科研人员？"},{"images":["https://pic4.zhimg.com/v2-f8b2149b062bb00fa8e20bfa48b7506b.jpg"],"type":0,"id":9648785,"ga_prefix":"101107","title":"前些日子，FBI 在反腐调查中逮捕了 4 名篮球教练和一名 adidas 高管"},{"images":["https://pic1.zhimg.com/v2-f0f42c1acdf48c6b1388c520d86337f4.jpg"],"type":0,"id":9646691,"ga_prefix":"101107","title":"两种对我而言极其重要的思维方式，现在介绍给你"},{"images":["https://pic1.zhimg.com/v2-2550f2ea1e7a205d9e9402dd41d643cc.jpg"],"type":0,"id":9648737,"ga_prefix":"101106","title":"瞎扯 · 如何正确地吐槽"}]
     * top_stories : [{"image":"https://pic4.zhimg.com/v2-0fabfc1f25534008794a44735ce445d3.jpg","type":0,"id":9649478,"ga_prefix":"101111","title":"鹿晗的粉丝帝国：我们只听、只信、只看鹿晗"},{"image":"https://pic4.zhimg.com/v2-69cf788e525517d17fd06d4587b1c183.jpg","type":0,"id":9648785,"ga_prefix":"101107","title":"前些日子，FBI 在反腐调查中逮捕了 4 名篮球教练和一名 adidas 高管"},{"image":"https://pic2.zhimg.com/v2-c6bf1321f0813ce6ae48b56388e45d71.jpg","type":0,"id":9648442,"ga_prefix":"101019","title":"想和喜欢的人一起去逛这些菜市场"},{"image":"https://pic1.zhimg.com/v2-fd5d9fe5ea4c5bb0e5422888dbcf4e4c.jpg","type":0,"id":9647091,"ga_prefix":"101017","title":"终于等到这款游戏，我已经准备（摔）几个手柄来庆祝一下"},{"image":"https://pic3.zhimg.com/v2-ebad6c9dd59183ddcce8960115598b2e.jpg","type":0,"id":9648068,"ga_prefix":"101014","title":"中国人第一次使用自己的射电望远镜找到新的脉冲星，此刻我想致敬两个人"}]
     */

    private String date;
    private List<StoriesBean> stories;
    private List<TopStoriesBean> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }

    public List<TopStoriesBean> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStoriesBean> top_stories) {
        this.top_stories = top_stories;
    }


}
