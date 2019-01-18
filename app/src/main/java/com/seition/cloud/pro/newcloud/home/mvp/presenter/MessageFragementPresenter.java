package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.liaoinstan.springview.widget.SpringView;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.message.Arr_MessageComment;
import com.seition.cloud.pro.newcloud.app.bean.message.Arr_MessageLatter;
import com.seition.cloud.pro.newcloud.app.bean.message.Arr_MessageSystem;
import com.seition.cloud.pro.newcloud.app.bean.message.MessageComment;
import com.seition.cloud.pro.newcloud.app.bean.message.MessageLetter;
import com.seition.cloud.pro.newcloud.app.bean.message.MessageSystem;
import com.seition.cloud.pro.newcloud.app.utils.AdapterViewUtils;
import com.seition.cloud.pro.newcloud.home.mvp.contract.MessageContract;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.message.activity.MessageChatActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.MessageCommentRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.MessagePrivateRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.MessageSystemRecyclerAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class MessageFragementPresenter extends BasePresenter<MessageContract.Model, MessageContract.FragmentView>
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
    MessageCommentRecyclerAdapter commentRecyclerAdapter;

    @Inject
    MessagePrivateRecyclerAdapter privateRecyclerAdapter;

    @Inject
    MessageSystemRecyclerAdapter systemRecyclerAdapter;

    @Inject
    public MessageFragementPresenter(MessageContract.Model model, MessageContract.FragmentView rootView) {
        super(model, rootView);
    }

    public void initAdapterListener() {
        setListener(commentRecyclerAdapter);
        setListener(privateRecyclerAdapter);
        setListener(systemRecyclerAdapter);
    }

    private void setListener(BaseQuickAdapter adapter) {
        adapter.setOnItemClickListener(this);
        adapter.setOnItemChildClickListener(this);
    }

    public void getMessageLetters() {
        mModel.getMessagePrivateLetters()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<Arr_MessageLatter>(mErrorHandler) {
                    @Override
                    public void onNext(Arr_MessageLatter data) {
                        ArrayList<MessageLetter> messageLetters = data.getData();
                        if (messageLetters.size() > 0)
                            privateRecyclerAdapter.setNewData(messageLetters);
                        else
                            privateRecyclerAdapter.setEmptyView(AdapterViewUtils.getEmptyViwe(mApplication));
                    }
                });
    }

    public void getMessageComments() {
        mModel.getMessageComment()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<Arr_MessageComment>(mErrorHandler) {
                    @Override
                    public void onNext(Arr_MessageComment data) {
                        ArrayList<MessageComment> messageComments = data.getData();
                        if (messageComments.size() > 0)
                            commentRecyclerAdapter.setNewData(messageComments);
                        else
                            commentRecyclerAdapter.setEmptyView(AdapterViewUtils.getEmptyViwe(mApplication));
                    }
                });
    }

    public void getMessageSystems() {
        mModel.getMessageSystems()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<Arr_MessageSystem>(mErrorHandler) {
                    @Override
                    public void onNext(Arr_MessageSystem data) {
                        ArrayList<MessageSystem> messageSystems = data.getData();
                        if (messageSystems.size() > 0)
                            systemRecyclerAdapter.setNewData(messageSystems);
                        else
                            systemRecyclerAdapter.setEmptyView(AdapterViewUtils.getEmptyViwe(mApplication));
                    }
                });
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadmore() {
        getMessageComments();
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
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.message_send_back:
                //评论回复操作
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        //进入详情页
        if (adapter instanceof MessagePrivateRecyclerAdapter) {//私信详情
            MessageLetter messageLetter = (MessageLetter) adapter.getItem(position);
            Bundle bundle = new Bundle();
            bundle.putSerializable("messageLetter", messageLetter);
            mRootView.launchActivity(new Intent(mApplication, MessageChatActivity.class).putExtras(bundle));
        } else if (adapter instanceof MessageCommentRecyclerAdapter) {//评论详情
//            MessageLetter messageLetter = (MessageLetter) adapter.getItem(position);
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("messageLetter", messageLetter);
//            mRootView.launchActivity(new Intent(mApplication, MessageChatActivity.class).putExtras(bundle));
        } else if (adapter instanceof MessageSystemRecyclerAdapter) {//系统消息详情
//            MessageLetter messageLetter = (MessageLetter) adapter.getItem(position);
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("messageLetter", messageLetter);
//            mRootView.launchActivity(new Intent(mApplication, MessageChatActivity.class).putExtras(bundle));
        }
    }
}
