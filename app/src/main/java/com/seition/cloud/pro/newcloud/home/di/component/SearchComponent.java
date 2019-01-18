package com.seition.cloud.pro.newcloud.home.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.seition.cloud.pro.newcloud.home.di.module.SearchModule;
import com.seition.cloud.pro.newcloud.home.mvp.ui.search.fragment.SearchLiveCoursesFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.search.fragment.SearchVideoCoursesFragment;

import dagger.Component;

//@Component(modules = {SearchModule.class, HomeModule.class}, dependencies = AppComponent.class) //通过 服用Module的方式复用presenter，有点错
@ActivityScope
@Component(modules = SearchModule.class, dependencies = AppComponent.class)
public interface SearchComponent {

    void inject(SearchVideoCoursesFragment searchVideoCoursesFragment);

    void inject(SearchLiveCoursesFragment searchLiveCoursesFragment);

//    void inject(SearchMainFragment searchFragment);
//    HomeComponent plus(HomeModule homeModule);
}