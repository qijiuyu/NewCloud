package com.seition.cloud.pro.newcloud.home.mvp.ui.more.mall.framgent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.pay.ali.AliPayUtils;
import com.jess.arms.base.BaseBackFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.liaoinstan.springview.widget.SpringView;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.PayResponse;
import com.seition.cloud.pro.newcloud.app.bean.ReceiveGoodsAddress;
import com.seition.cloud.pro.newcloud.app.bean.config.CredPayConfig;
import com.seition.cloud.pro.newcloud.app.bean.mall.Mall;
import com.seition.cloud.pro.newcloud.app.bean.money.BalanceDetails;
import com.seition.cloud.pro.newcloud.app.bean.money.CreditDetails;
import com.seition.cloud.pro.newcloud.app.config.MyConfig;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.app.utils.ViewContentSettingUtils;
import com.seition.cloud.pro.newcloud.app.utils.WXPayUtils;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerMallComponent;
import com.seition.cloud.pro.newcloud.home.di.module.MallModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.MallContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.MallDetailsPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.login.activity.LoginActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.user.fragment.ReceiveGoodsListFragment;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.seition.cloud.pro.newcloud.app.config.MyConfig.RequestCodeReceive;


public class MallDetailsFragment extends BaseBackFragment<MallDetailsPresenter> implements MallContract.DetailstView, View.OnClickListener {
    @BindView(R.id.springview)
    SpringView springView;

    @BindView(R.id.mall_details_cover)
    ImageView mall_details_cover;

    @BindView(R.id.pay_price)
    TextView pay_price;
    @BindView(R.id.mall_details_name)
    TextView mall_details_name;
    @BindView(R.id.mall_details_price)
    TextView mall_details_price;
    @BindView(R.id.mall_details_last_number)
    TextView mall_details_last_number;
    @BindView(R.id.mall_details_exchange_number)
    TextView mall_details_exchange_number;
    @BindView(R.id.mall_details_address)
    TextView mall_details_address;
    @BindView(R.id.mall_details_fare)
    TextView mall_details_fare;
    @BindView(R.id.mall_number)
    TextView mall_number;
    @BindView(R.id.mall_details_instro)
    TextView mall_details_instro;

//    private Mall mallRank;

    private Mall mall;
    private BottomSheetDialog bottomSheetDialog;
    private int mall_id = -1, price = 0, stock = 0, exchange = 0;
    private String coverUrl = "", title = "", fare = "", info = "";

    private int mallNumber = 1;
    private int mallStock;
    private String mall_address_id = "";
    private String mall_address = "";


