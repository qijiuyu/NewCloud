package com.seition.cloud.pro.newcloud.home.mvp.ui.course.adapter;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.seition.cloud.pro.newcloud.app.bean.course.CourseSeitionVideo;
import com.seition.cloud.pro.newcloud.app.config.MessageConfig;

/**
 * Created by addis on 2018/4/9.
 */

public class CourseSeitionVideoItem implements MultiItemEntity {
    public CourseSeitionVideo video;

    public CourseSeitionVideoItem(CourseSeitionVideo video) {
        this.video = video;
    }


    @Override
    public int getItemType() {
        return MessageConfig.VIDEO_ITEM;
    }
}
