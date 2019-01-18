package com.seition.cloud.pro.newcloud.app.bean.bind;

import com.jess.arms.base.bean.DataBean;

import java.util.ArrayList;

/**
 * Created by xzw on 2018-07-04.
 */

public class FaceSence extends DataBean<FaceSence> {

    private int is_open;
    ArrayList<String > open_scene;

    public int getIs_open() {
        return is_open;
    }

    public void setIs_open(int is_open) {
        this.is_open = is_open;
    }

    public ArrayList<String> getOpen_scene() {
        return open_scene;
    }

    public void setOpen_scene(ArrayList<String> open_scene) {
        this.open_scene = open_scene;
    }
}
