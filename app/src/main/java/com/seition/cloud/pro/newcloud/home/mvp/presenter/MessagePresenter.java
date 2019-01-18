package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.bean.DataBean;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.liaoinstan.springview.widget.SpringView;
import com.seition.cloud.pro.newcloud.app.bean.message.Arr_MessageLatterItem;
import com.seition.cloud.pro.newcloud.app.bean.message.MessageLetter;
import com.seition.cloud.pro.newcloud.app.bean.message.MessageLetterLast;
import com.seition.cloud.pro.newcloud.app.utils.AdapterViewUtils;
import com.seition.cloud.pro.newcloud.home.mvp.contract.MessageContract;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.message.adapter.MessageChatAdapter;

import java.util.ArrayList;
import java.util.Collections;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class MessagePresenter extends BasePresenter<MessageContract.Model, MessageContract.View>
        implements SpringView.OnFreshListener
        , BaseQuickAdapter.OnItemClickListener
        , BaseQuickAdapter.OnItemChildClickListener {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    MessageChatAdapter adapter;

    private MessageLetter messageLetter;

    public MessageLetter getMessageLetter() {
        return messageLetter;
    }

    public void setMessageLetter(MessageLetter messageLetter) {
        this.messageLetter = messageLetter;
    }

    public void initAdapterListener() {
        setListener(adapter);
    }

    private void setListener(BaseQuickAdapter adapter) {
        adapter.setOnItemClickListener(this);
        adapter.setOnItemChildClickListener(this);
    }

    @Inject
    public MessagePresenter(MessageContract.Model model, MessageContract.View rootView) {
        super(model, rootView);
    }

    public void replyMsg(String content) {
        if (content == null || content.trim().isEmpty()) return;
        mModel.replyMsg(content, messageLetter.getList_id(), messageLetter.getAnd_user_info().getUid() + "")
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<DataBean>(mErrorHandler) {
                    @Override
                    public void onNext(DataBean data) {
                        data.getCode();
                        getChatList();
                    }
                });
    }

    public void getChatList() {
        mModel.getChatList(messageLetter.getList_id())
                .subscribeOn(Schedulers.io())
//                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<Arr_MessageLatterItem>(mErrorHandler) {
                    @Override
                    public void onNext(Arr_MessageLatterItem data) {
                        ArrayList<MessageLetterLast> list = data.getData();
                        Collections.reverse(list);//对集合中的数据进行倒序排序
                        adapter.setNewData(list);
                        if (list.size() == 0)
                            adapter.setEmptyView(AdapterViewUtils.getEmptyViwe(mApplication));
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

    @Override
    public void onRefresh() {
    }

    @Override
    public void onLoadmore() {
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
