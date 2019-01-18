package com.seition.cloud.pro.newcloud.app.bean.course;

import com.jess.arms.base.bean.MBaseBean;

import java.util.ArrayList;

/**
 * Created by addis on 2018/8/31.
 */
public class HomeLive extends MBaseBean {

    private ArrayList<HomeLiveCourse> live_list;
    private ArrayList<Long> live_ctime;

    public void setTime() {
        if (live_list == null || live_ctime == null) return;
        for (int i = 0; i < live_list.size(); i++) {
            if (live_ctime.size() > i)
                live_list.get(i).setTime(live_ctime.get(i));
        }
    }

    public ArrayList<HomeLiveCourse> getLive_list() {
        return live_list;
    }

    public void setLive_list(ArrayList<HomeLiveCourse> live_list) {
        this.live_list = live_list;
    }

    public ArrayList<Long> getLive_ctime() {
        return live_ctime;
    }

    public void setLive_ctime(ArrayList<Long> live_ctime) {
        this.live_ctime = live_ctime;
    }
}
