package com.seition.cloud.pro.newcloud.home.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.seition.cloud.pro.newcloud.home.mvp.contract.MoneyContract;
import com.seition.cloud.pro.newcloud.home.mvp.model.MoneyModel;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.money.adapter.RechangeAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.MoneyDetailsListRecyclerAdapter;

import dagger.Module;
import dagger.Provides;


@Module
public class MoneyModule {
    private MoneyContract.View view;
    private MoneyContract.ListView listView;

    /**
     * 构建OwnerMoneyModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MoneyModule(MoneyContract.View view) {
        this.view = view;
    }
    public MoneyModule(MoneyContract.ListView view) {
        this.listView = view;
    }

    @ActivityScope
    @Provides
    MoneyContract.View provideOwnerMoneyView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MoneyContract.ListView provideMoneyDetailsListView() {
        return this.listView;
    }

    @ActivityScope
    @Provides
    MoneyContract.Model provideOwnerMoneyModel(MoneyModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    RechangeAdapter provideRechangeAdapter() {
        return new RechangeAdapter();
    }


    @ActivityScope
    @Provides
    MoneyDetailsListRecyclerAdapter provideMoneyDetailsListAdapter() {
        return new MoneyDetailsListRecyclerAdapter();
    }
}