package com.seition.cloud.pro.newcloud.home.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.seition.cloud.pro.newcloud.home.di.module.LoginModule;
import com.seition.cloud.pro.newcloud.home.mvp.ui.login.activity.FeedBackFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.login.activity.LoginActivity;

import dagger.Component;

@ActivityScope
@Component(modules = LoginModule.class, dependencies = AppComponent.class)
public interface LoginComponent {
    void inject(LoginActivity activity);
    void inject(FeedBackFragment activity);

//    void inject(BindFaceFragment activity);
}
//@LoginScope
//@Subcomponent(modules = LoginModule.class)