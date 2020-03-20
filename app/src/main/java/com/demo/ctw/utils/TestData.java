package com.demo.ctw.utils;

import com.demo.ctw.entity.AuctionCheckObject;
import com.demo.ctw.entity.NoticeObject;

import java.util.ArrayList;

public class TestData {
    public static String imgurl = "https://imgsa.baidu.com/forum/pic/item/92d0b5efce1b9d16a027815cfddeb48f8d5464c5.jpg";
    public static String imgurl1 = "https://imgsa.baidu.com/forum/pic/item/a306ab12c8fcc3cec2923a3e9c45d688d43f2007.jpg";
    public static String imgurl2 = "https://imgsa.baidu.com/forum/pic/item/f703918fa0ec08fa23b297aa57ee3d6d55fbda09.jpg";
    public static String imgurl3 = "https://imgsa.baidu.com/forum/pic/item/fdceae3eb13533fa1798b120a6d3fd1f41345bac.jpg";
    public static String imgurl4 = "https://imgsa.baidu.com/forum/pic/item/fa019882b9014a90b411ccb1a7773912b31beea2.jpg";
    public static String imgurl5 = "https://imgsa.baidu.com/forum/pic/item/b2b7d0a20cf431adb37c9b2f4536acaf2edd987e.jpg";
    public static String imgurl6 = "https://imgsa.baidu.com/forum/pic/item/fb26fb24b899a901b212723d13950a7b0208f5a9.jpg";
    public static String imgurl7 = "https://imgsa.baidu.com/forum/pic/item/6fc2a11c8701a18b084fa542902f07082838fe2a.jpg";
    public static String[] imgs = {imgurl, imgurl1, imgurl2, imgurl3, imgurl4, imgurl5, imgurl6, imgurl7, imgurl};

//    public static HomeObject getHomeData(int count) {
//        HomeObject homeObject = new HomeObject();
//        ArrayList<BannerObject> bannerObjects = new ArrayList<>();
//        for (int i = 0; i < 3; i++) {
//            BannerObject object = new BannerObject();
//            object.setLink(i);
//            object.setBanner_path(imgs[i]);
//            bannerObjects.add(object);
//        }
//        homeObject.setBanners(bannerObjects);
//        ArrayList<String> strings = new ArrayList<>();
//        for (int i = 0; i < 3; i++) {
//            strings.add("泉城广场大屏广告招商啦！ " + i);
//        }
//        homeObject.setList(strings);
//        ArrayList<HomeListObect> listObects = new ArrayList<>();
//        for (int i = 0; i < count; i++) {
//            HomeListObect object = new HomeListObect();
//            object.setTitle("高新万达大屏广告位 " + i);
//            object.setDate("03-28 12:00结束");
//            object.setImg(imgs[i]);
//            object.setAddress("济南市高新区工业南路万达广场");
//            object.setCount(i + "");
//            object.setTime("2" + i);
//            object.setPrice("9.0");
//            listObects.add(object);
//        }
//        homeObject.setData(listObects);
//
//        ArrayList<HomeItemObject> newsObjects = new ArrayList<>();
//        for (int i = 0; i < count; i++) {
//            HomeItemObject object = new HomeItemObject();
//            object.setTitle("高新万达大屏广告位 " + i);
//            object.setInfo("超差超差超差超查查");
//            object.setImg(imgs[i]);
//            object.setTime("2" + i);
//            newsObjects.add(object);
//        }
//        homeObject.setNews(newsObjects);
//        return homeObject;
//    }

    public static ArrayList<AuctionCheckObject> getAuctionCheck(int count) {
        ArrayList<AuctionCheckObject> data = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            AuctionCheckObject object = new AuctionCheckObject();
            object.setId(i + "");
            object.setName("电梯广告 " + i);
            data.add(object);
        }
        return data;
    }

    public static ArrayList<NoticeObject> getNotices(int count) {
        ArrayList<NoticeObject> data = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            NoticeObject object = new NoticeObject();
            object.setId(i + "");
            object.setTime("2019/12/01");
            object.setInfo("我是详情，我是详情----");
            object.setImg(imgs[i]);
            object.setTitle("测试一下而已 " + i);
            data.add(object);
        }
        return data;
    }
}
