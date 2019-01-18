package com.seition.cloud.pro.newcloud.app.bean;

import com.jess.arms.base.bean.DataBean;

import java.util.ArrayList;

/**
 * Created by addis on 2016/12/13.
 */

public class AdvertBean extends DataBean<ArrayList<AdvertBean>>{
    private String banner;
    private String bannerurl;

    public String getBanner_title() {
        return banner_title;
    }

    public void setBanner_title(String banner_title) {
        this.banner_title = banner_title;
    }

    private String banner_title;


    public void setBannerurl(String bannerurl) {
        this.bannerurl = bannerurl;
    }

    public String getBannerurl() {
        return bannerurl;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getBanner() {
        return banner;
    }
}
