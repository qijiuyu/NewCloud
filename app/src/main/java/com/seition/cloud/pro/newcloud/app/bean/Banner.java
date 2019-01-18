package com.seition.cloud.pro.newcloud.app.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by addis on 2016/12/13.
 */

public class Banner implements Serializable {
    private String msg;
    private int code;
    //    private ArrayList<Data> data;
    private ArrayList<AdvertBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ArrayList<AdvertBean> getData() {
        return data;
    }

    public void setData(ArrayList<AdvertBean> data) {
        this.data = data;
    }
}
