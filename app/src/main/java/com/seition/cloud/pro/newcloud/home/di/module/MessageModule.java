package com.seition.cloud.pro.newcloud.home.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.seition.cloud.pro.newcloud.home.mvp.contract.MessageContract;
import com.seition.cloud.pro.newcloud.home.mvp.model.MessageModel;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.message.adapter.MessageChatAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.MessageCommentRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.MessagePrivateRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.MessageSystemRecyclerAdapter;

import dagger.Module;
import dagger.Provides;


@Module
public class MessageModule {
    private MessageContract.View view;
    private MessageContract.FragmentView fragmentView;

    /**
     * 构建MessageModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MessageModule(MessageContract.View view) {
        this.view = view;
    }

    public MessageModule(MessageContract.FragmentView fragmentView) {
        this.fragmentView = fragmentView;
    }

    @ActivityScope
    @Provides
    MessageContract.View provideMessageView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MessageContract.FragmentView provideFragmentView() {
        return this.fragmentView;
    }


    @ActivityScope
    @Provides
    MessageContract.Model provideMessageModel(MessageModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    MessagePrivateRecyclerAdapter provideMessagePrivateAdapter() {
        return new MessagePrivateRecyclerAdapter();
    }

    @ActivityScope
    @Provides
    MessageSystemRecyclerAdapter provideMessageSystemAdapter() {
        return new MessageSystemRecyclerAdapter();
    }

    @ActivityScope
    @Provides
    MessageCommentRecyclerAdapter provideMessageCommentAdapter() {
        return new MessageCommentRecyclerAdapter();
    }

    @ActivityScope
    @Provides
    MessageChatAdapter provideMessageChatAdapter() {
        return new MessageChatAdapter();
    }

}