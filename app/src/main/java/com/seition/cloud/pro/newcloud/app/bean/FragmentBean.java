package com.seition.cloud.pro.newcloud.app.bean;


import android.support.v4.app.Fragment;

import com.jess.arms.base.bean.MBaseBean;

/**
 * Created by addis on 2017/5/19.
 */

public class FragmentBean extends MBaseBean {

    /**
     * 用于版本控制，修改一次，请把这个数值+1，并在此备注修改人，修改时间等信息
     * 更改内容： 第一次创建
     * updata name : addis
     * updata time : 2017/5/19
     */
    private static final long serialVersionUID = 2L;
    private static final String TAG = "FragmentBean";

    public FragmentBean(String title, Fragment fragment) {
        setFragment(fragment);
        setTitle(title);
    }

    private String title;
    private Fragment fragment;

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
