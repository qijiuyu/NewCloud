package com.seition.cloud.pro.newcloud.home.mvp.ui.buy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.jess.arms.base.BaseBackFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.coupon.CouponBean;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.CourseCardPresenter;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerCourseComponent;
import com.seition.cloud.pro.newcloud.home.di.module.CourseModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.CourseContract;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class RechargeCardUseFragment extends BaseBackFragment<CourseCardPresenter> implements CourseContract.CourseCardView {


    @BindView(R.id.card_recharge_input)
    EditText card_recharge_input;

    @BindView(R.id.card_recharge_commit)
    TextView card_recharge_commit;


    public static RechargeCardUseFragment newInstance() {
        Bundle args = new Bundle();
//        args.putSerializable("organ",organ);
        RechargeCardUseFragment fragment = new RechargeCardUseFragment();
//        fragment.setArguments(args);
        return fragment;
    }

    @OnClick({R.id.card_recharge_commit})
    void Buy(View view){
        switch (view.getId()){
            case R.id.card_recharge_commit:

                String number = card_recharge_input.getText().toString();
                if ("".equals(number)) {
                    showMessage( "请输入充值卡卡号");
                    return;
                }
                mPresenter.rechargeCardUse("cardpay",number);
                break;

        }
    }


    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerCourseComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .courseModule(new CourseModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_card_recharge_use,container,false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle("充值卡充值");
        card_recharge_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString().toLowerCase();
                if(input.length()>0)
                    card_recharge_commit.setBackgroundResource(R.drawable.shape_frame_theme);
                else
                    card_recharge_commit.setBackgroundResource(R.drawable.shape_frame_undo);
            }
        });
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
    public void showCoupon(CouponBean data) {

    }
}
