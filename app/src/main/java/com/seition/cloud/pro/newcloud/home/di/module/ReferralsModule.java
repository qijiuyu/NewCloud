package com.seition.cloud.pro.newcloud.home.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.seition.cloud.pro.newcloud.home.mvp.contract.ReferralsContract;
import com.seition.cloud.pro.newcloud.home.mvp.contract.RegisterContract;
import com.seition.cloud.pro.newcloud.home.mvp.model.ReferralsModel;
import com.seition.cloud.pro.newcloud.home.mvp.model.RegisterModel;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.referrals.adapter.ReferralsListAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.CourseLiveRecyclerAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by addis on 2018/11/23.
 */
@Module
public class ReferralsModule {
    private ReferralsContract.View view;

    /**
     * 构建ReferralsModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     * @param view
     */
    public ReferralsModule(ReferralsContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ReferralsContract.View provideReferralsView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ReferralsContract.Model provideReferralsModel(ReferralsModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    ReferralsListAdapter provideReferralsListAdapter() {
        return new ReferralsListAdapter();
    }
}
