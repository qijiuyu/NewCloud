package com.seition.cloud.pro.newcloud.home.mvp.ui.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.jess.arms.base.BaseBackFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.bind.FaceStatus;
import com.seition.cloud.pro.newcloud.app.utils.Utils;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerLoginComponent;
import com.seition.cloud.pro.newcloud.home.di.module.LoginModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.LoginContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.LoginPresenter;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class FeedBackFragment extends BaseBackFragment<LoginPresenter> implements  LoginContract.View{

    @BindView(R.id.toolbar_right_text)
    TextView toolbar_right_text;
    @BindView(R.id.content)
    EditText content;

    @BindView(R.id.tel)
    EditText tel;

    @OnClick(R.id.toolbar_right_text)
    void feedBackComit(View view) {
        String content_txt = content.getText().toString().trim();
        String way = tel.getText().toString().trim();
        if (content_txt.equals("")) {
            showMessage("请输入反馈意见内容！");
            return ;
        }
        if (!Utils.isPhone(way)) {
            showMessage( "请输入正确的联系方式！");
            return;
        }
        mPresenter.feedBack(content_txt,way);
    }

    public static FeedBackFragment newInstance( ) {
        Bundle args = new Bundle();
        FeedBackFragment fragment = new FeedBackFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerLoginComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_owner_feed_back,container,false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle(R.string.owner_feed_back);
        toolbar_right_text.setText(R.string.submit);
    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public void toHome() {

    }

    @Override
    public void toRegister(String app_token, String app_login_type) {

    }

    @Override
    public void showRegisterType(String type) {

    }

    @Override
    public void showFaceSaveStatus(FaceStatus status) {

    }

    @Override
    public void setFaceLoginTextVisibiliity(boolean isvisibility) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {
        pop();
    }

}
