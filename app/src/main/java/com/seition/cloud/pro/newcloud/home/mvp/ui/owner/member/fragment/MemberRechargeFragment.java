package com.seition.cloud.pro.newcloud.home.mvp.ui.owner.member.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.pay.ali.AliPayUtils;
import com.jess.arms.base.BaseBackFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.PayResponse;
import com.seition.cloud.pro.newcloud.app.bean.member.Member;
import com.seition.cloud.pro.newcloud.app.config.MyConfig;
import com.seition.cloud.pro.newcloud.app.receivers.WXPAYReceiver;
import com.seition.cloud.pro.newcloud.app.utils.Utils;
import com.seition.cloud.pro.newcloud.app.utils.WXPayUtils;
import com.seition.cloud.pro.newcloud.app.config.Service;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerMemberComponent;
import com.seition.cloud.pro.newcloud.home.di.module.MemberModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.MemberContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.MemberRechargePresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.MemberTypeGridRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.view.ShowAgreement;
import com.seition.cloud.pro.newcloud.widget.decoration.SpacesItemDecoration;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MemberRechargeFragment extends BaseBackFragment<MemberRechargePresenter> implements MemberContract.MemberRechargeView, BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.alpay)
    RadioButton alpay;
    @BindView(R.id.wxpay)
    RadioButton wxpay;

    @BindView(R.id.month_price_ll)
    LinearLayout month_price_ll;
    @BindView(R.id.year_price_ll)
    LinearLayout year_price_ll;

    @BindView(R.id.month_price_txt)
    TextView month_price_txt;
    @BindView(R.id.year_price_txt)
    TextView year_price_txt;

    @BindView(R.id.member_time_reduce)
    TextView member_time_reduce;
    @BindView(R.id.member_time_add)
    TextView member_time_add;
    @BindView(R.id.member_time_type)
    TextView member_time_type;
    @BindView(R.id.current_type)
    TextView current_type;
    @BindView(R.id.member_what)
    TextView member_what;

    @BindView(R.id.member_time_count)
    EditText member_time_count;

    @BindView(R.id.pay)
    TextView pay;
    @BindView(R.id.really_price)
    TextView really_price;

    @BindView(R.id.agree)
    CheckBox agree;

    @BindView(R.id.recycle_view)
    RecyclerView recycle_view;

    @Inject
    MemberTypeGridRecyclerAdapter adapter;


    @OnClick({R.id.month_price_ll, R.id.year_price_ll, R.id.alpay, R.id.pay, R.id.wxpay, R.id.member_time_reduce, R.id.member_time_add, R.id.agreement})
    void doSomething(View view) {
        switch (view.getId()) {
            case R.id.month_price_ll:
                month_price_ll.setBackgroundResource(R.drawable.shape_border_money_select);
                year_price_ll.setBackgroundResource(R.drawable.shape_border_gray);
                month_price_txt.setTextColor(getResources().getColor(R.color.red));
                year_price_txt.setTextColor(getResources().getColor(R.color.color_8));
                member_time_type.setText("月");
                vip_type_time = "month";
                singlePrice = Double.parseDouble(member.getVip_month());
                setReallyPrice();
                break;
            case R.id.year_price_ll:
                month_price_ll.setBackgroundResource(R.drawable.shape_border_gray);
                year_price_ll.setBackgroundResource(R.drawable.shape_border_money_select);
                month_price_txt.setTextColor(getResources().getColor(R.color.color_8));
                year_price_txt.setTextColor(getResources().getColor(R.color.red));
                member_time_type.setText("年");
                vip_type_time = "year";
                singlePrice = Double.parseDouble(member.getVip_year());
                setReallyPrice();
                break;
            case R.id.alpay:
                payStyle = MyConfig.ALIPAY;
                break;
            case R.id.pay:
                pay(payStyle);
                break;
            case R.id.wxpay:
                payStyle = MyConfig.WXPAY;
                break;
            case R.id.member_time_reduce:
                if (vip_time > 1)
                    vip_time--;
                member_time_count.setText(vip_time + "");
                setReallyPrice();
                break;
            case R.id.member_time_add:
                vip_time++;
                member_time_count.setText(vip_time + "");
                setReallyPrice();
                break;
            case R.id.agreement:
                getAgreement();
                break;
        }
    }

    void getAgreement() {
        ShowAgreement.showAgreement(_mActivity, getLayoutInflater(), agree, "vip");
    }


    public static MemberRechargeFragment newInstance(String vipGrade, boolean isVip, int currentVipType, Member member, ArrayList<Member> members) {
        Bundle args = new Bundle();
        args.putSerializable("member", member);
        args.putString("vipGrade", vipGrade);
        args.putBoolean("isVip", isVip);
        args.putInt("currentVipType", currentVipType);
        args.putParcelableArrayList("members", members);
        MemberRechargeFragment fragment = new MemberRechargeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerMemberComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .memberModule(new MemberModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_member_recharge, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setTitle(getResources().getString(R.string.member_recharge));
        initData();
        initView();
        registerReceivers();
        setMemberTypeSelect(member);
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

    private String vipGrade;
    private boolean isVip = false;
    private int currentVipType = 0;

    private void initData() {
        vipGrade = getArguments().getString("vipGrade");
        isVip = getArguments().getBoolean("isVip", false);
        currentVipType = getArguments().getInt("currentVipType", 0);
        member = (Member) getArguments().getSerializable("member");

        ArrayList<Member> alldatas = getArguments().getParcelableArrayList("members");

        System.out.println("isVip =" + isVip + "vipGrade =" + vipGrade + "currentVipType =" + currentVipType + "member.getId() =" + member.getId() + " =");
        for (int i = 0; i < alldatas.size(); i++) {
            if (Integer.parseInt(alldatas.get(i).getId()) >= currentVipType)
                datas.add(alldatas.get(i));
        }
    }

    private void initView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recycle_view.setLayoutManager(gridLayoutManager);// 布局管理器
        agree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                pay.setEnabled(isChecked);
                if (isChecked)
                    pay.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                else pay.setBackgroundColor(getResources().getColor(R.color.color_a5c3eb));

            }
        });
        recycle_view.setAdapter(adapter);
        adapter.setNewData(datas);
        recycle_view.addItemDecoration(new SpacesItemDecoration(Utils.dip2px(getActivity(), 0), Utils.dip2px(getActivity(), 10)));
        adapter.setOnItemClickListener(this);
        adapter.setSelectedItem(setCurrentPosition());

        member_time_count.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = member_time_count.getText().toString().trim();
                if (!"".equals(input))
                    vip_time = Integer.parseInt(input);
                else
                    vip_time = 1;
                setReallyPrice();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mPresenter.getPaySwitch();
    }

    private void setReallyPrice() {
        really_price.setText("¥" + vip_time * singlePrice);
    }

    private int user_vip;
    private String vip_type_time = "month";
    private int vip_time = 1;
    private double singlePrice = 0;


    private ArrayList<Member> datas = new ArrayList<>();
    Member member;

    private int setCurrentPosition() {
        for (int i = 0; i < datas.size(); i++) {
            if (Integer.parseInt(member.getId()) == Integer.parseInt(datas.get(i).getId()))
                return i;
        }
        return 0;
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
        mPresenter.rechargeVip(payStyle, user_vip, vip_type_time, vip_time);

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
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        this.adapter.setSelectedItem(position);
        this.adapter.notifyDataSetChanged();

        Member member = (Member) adapter.getItem(position);
        setMemberTypeSelect(member);
    }

    public void setMemberTypeSelect(Member member) {
        this.member = member;
        user_vip = Integer.parseInt(member.getId());
        month_price_txt.setText("¥" + member.getVip_month());
        year_price_txt.setText("¥" + member.getVip_year());

        if (vip_type_time.equals("month"))
            singlePrice = Double.parseDouble(member.getVip_month() + "");
        else if (vip_type_time.equals("year"))
            singlePrice = Double.parseDouble(member.getVip_year() + "");
        setReallyPrice();

        if (isVip)
            current_type.setText(vipGrade);


        if (!isVip)
            return;

        if (currentVipType < Integer.parseInt(member.getId()))
            member_what.setText("会员升级");
        if (currentVipType == Integer.parseInt(member.getId()))
            member_what.setText("会员续费");


    }

    String payStyle = "alipay";

    @Override
    public void showPayView(ArrayList<String> datas) {
        for (int i = 0; i < datas.size(); i++) {
            if (datas.get(i).equals("alipay")) {
                alpay.setVisibility(View.VISIBLE);
                if (i == 0) {
                    payStyle = "alipay";
//                    onClick(alpay);
                    alpay.setChecked(true);
                }
            }
            if (datas.get(i).equals("wxpay")) {
                wxpay.setVisibility(View.VISIBLE);
                if (i == 0) {
                    payStyle = "wxpay";
                    wxpay.setChecked(true);
                }
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
