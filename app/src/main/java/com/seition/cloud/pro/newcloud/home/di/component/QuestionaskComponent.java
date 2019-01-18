package com.seition.cloud.pro.newcloud.home.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.seition.cloud.pro.newcloud.home.di.module.QuestionaskModule;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.qa.fragment.QuestionaskAnswerFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.qa.fragment.QuestionaskClassifyFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.qa.fragment.QuestionaskDetailsFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.qa.fragment.QuestionaskFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.qa.fragment.QuestionaskHonorFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.qa.fragment.QuestionaskHotFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.qa.fragment.QuestionaskPublishFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.qa.fragment.QuestionaskSearchFragment;

import dagger.Component;

@ActivityScope
@Component(modules = QuestionaskModule.class, dependencies = AppComponent.class)
public interface QuestionaskComponent {
    void inject(QuestionaskFragment questionaskFragment);
    void inject(QuestionaskDetailsFragment questionaskDetailsFragment);

    void inject(QuestionaskPublishFragment questionaskPublishFragment);
    void inject(QuestionaskSearchFragment questionaskSearchFragment);
    void inject(QuestionaskHotFragment questionaskHotFragment);
    void inject(QuestionaskHonorFragment questionaskHonorFragment);
    void inject(QuestionaskAnswerFragment questionaskHonorFragment);
    void inject(QuestionaskClassifyFragment questionaskHonorFragment);
}