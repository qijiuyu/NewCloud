package com.seition.cloud.pro.newcloud.home.mvp.ui.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.mvp.IView;
import com.jess.arms.utils.ArmsUtils;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.config.MyConfig;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.home.mvp.ui.login.activity.LoginActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.main2.activity.MainActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public class LauncherActivity extends BaseActivity implements IView {

    @BindView(R.id.skip)
    TextView skip;
    @BindView(R.id.launcher_bg)
    ImageView launcher_bg;

    @BindView(R.id.rl_ad)
    RelativeLayout rlAd;

    @OnClick(R.id.rl_ad)
    public void onViewClicked() {
        next();
    }

    CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    private void next() {
        if (TextUtils.isEmpty(PreferenceUtil.getInstance(LauncherActivity.this).getString("oauth_token", null)))
            if (MyConfig.isDefaultOpenHome)
                toMain();//默认进入首页
            else
                toLogin();
        else toMain();
    }

    private void toMain() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
        Intent intent = new Intent(this, MainActivity.class);
        launchActivity(intent);
        killMyself();
    }

    private void toLogin() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
        Intent intent = new Intent(this, LoginActivity.class);
        launchActivity(intent);
        killMyself();
    }

    public Observable<Integer> countDown(int time) {
        if (time < 0) time = 0;
        final int countTime = time;
        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Long, Integer>() {
                    @Override
                    public Integer apply(@NonNull Long aLong) throws Exception {
                        return countTime - aLong.intValue();
                    }
                })
                .take(countTime + 1);
    }


    @Override
    public void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_launcher;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mCompositeDisposable.add(countDown(3).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(@NonNull Disposable disposable) throws Exception {
                skip.setText("跳过 4");
            }
        }).subscribeWith(new DisposableObserver<Integer>() {
            @Override
            public void onNext(Integer integer) {
                skip.setText("跳过 " + (integer + 1));
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                next();
            }
        }));
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
        super.onDestroy();
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@android.support.annotation.NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@android.support.annotation.NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
    }
}
