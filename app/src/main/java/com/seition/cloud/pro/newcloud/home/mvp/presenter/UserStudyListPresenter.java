package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;

import com.jess.arms.base.bean.DataBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.jess.arms.widget.statue.MultiStateView;
import com.seition.cloud.pro.newcloud.app.bean.study.StudyRecord;
import com.seition.cloud.pro.newcloud.app.bean.study.StudyRecordContent;
import com.seition.cloud.pro.newcloud.app.bean.study.StudyRecordSection;
import com.seition.cloud.pro.newcloud.app.utils.TimeUtils;
import com.seition.cloud.pro.newcloud.home.mvp.contract.UserContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class UserStudyListPresenter extends BasePresenter<UserContract.Model, UserContract.StudyView>{
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public UserStudyListPresenter(UserContract.Model model, UserContract.StudyView rootView) {
        super(model, rootView);
    }


    public  void getStudyRecord(boolean iscache){
        mModel.getStudyRecord( iscache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        if(mRootView!=null) {
                            mRootView.hideLoading();
                            mRootView.showStateViewState(MultiStateView.STATE_CONTENT);
                        }
                    }
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<StudyRecord>(mErrorHandler) {
                    @Override
                    public void onNext(StudyRecord data) {
                        ArrayList<StudyRecordContent> studyRecords = data.getData();
                        ArrayList<StudyRecordSection> studyRecordSections = new ArrayList<>();

                        Map<String ,ArrayList<StudyRecordContent>> maps= new HashMap<>();
                        for(int i =0;i<studyRecords.size();i++){
                            String timeKey = TimeUtils.stampToDate(studyRecords.get(i).getCtime(),TimeUtils.Format_TIME3);
                            if(maps.containsKey(timeKey)  ) {
                                ArrayList<StudyRecordContent> mapStudyContent = new ArrayList<>();
                                mapStudyContent = maps.get(timeKey);
                                mapStudyContent.add(studyRecords.get(i));
                                maps.put(timeKey, mapStudyContent);
                            }else{
                                ArrayList<StudyRecordContent> mapStudyContent = new ArrayList<>();
                                mapStudyContent.add(studyRecords.get(i));
                                maps.put(timeKey, mapStudyContent);
                            }
                        }

                        Iterator<Map.Entry<String ,ArrayList<StudyRecordContent>>> iterator1 = maps.entrySet().iterator();
                        Map.Entry<String ,ArrayList<StudyRecordContent>> entry;
                        while (iterator1.hasNext()) {
                            entry = iterator1.next();
                            studyRecordSections.add(new StudyRecordSection(true,   entry.getKey(), true));
                            for(int i=0 ;i<entry.getValue().size();i++){
                                studyRecordSections.add(new StudyRecordSection(entry.getValue().get(i) ));
                            }
                        }
                        mRootView.setData(studyRecordSections);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }

    public  void deleteStudyRecord(String sid){
        mModel.deleteStudyRecord( sid)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        if(mRootView!=null) {
                            mRootView.hideLoading();
                            mRootView.showStateViewState(MultiStateView.STATE_CONTENT);
                        }
                    }
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<DataBean>(mErrorHandler) {
                    @Override
                    public void onNext(DataBean data) {
                            mRootView.deleteSrudyRecord(data.getCode() == 1?true:false);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
