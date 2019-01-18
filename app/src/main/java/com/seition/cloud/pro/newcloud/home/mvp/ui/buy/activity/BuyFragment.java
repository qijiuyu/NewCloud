package com.seition.cloud.pro.newcloud.home.mvp.ui.buy.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pay.ali.AliPayUtils;
import com.jess.arms.base.BaseBackFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.PayResponse;
import com.seition.cloud.pro.newcloud.app.bean.common.Section;
import com.seition.cloud.pro.newcloud.app.bean.coupon.CouponBean;
import com.seition.cloud.pro.newcloud.app.bean.course.CourseSeitionVideo;
import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnline;
import com.seition.cloud.pro.newcloud.app.bean.offline.CourseOffline;
import com.seition.cloud.pro.newcloud.app.bean.user.UserAccount;
import com.seition.cloud.pro.newcloud.app.config.MyConfig;
import com.seition.cloud.pro.newcloud.app.receivers.WXPAYReceiver;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.app.utils.WXPayUtils;
import com.seition.cloud.pro.newcloud.app.config.Service;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerBuyComponent;
import com.seition.cloud.pro.newcloud.home.di.module.BuyModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.BuyContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.BuyPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.coupon.activity.SelectCouponMainActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.course.adapter.CourseSeitionVideoItem;
import com.seition.cloud.pro.newcloud.home.mvp.view.ShowAgreement;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.seition.cloud.pro.newcloud.app.config.MyConfig.RequestCodeCouponSelect;
import static com.seition.cloud.pro.newcloud.app.config.MyConfig.RequestCodeEntityCard;


public class BuyFragment extends BaseBackFragment<BuyPresenter> implements BuyContract.View {

    @BindView(R.id.agree)
    CheckBox agree;

    @BindView(R.id.cover)
    ImageView cover;
    @BindView(R.id.buy)
    TextView buy;

