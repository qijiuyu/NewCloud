package com.seition.cloud.pro.newcloud.home.mvp.ui.owner;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jess.arms.base.BaseBackFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.VersionInfo;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.app.utils.download.DBUtils;
import com.seition.cloud.pro.newcloud.app.config.Service;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerUserComponent;
import com.seition.cloud.pro.newcloud.home.di.module.UserModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.UserContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.SettingPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.login.activity.FeedBackFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.login.activity.LoginActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.login.activity.PasswordModifierFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.bind.fragment.BindManageFragment;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class OwnerSettingFragment extends BaseBackFragment<SettingPresenter> implements UserContract.SettingView {

    @BindView(R.id.user_photo)
    ImageView user_photo;
    @BindView(R.id.user_name)
    TextView user_name;
    @BindView(R.id.playSwitch)
    Switch playSwitch;
    @BindView(R.id.cache_size)
    TextView cache_size;

    @OnClick({R.id.rl_user_info, R.id.rl_bind, R.id.rl_change_pwd, R.id.version_updata_rl,
            R.id.feedback, R.id.clean_cache_rl, R.id.about_us_rl, R.id.exit_txt})
    void toSettingBlock(View view) {
        switch (view.getId()) {
            case R.id.rl_user_info:
                start(OwnerUserInfoFragment.newInstance());
//                launchActivity(new Intent(_mActivity,OwnerUserInfoFragment.class));
                break;
            case R.id.rl_bind:
                start(BindManageFragment.newInstance());
                break;
            case R.id.rl_change_pwd:
                start(PasswordModifierFragment.newInstance());
                break;
            case R.id.version_updata_rl:
                mPresenter.getVersionInfo();
                break;
            case R.id.feedback:
                start(FeedBackFragment.newInstance());
                break;
            case R.id.clean_cache_rl:
                DBUtils.init(getContext()).delectCache();
                showCacheSize();
                break;
            case R.id.about_us_rl:
                launchActivity(
                        new Intent(_mActivity, WebActivity.class)
                                .putExtra("title", "关于我们")
                                .putExtra("url", Service.DOMAIN_NAME + "basic.showAbout")
                );
                break;
            case R.id.exit_txt:
                PreferenceUtil.getInstance(_mActivity).clearLoginUser();
                launchActivity(new Intent(_mActivity, LoginActivity.class));
                _mActivity.finish();
                break;

        }
    }


    public static OwnerSettingFragment newInstance() {
        Bundle args = new Bundle();
        OwnerSettingFragment fragment = new OwnerSettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerUserComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .userModule(new UserModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_setting, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle(getResources().getString(R.string.owner_setting));
        playSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PreferenceUtil.getInstance(_mActivity).setWifiPlayConfig(isChecked);
            }
        });
        showCacheSize();
    }

    public void showCacheSize() {
        cache_size.setText(DBUtils.init(getContext()).calculatingCacheSize());
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        user_name.setText(PreferenceUtil.getInstance(_mActivity).getString("uname", null));
        String useravatar = PreferenceUtil.getInstance(_mActivity).getString("user_avatar", null);
        GlideLoaderUtil.LoadCircleImage(_mActivity, useravatar, user_photo);
    }

    @Override
    public void setData(Object data) {

    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        pop();
    }


    @Override
    public void showVersionInfo(VersionInfo versionInfo) {
        float versionCode = getVersionCodes(_mActivity);
        try {
            if (versionCode < Float.parseFloat(versionInfo.getAndroid().getVersion()))
                showVersionUpdatDialog(versionInfo.getAndroid().getDown_url());
            else
                showMessage("已是最新版本");
        } catch (NullPointerException e) {
            e.printStackTrace();
            showMessage("已是最新版本");
        }
    }

    private void showVersionUpdatDialog(String url) {
        new MaterialDialog.Builder(_mActivity)
                .content("是否立即更新版本？")
                .positiveText("确定")
                .negativeText("取消")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public float getVersionCodes(Context context)// 获取版本号(内部识别号)
    {
        try {
            //VersionCode：对消费者不可见，仅用于应用市场、程序内部识别版本，判断新旧等用途。Int
            //VersionName：展示给消费者，消费者会通过它认知自己安装的版本，下文提到的版本号都是说
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return Float.parseFloat(pi.versionName);

        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 1;
        }

    }

}
