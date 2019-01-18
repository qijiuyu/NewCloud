package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.jess.arms.widget.statue.MultiStateView;
import com.seition.cloud.pro.newcloud.app.bean.note.Note;
import com.seition.cloud.pro.newcloud.app.bean.note.NoteComment;
import com.seition.cloud.pro.newcloud.app.bean.note.NoteComments;
import com.seition.cloud.pro.newcloud.app.bean.note.Notes;
import com.seition.cloud.pro.newcloud.app.utils.AdapterViewUtils;
import com.seition.cloud.pro.newcloud.home.mvp.contract.UserContract;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.NoteCommentRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.NoteRecyclerAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class UserNoteListPresenter extends BasePresenter<UserContract.Model, UserContract.View>{
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    NoteRecyclerAdapter adapter;


    @Inject
    NoteCommentRecyclerAdapter commentRecyclerAdapter;
    @Inject
    public UserNoteListPresenter(UserContract.Model model, UserContract.View rootView) {
        super(model, rootView);
    }
    private int page =1 ,count =6;
    boolean isFirst = true;
    public  void getMyNotes(boolean pull){
        if (pull ) {//默认在第一次下拉刷新时使用缓存
            page = 1;
        }else {
            page++;
        }
        mModel.getMyNotes(  page, count)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        if(mRootView!=null){
                            mRootView.hideLoading();
                            mRootView.showStateViewState(MultiStateView.STATE_CONTENT);
                        }
                    }
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<Notes>(mErrorHandler) {
                    @Override
                    public void onNext(Notes data) {
                        ArrayList<Note> datas = data.getData();


                        if(pull) {
                            adapter.setNewData(datas);
                            if(datas.size()>0) {
                                if (datas.size()<count) {
                                    if (adapter.getFooterViewsCount()==0)
                                        adapter.addFooterView(AdapterViewUtils.getNoDataViwe(mApplication));
                                    mRootView.showSpingViewFooterEnable(false);//springView不可上拉
                                }
                                else {
                                    adapter.removeAllFooterView();
                                    mRootView.showSpingViewFooterEnable(true);//springView可上拉
                                }
                            }
                            else
                                adapter.setEmptyView(AdapterViewUtils.getEmptyViwe(mApplication));
                        }
                        else {
                            adapter.addData(datas);
                            if (datas.size() <count) {
                                if (adapter.getFooterViewsCount()==0)
                                    adapter.addFooterView(AdapterViewUtils.getNoDataViwe(mApplication));
                                mRootView.showSpingViewFooterEnable(false);
                            } else {
                                mRootView.showSpingViewFooterEnable(true);
                            }
                        }
                 /*
                        adapter.setNewData(datas);

                        if(adapter.getItemCount()>0)
                            mRootView.showStateViewState(MultiStateView.STATE_CONTENT);
                        else
                            mRootView.showStateViewState(MultiStateView.STATE_EMPTY);
                        mRootView.hideLoading();*/
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }

    int pid;
    int ntype;
    public  void getMyNoteComments(int pid,int ntype,boolean iscache){
        this.pid = pid;
        this.ntype = ntype;

        mModel.getMyNoteComments(pid,ntype, iscache)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        if(mRootView!=null)
                            mRootView.hideLoading();
                    }
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<NoteComments>(mErrorHandler) {
                    @Override
                    public void onNext(NoteComments data) {
                        ArrayList<NoteComment> studyRecords = data.getData();
                        if (studyRecords.size() > 0)
                            commentRecyclerAdapter.setNewData(studyRecords);
                        else commentRecyclerAdapter.setEmptyView(AdapterViewUtils.getEmptyViwe(mApplication));
                        if(commentRecyclerAdapter.getItemCount()>0)
                            mRootView.showStateViewState(MultiStateView.STATE_CONTENT);
                        else
                            mRootView.showStateViewState(MultiStateView.STATE_EMPTY);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }

    public  void addNoteComments(int pid , String content, int  kzid){
        mModel.addNoteComments(pid,content, kzid)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        if(mRootView!=null)
                            mRootView.hideLoading();
                    }
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<NoteComment>(mErrorHandler) {
                    @Override
                    public void onNext(NoteComment data) {
                        NoteComment comment = data.getData();

                        mRootView.showMessage(data.getMsg());
                        if(data.getCode() ==1 )
                            getMyNoteComments(pid,ntype,true);
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