    @BindView(R.id.course_name)
    TextView course_name;
    @BindView(R.id.pay_money)
    TextView pay_money;

    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.discount)
    TextView discount;
    @BindView(R.id.pay_price)
    TextView pay_price;
    @BindView(R.id.coupon_ll)
    LinearLayout coupon_ll;
    @BindView(R.id.discount_ll)
    LinearLayout discount_ll;

    @BindView(R.id.balance_rb)
    RadioButton balance;

    @BindView(R.id.commission_rb)
    RadioButton commission_rb;
    @BindView(R.id.alpay_rb)
    RadioButton alpay_rb;
    @BindView(R.id.wxpay_rb)
    RadioButton wxpay_rb;
    @BindView(R.id.bank_rb)
    RadioButton bank_rb;

    @BindView(R.id.bank_ll)
    LinearLayout bank_ll;
    @BindView(R.id.card_ll)
    LinearLayout card_ll;

    @BindView(R.id.number_coupon_txt)
    TextView number_coupon_txt;
    @BindView(R.id.number_card_bt)
    TextView number_card_bt;

    String courseCover = "", courseName = "", payMoney = "", discountMoney = "";


    String payStyle = "";
    private CourseSeitionVideo item;
    private Section section;
    private int coupon_id = -1;
    private CourseOnline course;
    private CourseOffline offline;


    public static BuyFragment newInstance(CourseOffline offline) {
        Bundle args = new Bundle();
        args.putSerializable("offline", offline);
        BuyFragment fragment = new BuyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static BuyFragment newInstance(CourseOnline course, CourseSeitionVideo item, Section section) {
        Bundle args = new Bundle();
        args.putSerializable("course", course);
        args.putSerializable("item", item);
        args.putSerializable("SectionItem", section);
        BuyFragment fragment = new BuyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static BuyFragment newInstance(CourseOnline course) {
        return newInstance(course, null, null);
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerBuyComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .buyModule(new BuyModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_buy, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle("订单支付");
        course = (CourseOnline) getArguments().getSerializable("course");
        item = (CourseSeitionVideo) getArguments().getSerializable("item");
        section = (Section) getArguments().getSerializable("SectionItem");
        offline = (CourseOffline) getArguments().getSerializable("offline");
        showBaseInfo();
        loadData();
        registerReceivers();
        agree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                buy.setEnabled(agree.isChecked());
                if (isChecked)
                    buy.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                else
                    buy.setBackgroundColor(getResources().getColor(R.color.color_a5c3eb));
            }
        });
    }

    WXPAYReceiver receiver;

    private void registerReceivers() {
        receiver = new WXPAYReceiver() {

            @Override
            public void exit() {
                killMyself();
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MyConfig.WXPAY_SUCCESS);
        _mActivity.registerReceiver(receiver, intentFilter);
    }

    private void unregisterReceivers() {
        if (receiver != null)
            _mActivity.unregisterReceiver(receiver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceivers();
    }

    @Override
    public void setData(Object data) {

    }

    @OnClick({R.id.buy, R.id.agreement, R.id.alpay_rb, R.id.wxpay_rb, R.id.balance_rb, R.id.commission_rb, R.id.bank_rb, R.id.card_ll, R.id.number_card_ll, R.id.coupon})
    void Buy(View view) {
        switch (view.getId()) {
            case R.id.alpay_rb:
                payStyle = MyConfig.ALIPAY;
                break;
            case R.id.wxpay_rb:
                payStyle = MyConfig.WXPAY;
                break;
            case R.id.balance_rb:
                payStyle = MyConfig.ICNPAY;
                break;
            case R.id.card_ll:
                payStyle = MyConfig.CACHE_SP_NAME;
//                startActivityForResult(new Intent(this, BuyCouponAvtivity.class).putExtra("course", course), 0);
                break;
            case R.id.commission_rb:
                payStyle = MyConfig.SPIPAY;
            case R.id.bank_rb:
                payStyle = MyConfig.UNIONPAY;
                break;
            case R.id.buy:
                pay(payStyle);
                break;
            case R.id.coupon://选择优惠券
                int type = 0;
                String id = null;
                if (course == null) {
                    if (offline != null) {
                        type = 3;
                        id = offline.getCourse_id();
                    }
                } else if (course.getType().equals("1")) {
                    type = 1;
                    id = course.getId();
                } else if (course.getType().equals("2")) {
                    type = 2;
                    id = course.getLive_id() == null ? course.getId() : course.getLive_id();
                }
                if (type == 0 || id == null) return;
//                loadRootFragment(R.id.id_content, SelectCouponMainActivity.newInstance(type, id));
//                startForResult(SelectCouponMainActivity.newInstance(type, id), 100);
                startActivityForResult(
                        new Intent(_mActivity, SelectCouponMainActivity.class)
                                .putExtra("type", type)
                                .putExtra("id", id)
                        , RequestCodeCouponSelect);
                break;
            case R.id.agreement:
                ShowAgreement.showAgreement(_mActivity, getLayoutInflater(), agree, "buy");
                break;
            case R.id.number_card_ll:
                Intent intent = new Intent(_mActivity, EntityCardUseActivity.class);
                intent.putExtra("course", course);
                if (isEntityCard)
                    intent.putExtra("coupon", couponBean);
                startActivityForResult(intent, RequestCodeEntityCard);
                break;
        }
    }


    private boolean isEntityCard = false;
    private CouponBean couponBean;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            couponBean = (CouponBean) data.getSerializableExtra("couponBean");
            showCoupon();
        } else if (resultCode == RequestCodeEntityCard) {
            couponBean = (CouponBean) data.getSerializableExtra("cb");
            isEntityCard = couponBean != null;
            showCoupon();
            number_coupon_txt.setEnabled(!isEntityCard);

        } else if (resultCode == 103) {
            couponBean = null;
            showCoupon();
        }
    }

    boolean isCourseCard = false;

    private void showCoupon() {
        if (couponBean != null) {
            int type = couponBean.getType();
            double discountPrice = 0;
            String catdTxt = "";
            isCourseCard = false;
            switch (type) {
                case 1:
                    discountPrice = couponBean.getPrice();
                    catdTxt = "优惠券 " + discountPrice;
                    break;
                case 2:
                    discountPrice = (course.getT_price() - couponBean.getDiscount() * course.getT_price() / 10);
                    catdTxt = "打折卡 " + couponBean.getDiscount() + "折";
                    break;
                case 5:
                    discountPrice = course.getT_price();
                    catdTxt = "课程卡";
                    isCourseCard = true;
                    break;
            }
            coupon_id = couponBean.getCoupon_id();
            if (isEntityCard) {
                number_card_bt.setText(catdTxt + "");
                number_coupon_txt.setText("  ");
            } else {
                number_card_bt.setText("  ");
                number_coupon_txt.setText(catdTxt + "");
            }
            discount.setText("-¥" + discountPrice);
            payMoney = (course.getT_price() - discountPrice) + "";
            pay_price.setText("¥" + (course.getT_price() - discountPrice));
        } else {
            coupon_id = -1;
            number_coupon_txt.setText("");
            number_card_bt.setText("");
            discount.setText("-¥" + 0);
            payMoney = course.getT_price() + "";
            pay_price.setText("¥" + course.getT_price());
        }
    }

    private void pay(String payStyle) {
        if (payStyle.isEmpty()) {
            showMessage("请选择支付方式！");
            return;
        }
        if (!agree.isChecked()) {
            showMessage(getString(R.string.procotol3));
            return;
        }
        if (course != null) {
            if (course.getType().equals("1")) {
                if (item != null) {
                    mPresenter.buyCourseVideoItemWithWxOrAli(course.getId(), item.getId() + "", payStyle, 1);
                } else if (Double.parseDouble(payMoney) == 0 && !isCourseCard)
                    mPresenter.addFreeOrder(course.getId(), 1, payStyle, coupon_id);
                else
                    mPresenter.buyCourseVideo(course.getId(), payStyle, coupon_id, isCourseCard);
            } else if (course.getType().equals("2")) {
                if (section != null)
                    mPresenter.buyCourseVideoItemWithWxOrAli(course.getLive_id(), section.getId() + "", payStyle, 2);
                else if (Double.parseDouble(payMoney) == 0 && !isCourseCard)
                    mPresenter.addFreeOrder(course.getLive_id(), 2, payStyle, coupon_id);
                else
                    mPresenter.buyCourseLive(course.getLive_id() == null ? course.getId() : course.getLive_id(), payStyle, coupon_id, isCourseCard);
            }
        } else {
            if (Double.parseDouble(payMoney) == 0)
                mPresenter.addFreeOrder(offline.getCourse_id(), 4, payStyle, coupon_id);
            else
                mPresenter.buyCourseOffline(offline.getCourse_id(), payStyle, coupon_id);
        }

    }

    private void showBaseInfo() {
        if (course == null) {
            coupon_ll.setVisibility(View.GONE);
            discount_ll.setVisibility(View.GONE);
            courseCover = offline.getImageurl();
            courseName = offline.getCourse_name();
            if (TextUtils.isEmpty(PreferenceUtil.getInstance(_mActivity).getString("oauth_token", null)))
                payMoney = offline.getT_price();///.equals("0") ? "免费" : offline.getT_price() + "";
            else
                payMoney = offline.getPrice();//.equals("0") ? "免费" : offline.getPrice() + "";
        } else if (offline == null) {
            courseCover = course.getCover();
            courseName = course.getVideo_title();
            if (item != null) {
                courseName += " —— " + item.getTitle();
                coupon_ll.setVisibility(View.GONE);
                payMoney = item.getCourse_hour_price() + "";
            } else if (section != null) {
                courseName += " —— " + section.getTitle();
                coupon_ll.setVisibility(View.GONE);
                payMoney = section.getCourse_hour_price() + "";
            } else {
                if (TextUtils.isEmpty(PreferenceUtil.getInstance(_mActivity).getString("oauth_token", null)))
                    payMoney = course.getT_price() == 0 ? "0" : course.getT_price() + "";
                else
                    payMoney = course.getPrice() == 0 ? "0" : course.getPrice() + "";

                if (payMoney.equals("0"))
                    coupon_ll.setVisibility(View.GONE);
                else {
                    if (course.getType().equals("1"))
                        mPresenter.getVideoCoupon(course.getId());
                    else if (course.getType().equals("2"))
                        mPresenter.getLiveCoupon(course.getLive_id() == null ? course.getId() : course.getLive_id());
                }
            }
        }
        GlideLoaderUtil.LoadImage(_mActivity, courseCover, cover);
        course_name.setText(courseName);
        pay_money.setText("¥" + /*(payMoney.equals("免费") ? "0" : */payMoney);
        price.setText("¥" + payMoney);
        discount.setText("-¥0");
        pay_price.setText("¥" + payMoney);

    }


    private void loadData() {
        mPresenter.getUserAccount(true);
        mPresenter.getPaySwitch();
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
    public void showUserAccount(UserAccount account) {
        balance.setText("   (当前账户余额为¥" + account.getLearn());
    }

    @Override
    public void showPayView(ArrayList<String> datas) {
        for (int i = 0; i < datas.size(); i++) {
            String payType = datas.get(i);
            switch (payType) {
                case "cardpay"://充值卡充值
//                    card_ll.setVisibility(View.VISIBLE);
                    break;
                case MyConfig.WXPAY://微信充值
                    wxpay_rb.setVisibility(View.VISIBLE); //wxpay_ll wxpay_rb
                    if (i == 0) {
                        wxpay_rb.setChecked(true);
                        payStyle = payType;
                    }
                    break;
                case MyConfig.ALIPAY://支付宝充值
                    alpay_rb.setVisibility(View.VISIBLE);//alpay_ll  alpay_rb
                    if (i == 0) {
                        alpay_rb.setChecked(true);
                        payStyle = payType;
                    }
                    break;
                case MyConfig.ICNPAY://余额支付
                    balance.setVisibility(View.VISIBLE);//alpay_ll  alpay_rb
                    if (i == 0) {
                        balance.setChecked(true);
                        payStyle = payType;
                    }
                    break;
            }
        }
    }

    @Override
    public void showPayResult(PayResponse data) {
        if (data.getAlipay() != null) {
            toPayFromAliPayFromService(data.getAlipay().getBasic());
        } else if (data.getWxpay() != null) {

            WXPayUtils.WXPayBuilder builder = new WXPayUtils.WXPayBuilder();
            builder.setAppId(data.getWxpay().getBasic().getAppid())
                    .setPartnerId(data.getWxpay().getBasic().getPartnerid())
                    .setPrepayId(data.getWxpay().getBasic().getPrepayid())
                    .setPackageValue(data.getWxpay().getBasic().getPackages())
                    .setNonceStr(data.getWxpay().getBasic().getNoncestr())
                    .setTimeStamp(data.getWxpay().getBasic().getTimestamp())
                    .setSign(data.getWxpay().getBasic().getSign())
                    .build().toWXPayNotSign(_mActivity, MyConfig.APP_ID);
        }
    }

    @Override
    public void showCoupon(ArrayList<CouponBean> couponBeans) {
        if (couponBeans == null || couponBeans.size() == 0)
            number_coupon_txt.setText("无可用优惠券");
        else {
            Intent intent = new Intent();
            intent.putExtra("couponBean", couponBeans.get(0));
            onActivityResult(0, Activity.RESULT_OK, intent);
        }
    }

    private void toPayFromAliPayFromService(String orderInfo) {

        AliPayUtils aliPayUtils = new AliPayUtils(_mActivity);
        aliPayUtils.requestPayFromServiceSide(orderInfo);
        aliPayUtils.setPayListener(new AliPayUtils.OnAlipayListener() {
            @Override
            public void onSuccess() {
                super.onSuccess();
                Toast.makeText(_mActivity, "支付成功", Toast.LENGTH_LONG).show();
                killMyself();
            }

            @Override
            public void onCancel() {
                super.onCancel();
                Toast.makeText(_mActivity, "取消支付", Toast.LENGTH_LONG).show();
            }
        });
    }
}
