package com.seition.cloud.pro.newcloud.home.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.seition.cloud.pro.newcloud.home.di.module.GroupModule;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.group.activity.GroupActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.group.activity.GroupCreateActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.group.activity.GroupDetailsActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.group.activity.GroupMemberListActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.group.activity.GroupOwnerActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.group.activity.GroupTopicListActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.group.fragment.GroupFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.group.fragment.GroupOwnerFragment;

import dagger.Component;

@ActivityScope
@Component(modules =GroupModule.class, dependencies = AppComponent.class)
public interface GroupComponent {
    void inject(GroupActivity groupActivity);
    void inject(GroupCreateActivity groupCreateActivity);
    void inject(GroupDetailsActivity groupDetailsActivity);
    void inject(GroupMemberListActivity groupMemberListActivity);
    void inject(GroupOwnerActivity groupOwnerActivity);
    void inject(GroupTopicListActivity groupTopicListActivity);
    void inject(GroupFragment groupFragment);
    void inject(GroupOwnerFragment groupOwnerFragment);
}