package com.seition.cloud.pro.newcloud.home.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.seition.cloud.pro.newcloud.home.di.module.UserModule;
import com.seition.cloud.pro.newcloud.home.mvp.ui.login.activity.GetPasswordEmailActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.login.activity.GetPasswordPhoneActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.login.activity.SetPasswordActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.OwnerSettingFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.exchange.fragment.OwnerExchangeFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.note.OwnerNoteCommentFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.note.OwnerNoteFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.study.fragment.OwnerStudyFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.user.fragment.AreaFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.user.fragment.ReceiveGoodsAddFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.user.fragment.ReceiveGoodsListFragment;

import dagger.Component;

@ActivityScope
@Component(modules =UserModule.class, dependencies = AppComponent.class)
public interface UserComponent {

    void inject(OwnerStudyFragment fragment);
    void inject(OwnerExchangeFragment fragment);
    void inject(ReceiveGoodsListFragment receiveGoodsListFragment);
    void inject(ReceiveGoodsAddFragment receiveGoodsListFragment);
    void inject(AreaFragment areaFragment);

    void inject(OwnerNoteFragment areaFragment);
    void inject(OwnerNoteCommentFragment areaFragment);
    void inject(OwnerSettingFragment settingFragment);

    void inject(GetPasswordPhoneActivity activity);
    void inject(GetPasswordEmailActivity activity);
    void inject(SetPasswordActivity activity);
}