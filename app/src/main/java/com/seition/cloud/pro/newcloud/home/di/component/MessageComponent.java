package com.seition.cloud.pro.newcloud.home.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.seition.cloud.pro.newcloud.home.di.module.MessageModule;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.message.activity.MessageActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.message.activity.MessageChatActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.message.activity.MessageCommentActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.message.activity.MessagePrivateActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.message.activity.MessageSystemActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.message.fragment.MessageCommentFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.message.fragment.MessagePrivateFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.message.fragment.MessageSystemFragment;

import dagger.Component;

@ActivityScope
@Component(modules = MessageModule.class, dependencies = AppComponent.class)
public interface MessageComponent {
    void inject(MessageActivity activity);
    void inject(MessagePrivateActivity activity);
    void inject(MessageCommentActivity activity);
    void inject(MessageSystemActivity activity);
    void inject(MessageChatActivity activity);

    void inject(MessagePrivateFragment privateFragment);
    void inject(MessageSystemFragment systemFragment);
    void inject(MessageCommentFragment commentFragment);
}