    public static MallDetailsFragment newInstance(Mall mall) {
        Bundle args = new Bundle();
        args.putSerializable("mall", mall);
        MallDetailsFragment fragment = new MallDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick({R.id.mall_details_address, R.id.mall_number_delete, R.id.mall_number_add, R.id.exchange_goods})
    void doOperation(View view) {
        switch (view.getId()) {
            case R.id.mall_details_address:
                if (TextUtils.isEmpty(PreferenceUtil.getInstance(_mActivity).getString("oauth_token", null)))
                    launchActivity(new Intent(_mActivity, LoginActivity.class).putExtra("SkipToHome", false));
                else
                    startForResult(ReceiveGoodsListFragment.newInstance(), RequestCodeReceive);
                break;
            case R.id.mall_number_delete:
                if (mallNumber > 1)
                    mallNumber--;
                if (mallNumber < 1)
                    mallNumber = 0;
                mall_number.setText("" + mallNumber);
                break;
            case R.id.mall_number_add:
                mallNumber++;
                if (mallNumber <= mallStock)
                    mall_number.setText("" + mallNumber);
                else {
                    mallNumber = mallStock;
                    mall_number.setText("" + mallNumber);
                    showMessage("没有更多了");
                }
                break;
            case R.id.exchange_goods:
                if (TextUtils.isEmpty(PreferenceUtil.getInstance(_mActivity).getString("oauth_token", null))) {
                    launchActivity(new Intent(_mActivity, LoginActivity.class).putExtra("SkipToHome", false));
                } else {
                    if ("".equals(mall_address_id)) {
                        showMessage("请选择收货地址");
                    } else if (mallNumber == 0) {
                        showMessage("请选择兑换数量");//Toast.makeText(_mActivity, "请选择兑换数量", Toast.LENGTH_SHORT).show();
                    } else if (mallNumber > mallStock) {
                        Toast.makeText(_mActivity, "库存不足", Toast.LENGTH_SHORT).show();
                    } else {
                        mPresenter.getCountDetails();
//                        new MaterialDialog.Builder(_mActivity)
//                                .content("确定要花费" + (mall.getPrice() * mallNumber + mall.getFare()) + "积分兑换该商品？")
//                                .positiveText("确定")
//                                .negativeText("取消")
//                                .onPositive(new MaterialDialog.SingleButtonCallback() {
//                                    @Override
//                                    public void onClick(MaterialDialog dialog, DialogAction which) {
//                                        try {
//                                            mPresenter.exchangeMallGood(mall.getGoods_id(), mallNumber, mall_address_id);
//
//                                        } catch (Exception e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                })
//                                .onNegative(new MaterialDialog.SingleButtonCallback() {
//                                    @Override
//                                    public void onClick(MaterialDialog dialog, DialogAction which) {
//                                        dialog.dismiss();
//                                    }
//                                })
//                                .show();
                    }
                }

                break;
        }
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerMallComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .mallModule(new MallModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mall_details, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mall = (Mall) getArguments().getSerializable("mall");
        mallStock = mall.getStock();
        initData();
        loadData();
        initPayDialog();
    }

    public void initPayDialog() {
        bottomSheetDialog = new BottomSheetDialog(_mActivity);
        bottomSheetDialog.setContentView(R.layout.dialog_select_pay);
        bottomSheetDialog.findViewById(R.id.alpay).setOnClickListener(this);
        bottomSheetDialog.findViewById(R.id.wxpay).setOnClickListener(this);
        bottomSheetDialog.findViewById(R.id.balance).setOnClickListener(this);
        bottomSheetDialog.findViewById(R.id.buy).setOnClickListener(this);
    }

    @Override
    public void showDialog(CreditDetails credit) {
        ((TextView) bottomSheetDialog.findViewById(R.id.balance)).setText("   (当前账户积分为¥" + credit.getCredit_info().getScore() + ")");
        showPriceForDialog();
        bottomSheetDialog.show();
    }

    private void showPriceForDialog() {
        ((TextView) bottomSheetDialog.findViewById(R.id.pay_price)).setText(
                ViewContentSettingUtils.getMallPrice(mall.getPrice() * mallNumber + mall.getFare(), getContext(), false, payStyle.equals(MyConfig.CREDIT)));
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == RequestCodeReceive && resultCode == RESULT_OK && data != null) {
            ReceiveGoodsAddress receiveGoodsAddress = (ReceiveGoodsAddress) data.getSerializable("ReceiveAddressResult");
            mall_address = "" + receiveGoodsAddress.getProvince() + receiveGoodsAddress.getCity() + receiveGoodsAddress.getArea() + receiveGoodsAddress.getAddress();
            mall_address_id = receiveGoodsAddress.getAddress_id();
            mall_details_address.setText(mall_address);
//            getArguments().putString(ARG_TITLE, mTitle);
//            Toast.makeText(_mActivity, R.string.modify_title, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public void showPrice(CredPayConfig credPayConfig) {
        if (bottomSheetDialog != null && credPayConfig != null && credPayConfig.getPay_type() != null) {
            bottomSheetDialog.findViewById(R.id.alpay).setVisibility(View.GONE);
            bottomSheetDialog.findViewById(R.id.wxpay).setVisibility(View.GONE);
            for (String str : credPayConfig.getPay_type())
                switch (str) {
                    case MyConfig.ALIPAY:
                        bottomSheetDialog.findViewById(R.id.alpay).setVisibility(View.VISIBLE);
                        ((RadioButton) bottomSheetDialog.findViewById(R.id.alpay)).setChecked(true);
                        ((RadioButton) bottomSheetDialog.findViewById(R.id.wxpay)).setChecked(false);
                        ((RadioButton) bottomSheetDialog.findViewById(R.id.balance)).setChecked(false);
                        payStyle = MyConfig.ALIPAY;
                        break;
                    case MyConfig.WXPAY:
                        bottomSheetDialog.findViewById(R.id.wxpay).setVisibility(View.VISIBLE);
                        if (payStyle.isEmpty()) {
                            ((RadioButton) bottomSheetDialog.findViewById(R.id.wxpay)).setChecked(true);
                            ((RadioButton) bottomSheetDialog.findViewById(R.id.alpay)).setChecked(false);
                            ((RadioButton) bottomSheetDialog.findViewById(R.id.balance)).setChecked(false);
                            payStyle = MyConfig.WXPAY;
                        }
                        break;
                }
        }
        if (MyConfig.isOpenCredPay) {
            pay_price.setText("所需积分");
            if (bottomSheetDialog != null) {
                bottomSheetDialog.findViewById(R.id.balance).setVisibility(View.VISIBLE);
                if (payStyle.isEmpty()) {
                    ((RadioButton) bottomSheetDialog.findViewById(R.id.balance)).setChecked(true);
                    ((RadioButton) bottomSheetDialog.findViewById(R.id.wxpay)).setChecked(false);
                    ((RadioButton) bottomSheetDialog.findViewById(R.id.alpay)).setChecked(false);
                    payStyle = MyConfig.CREDIT;
                }
            }
        } else {
            pay_price.setText("所需金额");
            if (bottomSheetDialog != null)
                bottomSheetDialog.findViewById(R.id.balance).setVisibility(View.GONE);
        }
        mall_details_price.setText(ViewContentSettingUtils.getMallPrice(mall.getPrice(), getContext(), true, false));
        mall_details_fare.setText(ViewContentSettingUtils.getMallPrice(mall.getFare(), getContext(), true, false));
    }

    private void initData() {
        mPresenter.getInitCredpayConfig();
        setTitle(mall.getTitle());
        GlideLoaderUtil.LoadImage(_mActivity, mall.getCover(), mall_details_cover);
        mall_details_name.setText(mall.getTitle());
        mall_details_last_number.setText(mall.getStock() + "");
        mall_details_exchange_number.setText(mall.getNum() + "");
        showPrice(null);
        mall_details_instro.setText(mall.getInfo());
        springView.setType(SpringView.Type.FOLLOW);
        springView.setEnableFooter(false);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
//                page =1 ;
//                loadData(page,true);
            }

            @Override
            public void onLoadmore() {
//                page ++ ;
//                loadData(page,false);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getInitCredpayConfig();
    }

    private void loadData() {
        if (TextUtils.isEmpty(PreferenceUtil.getInstance(_mActivity).getString("oauth_token", null))) {
        } else
            mPresenter.getReceiveAddresses();
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
    public void setReceiveAddress(ReceiveGoodsAddress address) {
        if (address != null)
            mall_address_id = address.getAddress_id();
        mall_details_address.setText(address == null ? "添加收货地址" : address.getProvince() + address.getCity() + address.getArea() + address.getAddress());
    }

    @Override
    public void reLoad(int num) {
        mall_details_last_number.setText(mall.getStock() - num + "");
    }

    private void toPayFromAliPayFromService(String orderInfo) {

        AliPayUtils aliPayUtils = new AliPayUtils(_mActivity);
        aliPayUtils.requestPayFromServiceSide(orderInfo);
        aliPayUtils.setPayListener(new AliPayUtils.OnAlipayListener() {
            @Override
            public void onSuccess() {
                super.onSuccess();
//                loadData();
                showMessage("支付成功");
            }

            @Override
            public void onCancel() {
                super.onCancel();
                showMessage("取消支付");
            }
        });
    }

    @Override
    public void showPayResult(PayResponse data) {
        if (data.getAlipay() != null) {
            toPayFromAliPayFromService(data.getAlipay().getBasic());
        } else if (data.getWxpay() != null) {
            if (data.getWxpay().getBasic().getAppid() == null) {
                showMessage(data.getWxpay().getBasic().getReturn_msg());
                return;
            }
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

    String payStyle = "";

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.alpay:
                payStyle = MyConfig.ALIPAY;
                break;
            case R.id.wxpay:
                payStyle = MyConfig.WXPAY;
                break;
            case R.id.balance:
                payStyle = MyConfig.CREDIT;
                break;
            case R.id.buy:
                bottomSheetDialog.dismiss();
                pay(payStyle);
                break;
        }
        showPriceForDialog();
    }

    private void pay(String payStyle) {
        if (payStyle.isEmpty()) {
            showMessage("请选择支付方式！");
            return;
        }
        if (payStyle.equals(MyConfig.CREDIT)) {
            //原来的积分支付
            mPresenter.exchangeMallGood(mall.getGoods_id(), mallNumber, mall_address_id);
        } else {
            //使用钱支付
            mPresenter.useAliPayOrWxPay(mall, mallNumber, mall_address_id, payStyle);
        }
    }
}
