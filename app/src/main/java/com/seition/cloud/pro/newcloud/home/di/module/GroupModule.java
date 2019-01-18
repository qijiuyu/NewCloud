package com.seition.cloud.pro.newcloud.home.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.seition.cloud.pro.newcloud.home.mvp.contract.GroupContract;
import com.seition.cloud.pro.newcloud.home.mvp.model.GroupModel;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.GroupApplyMemberHorizontalListAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.GroupMemberHorizontalListAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.GroupMemberRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.GroupRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.GroupTopicRecyclerAdapter;

import dagger.Module;
import dagger.Provides;


@Module
public class GroupModule {
    //    private Context context;
    private GroupContract.View view;
    private GroupContract.fView fView;
    private GroupContract.GroupOperationView operationView;

    private GroupContract.GroupTopicView topicView;

    private GroupContract.GroupMemberView memberView;

    /**
     * 构建GroupModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public GroupModule(GroupContract.View view) {
        this.view = view;

    }

    public GroupModule(GroupContract.fView fViewview) {
        this.fView = fViewview;
    }

    public GroupModule(GroupContract.GroupOperationView groupOperationView) {
        this.operationView = groupOperationView;
    }

    public GroupModule(GroupContract.GroupTopicView groupTopicView) {
        this.topicView = groupTopicView;
    }

    public GroupModule(GroupContract.GroupMemberView memberView) {
        this.memberView = memberView;
    }

//    @Provides
//    @Singleton
//    Context provideApplicationContext() {
//        return context;
//    }

    @ActivityScope
    @Provides
    GroupContract.View provideGroupView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    GroupContract.Model provideGroupModel(GroupModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    GroupContract.fView provideGroupfView() {
        return this.fView;
    }

    @ActivityScope
    @Provides
    GroupContract.fModel provideGroupfModel(GroupModel model) {
        return model;
    }


    @ActivityScope
    @Provides
    GroupRecyclerAdapter provideUserAdapter() {
        return new GroupRecyclerAdapter();
    }


    @ActivityScope
    @Provides
    GroupContract.GroupOperationView provideGroupOperationView() {
        return this.operationView;
    }

    @ActivityScope
    @Provides
    GroupContract.GroupOperationModel provideGroupOperationModel(GroupModel model) {
        return model;
    }


    @ActivityScope
    @Provides
    GroupContract.GroupTopicView provideGroupTopicView() {
        return this.topicView;
    }

    @ActivityScope
    @Provides
    GroupContract.GroupTopicModel provideGroupTopivModel(GroupModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    GroupTopicRecyclerAdapter provideTopicAdapter() {
        return new GroupTopicRecyclerAdapter();
    }


    @ActivityScope
    @Provides
    GroupContract.GroupMemberView provideGroupMemberView() {
        return this.memberView;
    }

    @ActivityScope
    @Provides
    GroupMemberRecyclerAdapter provideMemberAdapter() {
        return new GroupMemberRecyclerAdapter();
    }

    @ActivityScope
    @Provides
    GroupMemberHorizontalListAdapter provideMemberListAdapter() {
        return new GroupMemberHorizontalListAdapter();
    }

    @ActivityScope
    @Provides
    GroupApplyMemberHorizontalListAdapter provideApplyMemberListAdapter() {
        return new GroupApplyMemberHorizontalListAdapter();
    }


}