package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.bean.DataBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.seition.cloud.pro.newcloud.app.bean.Share;
import com.seition.cloud.pro.newcloud.app.bean.bind.FaceSence;
import com.seition.cloud.pro.newcloud.app.bean.bind.FaceStatus;
import com.seition.cloud.pro.newcloud.app.bean.common.Section;
import com.seition.cloud.pro.newcloud.app.bean.config.FreeCourseNotLoginWatchVideo;
import com.seition.cloud.pro.newcloud.app.bean.course.CourseSeitionVideo;
import com.seition.cloud.pro.newcloud.app.bean.course.CourseVideoFreeTime;
import com.seition.cloud.pro.newcloud.app.bean.download.InitDownloadBean;
import com.seition.cloud.pro.newcloud.app.bean.examination.Examination;
import com.seition.cloud.pro.newcloud.app.bean.examination.MExamBean;
import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnline;
import com.seition.cloud.pro.newcloud.app.config.MessageConfig;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.home.mvp.contract.CourseContract;
import com.seition.cloud.pro.newcloud.home.mvp.ui.course.adapter.CourseSeitionVideoItem;
import com.seition.cloud.pro.newcloud.home.mvp.ui.course.popup.CourseDetailMorePopup;
import com.seition.cloud.pro.newcloud.home.mvp.ui.login.activity.LoginActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.exam.activity.ExaminationActivity;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class CoursePresenter extends BasePresenter<CourseContract.Model, CourseContract.View>
        implements CourseDetailMorePopup.OnClickListener, BaseQuickAdapter.OnItemClickListener {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    public String courseId;
    private boolean isCourseinfoCache = true;
    private boolean isLive;
    public CourseOnline course;
    boolean isNotLoginWatchFreeVideo = false;
    String sid = "";

    @Inject
    public CoursePresenter(CourseContract.Model model, CourseContract.View rootView) {
        super(model, rootView);
    }

    public void getNotLoginWatchFreeVideo() {
        mModel.getNotLoginWatchFreeVideo()
                .subscribeOn(Schedulers.io())
//                .retryWhen(new RetryWithDelay(1, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    //错误
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<FreeCourseNotLoginWatchVideo>(mErrorHandler) {
                    @Override
                    public void onNext(FreeCourseNotLoginWatchVideo data) {
                        try {
                            isNotLoginWatchFreeVideo = data.getData().getFree_course_opt() == 1;
                        } catch (Exception e) {
                            isNotLoginWatchFreeVideo = false;
                        }
                    }
                });
    }

    public void getExamInfoAndStartExam(int eid, int exams_type) {
        mRootView.showLoading();
        mModel.getCourseExamInfo(eid, exams_type)
                .subscribeOn(Schedulers.io())
//                .retryWhen(new RetryWithDelay(1, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (mRootView != null)
                        mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<Examination>(mErrorHandler) {
                    @Override
                    public void onNext(Examination data) {
                        if (mRootView == null) return;
                        if (data != null && data.getData() != null)
                            startExam(data.getData(), exams_type);
                        else
                            mRootView.showMessage("没有数据");
                    }
                });
    }

    private void startExam(MExamBean examBean, int exams_type) {
        if (examBean == null) {
            return;
        }
        mRootView.launchActivity(new Intent(mApplication, ExaminationActivity.class)
                .putExtra(MessageConfig.START_EXAMINATION, examBean)
                .putExtra(MessageConfig.START_EXAMINATION_IS_TEST, true)
                .putExtra(MessageConfig.START_EXAMINATION_TYPE, exams_type));
    }

    public void getCourseDetails(String courseId, boolean isLive, String sid) {
        this.courseId = courseId;
        this.isLive = isLive;
        this.sid = sid;
        if (this.isLive)
            getLiveNow();
        else
            getCourseDetails();
    }

    public void getLiveNow() {
        mRootView.showLoading();
        mModel.getLiveDetails(courseId, true)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    //错误
                    if (mRootView != null)
                        mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<CourseOnline>(mErrorHandler) {
                    @Override
                    public void onNext(CourseOnline data) {
                        course = data.getData();
                        is_collect = course.getIs_collect().equals("1") ? true : false;
                        mRootView.showCourse(course);
                        mRootView.setIsCollect(is_collect);
                        if (mRootView != null)
                            mRootView.hideLoading();
                    }
                });
    }

    public void getCourseDetails() {
        mRootView.showLoading();
        mModel.getCourseDetails(courseId, isCourseinfoCache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (mRootView != null)
                        mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<CourseOnline>(mErrorHandler) {
                    @Override
                    public void onNext(CourseOnline data) {
                        //拿到数据
                        course = data.getData();
                        is_collect = course.getIscollect().equals("1") ? true : false;
                        mRootView.setIsCollect(is_collect);
                        mRootView.showCourse(course);
                    }
                });
    }

    public void collectOffine(int type, String source_id) {
        mModel.collectCourse(type, source_id)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {

                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<DataBean>(mErrorHandler) {
                    @Override
                    public void onNext(DataBean data) {
                        if (data.getCode() == 1) {
                            is_collect = !is_collect;
                            mRootView.setIsCollect(is_collect);
                            mRootView.showMessage((is_collect ? "收藏" : "取消收藏") + "成功");
                        } else
                            mRootView.showMessage((is_collect ? "取消收藏" : "收藏") + "失败");
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.showMessage((is_collect ? "取消收藏" : "收藏") + "失败");
                    }
                });
    }

    public void getShareUrl(String type, String vid, String mhm_id) {
        mModel.getShare(type, vid, mhm_id)
                .subscribeOn(Schedulers.io())
//                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {

                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<Share>(mErrorHandler) {
                    @Override
                    public void onNext(Share data) {
                        Share share = data.getData();
                        if (data.getCode() == 1)
                            mRootView.share(share);
                    }
                });
    }

    /**
     * 获取试看的时长
     */
    public void getVideoFreeTime(String id) {
        mRootView.showLoading();
        mModel.getVideoFreeTime(id)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (mRootView != null)
                        mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<CourseVideoFreeTime>(mErrorHandler) {
                    @Override
                    public void onNext(CourseVideoFreeTime data) {
                        if (data.getData() != null)
                            mRootView.setPlayTime(data.getData().getVideo_free_time());
                    }
                });
    }

    public void addStudyRecord(String vid, String sid, long time) {
        mRootView.showLoading();
        mModel.addStudyRecord(vid, sid, time)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (mRootView != null)
                        mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<DataBean>(mErrorHandler) {
                    @Override
                    public void onNext(DataBean data) {

                    }
                });
    }

    public void getFaceSence() {
        isOpenFaceMoudle = false;
        needFaceVerify = false;
        mModel.getFaceSence()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<FaceSence>(mErrorHandler) {
                    @Override
                    public void onNext(FaceSence data) {
                        FaceSence faceSence = data.getData();
                        //拿到数据
//                        if (data.getCode() == 1) {
                        isOpenFaceMoudle = faceSence.getIs_open() == 1 ? true : false;

                        PreferenceUtil.getInstance(mApplication).saveInt("SenceOpen", faceSence.getIs_open());

                        if (faceSence != null && faceSence.getIs_open() == 1) {
                            for (int i = 0; i < faceSence.getOpen_scene().size(); i++) {
                                if (faceSence.getOpen_scene().get(i).equals("video")) {
                                    needFaceVerify = true;
                                    return;
                                }
                                needFaceVerify = false;
                            }
                        } else needFaceVerify = false;
//                        } else mRootView.showMessage(data.getMsg());
                    }
                });
    }


    public boolean isOpenFaceMoudle = false;//是否开启人脸功能
    boolean needFaceVerify = false;


    public void getFaceSaveStatus() {
        mModel.getFaceSaveStatus()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<FaceStatus>(mErrorHandler) {
                    @Override
                    public void onNext(FaceStatus data) {
//                        System.out.println("getFaceSaveStatus ");
                        FaceStatus status = data.getData();
                        if (data.getCode() == 1) {
                            mRootView.showFaceSaveStatus(status);
                        } else
                            mRootView.showMessage("人脸储存状态获取失败");
                    }
                });
    }


    public void toBuy() {
//            mRootView.launchActivity(new Intent(mApplication, BuyFragment.class).putExtra("course", course));
        if (course == null) return;
        mRootView.start(course);
    }

    private boolean is_collect;

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }


    @Override
    public void collect() {
        //收藏/取消收藏
        collectOffine(is_collect ? 0 : 1, courseId);
    }

    @Override
    public void shape() {
        getShareUrl(!isLive ? "0" : "2", courseId, sid);
    }

    @Override
    public void download() {
        //下载
        if (course.getIs_buy() != 0)
            mRootView.toDownload(InitDownloadBean.initCourseCacheBean(course));
        else mRootView.showMessage("购买课程方可下载！");
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        //播放视频
        if (mRootView == null) return;
        if (isNotLoginWatchFreeVideo && TextUtils.isEmpty(PreferenceUtil.getInstance(mApplication).getString("oauth_token", null)))
            mRootView.launchActivity(new Intent(mApplication, LoginActivity.class).putExtra("SkipToHome", false));
        else {
            Object object = adapter.getItem(position);
            if (object instanceof CourseSeitionVideoItem) {
                CourseSeitionVideoItem item = (CourseSeitionVideoItem) object;
                if (course.getIs_buy() != 0 || item.video.getIs_free() == 1 || item.video.getIs_buy() == 1) {
                    watchCourseSection(item.video);
                } else if (course.getPrice() == 0 || item.video.getCourse_hour_price() == 0)
                    if (item.video.getVideo_type() == 1)//如果是视频，需要去试看
                        watchCourseSection(item.video);
                    else
                        mRootView.showMessage("请先购买本课程才可观看！");
                else
                    mRootView.start(course, item.video, null);//这里是跳到单课时购买页
            }
        }
    }

    public void watchCourseSection(CourseSeitionVideo item) {
        setVideo(item);
        if (needFaceVerify)
            getFaceSaveStatus();
        else {
            mRootView.playVideo(item);
        }
    }

    public void start(Section section) {
        mRootView.start(course, null, section);//这里是跳到单课时购买页
    }

    CourseSeitionVideo video;

    public CourseSeitionVideo getVideo() {
        return video;
    }

    public void setVideo(CourseSeitionVideo video) {
        this.video = video;
    }
}
