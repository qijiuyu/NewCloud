package com.seition.cloud.pro.newcloud.home.mvp.ui.course.adapter;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.seition.cloud.pro.newcloud.app.bean.download.DownloadBean;
import com.seition.cloud.pro.newcloud.app.config.MessageConfig;

/**
 * Created by addis on 2018/4/9.
 */

public class CourseSeitionVideoDownloadItem implements MultiItemEntity {
    public DownloadBean video;

    public CourseSeitionVideoDownloadItem(DownloadBean video) {
        this.video = video;
    }


    @Override
    public int getItemType() {
        return MessageConfig.VIDEO_ITEM;
    }
}
