package com.seition.cloud.pro.newcloud.home.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.seition.cloud.pro.newcloud.home.di.module.CommentModule;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_fragment.CommentFragment;


@ActivityScope
@Component(modules = CommentModule.class, dependencies = AppComponent.class)
public interface CommentComponent {
    void inject(CommentFragment fragment);
}