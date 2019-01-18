package com.seition.cloud.pro.newcloud.home.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.seition.cloud.pro.newcloud.home.mvp.contract.UserContract;
import com.seition.cloud.pro.newcloud.home.mvp.model.UserModel;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.AreaRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.NoteCommentRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.NoteRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.ReceiveAddressRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.UserExchangeRecordRecyclerAdapter;

import dagger.Module;
import dagger.Provides;


@Module
public class UserModule {
    private UserContract.View view;
    private UserContract.StudyView studyView;
    private UserContract.SettingView settingView;
    private UserContract.FindPasswordView findPasswordView;

    /**
     * 构建SearchModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public UserModule(UserContract.View view) {
        this.view = view;
    }
    public UserModule(UserContract.StudyView view) {
        this.studyView = view;
    }
    public UserModule(UserContract.SettingView view) {
        this.settingView = view;
    }
    public UserModule(UserContract.FindPasswordView view) {
        this.findPasswordView = view;
    }
    @ActivityScope
    @Provides
    UserContract.View provideSearchView() {
        return this.view;
    }
    @ActivityScope
    @Provides
    UserContract.StudyView provideStudyView() {
        return this.studyView;
    }
    @ActivityScope
    @Provides
    UserContract.SettingView provideSettingView() {
        return this.settingView;
    }
    @ActivityScope
    @Provides
    UserContract.FindPasswordView provideFinView() {
        return this.findPasswordView;
    }
    @ActivityScope
    @Provides
    UserContract.Model provideSearchModel(UserModel model) {
        return model;
    }

//    @ActivityScope
//    @Provides
//    UserStudyRecordRecyclerAdapter privoiduserStudyRecordAdapter(){
//        return  new UserStudyRecordRecyclerAdapter();
//    }

    @ActivityScope
    @Provides
    UserExchangeRecordRecyclerAdapter privoiduserExchangeRecordAdapter(){
        return  new UserExchangeRecordRecyclerAdapter();
    }

    @ActivityScope
    @Provides
    ReceiveAddressRecyclerAdapter privoidReceiveAddressAdapter(){
        return  new ReceiveAddressRecyclerAdapter();
    }
    @ActivityScope
    @Provides
    AreaRecyclerAdapter privoidAreaAdapter(){
        return  new AreaRecyclerAdapter();
    }

    @ActivityScope
    @Provides
    NoteRecyclerAdapter privoidUserNoteAdapter(){
        return  new NoteRecyclerAdapter();
    }
    @ActivityScope
    @Provides
    NoteCommentRecyclerAdapter privoidNoteCommentAdapter(){
        return  new NoteCommentRecyclerAdapter();
    }


}