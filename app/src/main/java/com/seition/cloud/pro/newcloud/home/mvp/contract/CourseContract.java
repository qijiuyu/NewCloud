package com.seition.cloud.pro.newcloud.home.mvp.contract;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.jess.arms.base.bean.DataBean;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.seition.cloud.pro.newcloud.app.PayResponse;
import com.seition.cloud.pro.newcloud.app.bean.Share;
import com.seition.cloud.pro.newcloud.app.bean.bind.FaceSence;
import com.seition.cloud.pro.newcloud.app.bean.bind.FaceStatus;
import com.seition.cloud.pro.newcloud.app.bean.common.Section;
import com.seition.cloud.pro.newcloud.app.bean.config.FreeCourseNotLoginWatchVideo;
import com.seition.cloud.pro.newcloud.app.bean.coupon.CouponBean;
import com.seition.cloud.pro.newcloud.app.bean.course.CourseSeition;
import com.seition.cloud.pro.newcloud.app.bean.course.CourseSeitionVideo;
import com.seition.cloud.pro.newcloud.app.bean.course.CourseVideoFreeTime;
import com.seition.cloud.pro.newcloud.app.bean.course.SeitionDetailsBean;
import com.seition.cloud.pro.newcloud.app.bean.download.CourseCacheBean;
import com.seition.cloud.pro.newcloud.app.bean.examination.Examination;
import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnline;
import com.seition.cloud.pro.newcloud.app.bean.live.VH;
import com.seition.cloud.pro.newcloud.home.mvp.ui.course.adapter.CourseSeitionVideoItem;

import java.util.ArrayList;

import io.reactivex.Observable;


public interface CourseContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void setPlayTime(int playTime);

        void showCourse(CourseOnline data);

        void toDownload(CourseCacheBean course);

        void playVideo(CourseSeitionVideo video);

        void setIsCollect(boolean isCollect);

        void share(Share data);

        void start(CourseOnline course);

        void start(CourseOnline course, CourseSeitionVideo item, Section section);

        void start(Section section);

        void showFaceSaveStatus(FaceStatus status);
    }

    interface CourseCardView extends IView {
        void showCoupon(CouponBean data);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<Examination> getCourseExamInfo(int paper_id, int exams_type);

        Observable<SeitionDetailsBean> getLiveSeitionDetails(String courseId, String seitionId);

        Observable<VH> getVHId();

        Observable<CourseOnline> getCourseDetails(String courseId, boolean isCache);

        Observable<CourseOnline> getLiveDetails(String courseId, boolean isCache);

        Observable<FreeCourseNotLoginWatchVideo> getNotLoginWatchFreeVideo();

        Observable<CourseSeition> getCourseSeitionList(String courseId, boolean isCache);

        Observable<DataBean> collectCourse(int type, String source_id);

        Observable<Share> getShare(String type, String vid, String mhm_id);

        Observable<CouponBean> getExchangeCard(String coupon_code, int mhm_id, double price, String vid);

        Observable<DataBean> cancelExchangeCard(String coupon_code, int mhm_id);

        Observable<PayResponse> rechargeCardUse(String pay_for, String card_number);

        Observable<DataBean> addStudyRecord(String vid, String sid, long time);

        Observable<CourseVideoFreeTime> getVideoFreeTime(String id);

        Observable<FaceSence> getFaceSence();

        Observable<FaceStatus> getFaceSaveStatus();
    }

    interface LiveSeitionView extends IView {
        void showSeition(ArrayList<Section> sections);

        void toBuySection(Section section);
    }

    interface SeitionView extends IView {
        void showSeition(ArrayList<MultiItemEntity> seitions);
    }

    interface CourseDownloadView extends IView {
        void showSeition(ArrayList<MultiItemEntity> seitions);
    }

    interface CourseListView extends IView {
    }
}
