package com.seition.cloud.pro.newcloud.home.mvp.ui.buy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.coupon.CouponBean;
import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnline;
import com.seition.cloud.pro.newcloud.app.config.MyConfig;
import com.seition.cloud.pro.newcloud.app.utils.Utils;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.CourseCardPresenter;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerCourseComponent;
import com.seition.cloud.pro.newcloud.home.di.module.CourseModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.CourseContract;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.seition.cloud.pro.newcloud.app.config.MyConfig.RequestCodeEntityCard;


public class EntityCardUseActivity extends BaseActivity<CourseCardPresenter> implements CourseContract.CourseCardView {

    @BindView(R.id.entity_card)
    LinearLayout entity_card;
    @BindView(R.id.card_recharge_input)
    EditText card_recharge_input;
    @BindView(R.id.use_entity_card)
    LinearLayout use_entity_card;
    @BindView(R.id.entity_card_num)
    TextView entity_card_num;
    @BindView(R.id.coupon)
    TextView coupon;
    @BindView(R.id.card_recharge_commit)
    TextView card_recharge_commit;



    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerCourseComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .courseModule(new CourseModule(this))
                .build()
                .inject(this);
    }

    @OnClick({R.id.card_recharge_commit})
    void Buy(View view){
        switch (view.getId()){
            case R.id.card_recharge_commit:
                Utils.hideSoftInput(view);
                if (couponBean == null) {
                    String number = card_recharge_input.getText().toString();
                    if ("".equals(number)) {
                        showMessage( "请输入实体卡卡号");
                        return;
                    }
                    if (course.getType().equals("1"))
                        mPresenter.getExchangeCard(number
                                , MyConfig.isOpenAboutSchool ? course.getSchool_info().getSchool_id() : 0
                                , course.getPrice()
                                , course.getId());
                    else if (course.getType().equals("2"))
                        mPresenter.getExchangeCard(number
                                , MyConfig.isOpenAboutSchool ? course.getSchool_info().getSchool_id() : 0
                                , course.getPrice()
                                , course.getLive_id());
                } else {
                    String card = couponBean.getCode()+"";
                    if (card == null || card.trim().isEmpty()) {
                        showMessage( "请输入实体卡编码！");
                        return;
                    }
                    mPresenter.cancelExchangeCard(card,MyConfig.isOpenAboutSchool ? course.getSchool_info().getSchool_id() : 0);
                }
                break;

        }
    }


    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_card_entity_use; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle("实体卡");
        course = (CourseOnline) getIntent().getSerializableExtra("course");
        couponBean = (CouponBean) getIntent().getSerializableExtra("coupon");

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
        showCoupon();
    }
    private CourseOnline course;
    private CouponBean couponBean;
    private void showCoupon() {
        if (couponBean == null) {
            //未使用实体卡
            use_entity_card.setVisibility(View.GONE);
            entity_card.setVisibility(View.VISIBLE);
            card_recharge_commit.setText("确定使用");
            card_recharge_input.setText("");
        } else {
            //已使用实体卡
            use_entity_card.setVisibility(View.VISIBLE);
            entity_card.setVisibility(View.GONE);
            card_recharge_commit.setText("取消使用");
            entity_card_num.setText(couponBean.getCode()+"");
            int type = couponBean.getType();
            String catdTxt = "";
            switch (type) {
                case 1:
                    catdTxt = "优惠券 " + couponBean.getPrice();
                    break;
                case 2:
                    catdTxt = "打折卡 " + couponBean.getDiscount() + "折";
                    break;
                case 5:
                    catdTxt = "课程卡";
                    break;
            }
            coupon.setText(catdTxt);
        }
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
        finish();
    }


    @Override
    public void showCoupon(CouponBean data) {
        couponBean = data;
        Intent intent = new Intent();
        intent.putExtra("cb", data);
        setResult(RequestCodeEntityCard, intent);
        killMyself();
    }
}
