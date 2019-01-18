package com.seition.cloud.pro.newcloud.home.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.seition.cloud.pro.newcloud.home.mvp.contract.OrderContract;
import com.seition.cloud.pro.newcloud.home.mvp.model.OrderModel;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.OrderListRecyclerAdapter;

import dagger.Module;
import dagger.Provides;


@Module
public class OrderModule {
    private OrderContract.View view;

    /**
     * 构建OrderModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public OrderModule(OrderContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    OrderContract.View provideOrderView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    OrderContract.Model provideOrderModel(OrderModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    OrderListRecyclerAdapter provideOrderAdapter(OrderModel model) {
        return new  OrderListRecyclerAdapter();
    }

}