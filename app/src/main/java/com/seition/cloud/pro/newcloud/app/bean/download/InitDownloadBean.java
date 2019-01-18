package com.seition.cloud.pro.newcloud.app.bean.download;

import android.content.Context;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.seition.cloud.pro.newcloud.app.bean.course.CourseSeition;
import com.seition.cloud.pro.newcloud.app.bean.course.CourseSeitionVideo;
import com.seition.cloud.pro.newcloud.app.bean.library.LibraryItemBean;
import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnline;
import com.seition.cloud.pro.newcloud.app.config.MessageConfig;
import com.seition.cloud.pro.newcloud.app.utils.download.DBUtils;
import com.seition.cloud.pro.newcloud.home.mvp.ui.course.adapter.CourseSeitionTitleDownloadItem;
import com.seition.cloud.pro.newcloud.home.mvp.ui.course.adapter.CourseSeitionTitleItem;
import com.seition.cloud.pro.newcloud.home.mvp.ui.course.adapter.CourseSeitionVideoDownloadItem;
import com.seition.cloud.pro.newcloud.home.mvp.ui.course.adapter.CourseSeitionVideoItem;

import java.util.ArrayList;

/**
 * Created by addis on 2018/3/30.
 */

public class InitDownloadBean {

    public static CourseSeitionCacheBean initCourseSeitionCacheBean(CourseSeition bean) {
        return new CourseSeitionCacheBean(bean.getId(), bean.getVid() + "", bean.getTitle());
    }

    public static CourseCacheBean initCourseCacheBean(CourseOnline bean) {
        return new CourseCacheBean(bean.getSection_count()
                , bean.getVideo_order_count()
                , bean.getId(), bean.getVideo_title(), bean.getCover());
    }

    public static DownloadBean initDownloadBean(CourseSeitionVideo item) {
        return initDownloadBean(MessageConfig.TYPE_VIDEO
                , (long) item.getId(), (long) item.getCid(), (long) item.getPid(), item.getTitle()
                , "mp4", "", item.getVideo_address());
    }

    public static DownloadBean initDownloadBean(LibraryItemBean item) {
        return initDownloadBean(MessageConfig.TYPE_BOOK
                , (long) item.getDoc_id(), 0l, 0l, item.getTitle()
                , item.getExtension(), item.getCover(), item.getAttach());
    }

    public static DownloadBean initDownloadBean(String fileType, Long fId
            , Long cId, Long pId, String title, String extension, String cover, String url) {
        return new DownloadBean(fileType, fId, cId, pId, title, extension, cover, url);
    }

    public static DownloadBean initDownloadBean(Long key, String cacheId, int downloadId
            , String fileSavePath, Long progress, Long total
            , Long addTime, Integer state, String fileType, Long fId, Long cId, Long pId
            , String title, String extension, String cover, String url) {
        return new DownloadBean(key, cacheId, downloadId, fileSavePath, progress, total
                , addTime, state, fileType, fId, cId, pId, title
                , extension, cover, url);
    }

    public static ArrayList<MultiItemEntity> seitionDataToExpandableData(ArrayList<CourseSeition> seitions, Context mContext, boolean isDownload) {
        ArrayList<MultiItemEntity> res = new ArrayList<>();
        if (seitions != null)
            for (int i = 0; i < seitions.size(); i++)
                if (isDownload)
                    res.add(initCourseSeitionTitleDownloadItem(seitions.get(i), mContext));
                else
                    res.add(initCourseSeitionTitleItem(seitions.get(i), mContext));
        return res;
    }

    public static CourseSeitionTitleItem initCourseSeitionTitleItem(CourseSeition seition, Context mContext) {
        CourseSeitionTitleItem seitionItem = new CourseSeitionTitleItem(seition);
        ArrayList<CourseSeitionVideo> videos = seition.getChild();
        if (videos != null)
            for (int i = 0; i < videos.size(); i++)
                seitionItem.addSubItem(new CourseSeitionVideoItem(videos.get(i)));
        return seitionItem;
    }

    public static CourseSeitionTitleDownloadItem initCourseSeitionTitleDownloadItem(CourseSeition seition, Context mContext) {
        CourseSeitionTitleDownloadItem seitionItem = new CourseSeitionTitleDownloadItem(seition);
        ArrayList<CourseSeitionVideo> videos = seition.getChild();
        if (videos != null)
            for (int i = 0; i < videos.size(); i++)
                seitionItem.addSubItem(new CourseSeitionVideoDownloadItem(
                        DBUtils.init(mContext).queryCacheKeyOne(
                                InitDownloadBean.initDownloadBean(videos.get(i))))
                );
        return seitionItem;
    }
}
