package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;
import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.liaoinstan.springview.widget.SpringView;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.bind.FaceSence;
import com.seition.cloud.pro.newcloud.app.bean.bind.FaceStatus;
import com.seition.cloud.pro.newcloud.app.bean.examination.ARRExamBean;
import com.seition.cloud.pro.newcloud.app.bean.examination.ARR_TestClassify;
import com.seition.cloud.pro.newcloud.app.bean.examination.Examination;
import com.seition.cloud.pro.newcloud.app.bean.examination.MExamBean;
import com.seition.cloud.pro.newcloud.app.bean.examination.Moudles;
import com.seition.cloud.pro.newcloud.app.bean.examination.TestClassify;
import com.seition.cloud.pro.newcloud.app.config.MessageConfig;
import com.seition.cloud.pro.newcloud.app.listener.ExamListener;
import com.seition.cloud.pro.newcloud.app.popupwindow.BasePopWindow;
import com.seition.cloud.pro.newcloud.app.popupwindow.CategoryPickPopupWindow;
import com.seition.cloud.pro.newcloud.app.popupwindow.ExamCategoryPickPopupWindow;
import com.seition.cloud.pro.newcloud.app.popupwindow.ExamLevelPopWindow;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.home.mvp.contract.ExamContract;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.exam.activity.ExaminationActivity;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class ExamPresenter extends BasePresenter<ExamContract.ExamModel, ExamContract.ExamListView>
        implements BaseQuickAdapter.OnItemClickListener, SpringView.OnFreshListener, ExamListener
        , ExamCategoryPickPopupWindow.OnDialogItemClickListener, ExamLevelPopWindow.OnDialogItemClickListener {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    public Moudles moudle;
    public int page = 1;
    public int count = 10;
    public String subject_id;
    public int level;

    boolean isClassifyListCache = true, isExamListCache = true;

    @Inject
    public ExamPresenter(ExamContract.ExamModel model, ExamContract.ExamListView rootView) {
        super(model, rootView);
    }

    public void setMoudle(Moudles moudle) {
        this.moudle = moudle;
    }

    public void examClassifyList() {
        mRootView.showLoading();
        mModel.examClassifyList()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<ARR_TestClassify>(mErrorHandler) {
                    @Override
                    public void onNext(ARR_TestClassify data) {
                        if (mRootView == null) return;
                        mRootView.setClassifyListData(data.getData());
                        mRootView.hideLoading();
                    }
                });
    }

    public void getExamList(boolean pullToRefresh) {
//        mRootView.showLoading();
        mModel.examList(page, count, subject_id, level, moudle.getExams_module_id())
                .subscribeOn(Schedulers.io())
//                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<ARRExamBean>(mErrorHandler) {
                    @Override
                    public void onNext(ARRExamBean data) {
                        if (mRootView == null) return;
                        if (pullToRefresh) {
                            mRootView.setExams(data.getData());
                        } else {
                            mRootView.addExams(data.getData());
                        }
//                        mRootView.hideLoading();
                    }
                });
    }

    public void getExamInfoAndStartExam(MExamBean examBean, int exams_type) {
        mRootView.showLoading();
        mModel.getExamInfo(examBean.getExams_paper_id(), exams_type, examBean.getExams_users_id() == 0 ? "" : examBean.getExams_users_id() + "")
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                   /* if (mRootView != null)
                        mRootView.hideLoading();*/
                })
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        //item被点击
        mRootView.showExamInfo((MExamBean) adapter.getItem(position));
    }

    @Override
    public void onRefresh() {
        page = 1;
        getExamList(true);
    }

    @Override
    public void onLoadmore() {
        page++;
        getExamList(false);
    }

    MExamBean meb;

    public MExamBean getMeb() {
        return meb;
    }

    public void setMeb(MExamBean meb) {
        this.meb = meb;
    }

    @Override
    public void toExam(MExamBean meb, int type) {
        //从这里进入进入考试
        //考试类型 1练习模式 2 考试模式
        switch (type) {
            case R.id.test:
                getExamInfoAndStartExam(meb, 1);
                break;
            case R.id.exam:
                setMeb(meb);
                if (needFaceVerify)
                    getFaceSaveStatus();
                else
                    getExamInfoAndStartExam(meb, 2);
//                    mRootView.playVideo(item.video);

                break;
        }
    }

    public void getFaceSence() {//{"data":{"is_open":0,"open_scene":[]},"code":1,"msg":"获取成功"}
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
                                if (faceSence.getOpen_scene().get(i).equals("exams"))
                                    needFaceVerify = true;

                            }
                        }

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
                        System.out.println("getFaceSaveStatus ");
                        FaceStatus status = data.getData();
                        if (data.getCode() == 1) {
                            mRootView.showFaceSaveStatus(status);
                        } else
                            mRootView.showMessage("人脸储存状态获取失败");

                    }
                });
    }

    @Override
    public void onWindowItemClick(Object p) {
        TestClassify category = (TestClassify) p;
        if (category == null) return;
        subject_id = category.getSubject_id();
        getExamList(true);
    }

    @Override
    public void onDialogItemClick(BasePopWindow popWindow, int levelId, int position) {
        level = levelId;
        getExamList(true);
    }
}
