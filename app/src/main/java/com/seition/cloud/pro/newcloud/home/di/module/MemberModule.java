package com.seition.cloud.pro.newcloud.home.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.seition.cloud.pro.newcloud.home.mvp.contract.MemberContract;
import com.seition.cloud.pro.newcloud.home.mvp.model.MemberModel;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.CourseGridRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.MemberTypeGridRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.MemberTypeRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.MemberUserRecyclerAdapter;

import dagger.Module;
import dagger.Provides;


@Module
public class MemberModule {
    private MemberContract.View view;
    private MemberContract.MemberView memberView;
    private MemberContract.MemberCourseView memberCourseView;
    private MemberContract.MemberRechargeView memberRechargeView;

    /**
     * 构建MemberModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MemberModule(MemberContract.View view) {
        this.view = view;
    }

    public MemberModule(MemberContract.MemberView memberView) {
        this.memberView = memberView;
    }
    public MemberModule(MemberContract.MemberCourseView memberView) {
        this.memberCourseView = memberView;
    }
    public MemberModule(MemberContract.MemberRechargeView memberView) {
        this.memberRechargeView = memberView;
    }
    @ActivityScope
    @Provides
    MemberContract.View provideMemberView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MemberContract.MemberView provideMemberActivityView() {
        return this.memberView;
    }

    @ActivityScope
    @Provides
    MemberContract.MemberCourseView provideMemberCourseView() {
        return this.memberCourseView;
    }
    @ActivityScope
    @Provides
    MemberContract.MemberRechargeView provideMemberRechargeView() {
        return this.memberRechargeView;
    }
    @ActivityScope
    @Provides
    MemberContract.Model provideMemberModel(MemberModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    MemberTypeRecyclerAdapter provideMemberAdapter() {
        return new MemberTypeRecyclerAdapter();
    }

    @ActivityScope
    @Provides
    MemberUserRecyclerAdapter provideMemberUserAdapter() {
        return new MemberUserRecyclerAdapter();
    }

    @ActivityScope
    @Provides
    CourseGridRecyclerAdapter provideMemberCourseAdapter() {
        return new CourseGridRecyclerAdapter();
    }

    @ActivityScope
    @Provides
    MemberTypeGridRecyclerAdapter provideMemberTypeAdapter() {
        return new MemberTypeGridRecyclerAdapter();
    }
}