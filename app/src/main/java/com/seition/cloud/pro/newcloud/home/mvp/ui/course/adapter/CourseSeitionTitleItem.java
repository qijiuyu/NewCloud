package com.seition.cloud.pro.newcloud.home.mvp.ui.course.adapter;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.seition.cloud.pro.newcloud.app.bean.course.CourseSeition;
import com.seition.cloud.pro.newcloud.app.bean.download.CourseSeitionCacheBean;
import com.seition.cloud.pro.newcloud.app.bean.download.InitDownloadBean;
import com.seition.cloud.pro.newcloud.app.config.MessageConfig;

/**
 * Created by addis on 2018/4/9.
 */

public class CourseSeitionTitleItem extends AbstractExpandableItem<CourseSeitionVideoItem> implements MultiItemEntity {
    public CourseSeitionCacheBean mSeition;

    public CourseSeitionTitleItem(CourseSeition seition) {
        this.mSeition = InitDownloadBean.initCourseSeitionCacheBean(seition);
    }

    public CourseSeitionTitleItem(CourseSeitionCacheBean seition) {
        this.mSeition = seition;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return MessageConfig.SEITION_ITEM;
    }
}