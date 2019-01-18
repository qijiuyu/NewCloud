package com.seition.cloud.pro.newcloud.home.mvp.ui.owner.money.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.pay.ali.AliPayUtils;
import com.jess.arms.base.BaseBackFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.MApplication;
import com.seition.cloud.pro.newcloud.app.bean.bind.BankBean;
import com.seition.cloud.pro.newcloud.app.bean.money.BalanceDetails;
import com.seition.cloud.pro.newcloud.app.bean.money.BalanceInfo;
import com.seition.cloud.pro.newcloud.app.bean.money.BalanceRechangeBean;
import com.seition.cloud.pro.newcloud.app.bean.money.CreditDetails;
import com.seition.cloud.pro.newcloud.app.bean.money.PayType;
import com.seition.cloud.pro.newcloud.app.bean.money.SpiltDetails;
import com.seition.cloud.pro.newcloud.app.config.MyConfig;
import com.seition.cloud.pro.newcloud.app.dialog.SelectBankDialog;
import com.seition.cloud.pro.newcloud.app.config.Service;
import com.seition.cloud.pro.newcloud.app.receivers.WXPAYReceiver;
import com.seition.cloud.pro.newcloud.app.utils.M;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerMoneyComponent;
import com.seition.cloud.pro.newcloud.home.di.module.MoneyModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.MoneyContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.MoneyPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.buy.activity.RechargeCardUseFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.bind.fragment.BankAddFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.bind.fragment.BindAliFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.money.adapter.RechangeAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.DialogBankSelectAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.view.ShowAgreement;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class OwnerMoneyFragment extends BaseBackFragment<MoneyPresenter> implements MoneyContract.View,
        BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener, RechangeAdapter.SaveEditListener {
    @BindView(R.id.money_number)
    EditText money_number;
    @BindView(R.id.toolbar_right_text)
    TextView toolbar_right_text;
    @BindView(R.id.price_symbol)
    TextView price_symbol;
    @BindView(R.id.funcation_name)
    TextView funcation_name;
    @BindView(R.id.head_money)
    TextView head_money;
    @BindView(R.id.head_info)
    TextView head_info;
    @BindView(R.id.pay_style)
    TextView pay_style;

    @BindView(R.id.pay_number)
    TextView pay_number;
    @BindView(R.id.commission_get_money)
    TextView commission_get_money;
    @BindView(R.id.add_money)
    TextView add_money;
    @BindView(R.id.info)
    TextView info;
    @BindView(R.id.commission)
    LinearLayout commission;
    @BindView(R.id.banlance)
    LinearLayout banlance;
    @BindView(R.id.card_ll)
    LinearLayout card_ll;
    //    @BindView(R.id.alpay_ll)
//    LinearLayout alpay_ll;
//    @BindView(R.id.wxpay_ll)
//    LinearLayout wxpay_ll;
//    @BindView(R.id.balance_ll)
//    LinearLayout balance_ll;
//    @BindView(R.id.commission_ll)
//    LinearLayout commission_ll;
    @BindView(R.id.bank_ll)
    LinearLayout bank_ll;


    @BindView(R.id.alpay_rb)
    RadioButton alpay_rb;
    @BindView(R.id.wxpay_rb)
    RadioButton wxpay_rb;
    @BindView(R.id.balance_rb)
    RadioButton balance_rb;
    @BindView(R.id.commission_rb)
    RadioButton commission_rb;
    @BindView(R.id.bank_rb)
    RadioButton bank_rb;
    @BindView(R.id.commission_bank_text)
    TextView commission_bank_text;
    @BindView(R.id.agree)
    CheckBox agree;
    @BindView(R.id.pay)
    TextView pay;
    @BindView(R.id.price_more)
    LinearLayout price_more;
    @BindView(R.id.more_money)
    EditText more_money;

    @Inject
    RechangeAdapter adapter;
    @BindView(R.id.recycle_view)
    RecyclerView recycle_view;

    private int type;

    String payStyle = "";

    private int[] sple_score = new int[]{1, 100};
    boolean isMore = false;

    @OnClick({R.id.pay, R.id.toolbar_right_text, R.id.agreement, R.id.alpay_rb
            , R.id.wxpay_rb, R.id.balance_rb, R.id.bank_rb, R.id.card_ll
            , R.id.commission_rb, R.id.commission_bank_ll, R.id.commission_get_money, R.id.price_more, R.id.more_money})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.price_more:
            case R.id.more_money:
                double temp = 0;
                double number = 0d;
                try {
                    temp = Double.valueOf(more_money.getText().toString());
                    number = temp;
                } catch (Exception e) {
                    e.printStackTrace();
                    if (number == 0) more_money.setText("");
                    else if (number == (int) number)
                        more_money.setText((int) number + "");
                    else
                        more_money.setText(number + "");
                }
                select(adapter, -1);
                showMoney(number);
                price_more.setBackgroundResource(R.drawable.shape_money_select);
                break;
            case R.id.pay:
                //确定支付的操作
//                mPresenter.pay();
                commit();
                break;
            case R.id.toolbar_right_text:
                start(MoneyDetailsListFragment.newInstance(type));
                //进入明细页
                break;
            case R.id.agreement:
                //进入协议详情页
                ShowAgreement.showAgreement(_mActivity, getLayoutInflater(), agree, "buy");
                break;
            case R.id.alpay_rb:
                payStyle = MyConfig.ALIPAY;
                break;
            case R.id.wxpay_rb:
                payStyle = MyConfig.WXPAY;
                break;
            case R.id.balance_rb:
                payStyle = MyConfig.ICNPAY;
                break;
            case R.id.bank_rb:
                if (card__list.size() == 0)
                    start(BankAddFragment.newInstance());
                payStyle = MyConfig.UNIONPAY;
                break;
            case R.id.card_ll:
                start(RechargeCardUseFragment.newInstance());
                break;
            case R.id.commission_rb:
                payStyle = MyConfig.SPIPAY;
                break;
            case R.id.commission_bank_ll:
                if (card__list.size() == 0)
                    start(BankAddFragment.newInstance());
                else
                    showSimpleList();
//                    showOwnerBank();   //选择银行卡
                break;
            case R.id.commission_get_money:
                if ((int) spilt.getSpilt_info().getBalance() == 0)
                    showMessage("您的收入为零");
                else {
                    money_number.setText((int) spilt.getSpilt_info().getBalance() + "");
                    showMoney((int) spilt.getSpilt_info().getBalance());
                }
                break;
        }

    }


    SelectBankDialog sbd;
    int positions = 0;

    private void showOwnerBank() {
        sbd = new SelectBankDialog.Builder(_mActivity)
                .setTitle("选择银行卡")
                .setList(card__list, new SelectBankDialog.bankSelect() {
                    @Override
                    public void selectClick(BankBean bean, int position) {
                        commission_bank_text.setText(bean.getCard_info());
                        positions = position;
                        commission_rb.setChecked(true);
                        if (sbd != null) sbd.dismiss();
                    }
                }, positions).setPositiveButton("添加新卡", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        start(BankAddFragment.newInstance());
                    }
                }).create();
        sbd.show();
    }

    DialogBankSelectAdapter selectAdapter = null;
    RecyclerView listview;
    RelativeLayout positive;

    public void showSimpleList() {
        MaterialDialog dialog =
                new MaterialDialog.Builder(_mActivity)
                        .customView(R.layout.dialog_select_bank_new, true)
                        .positiveText("")
                        .negativeText("")
                        .build();
        listview = (RecyclerView) dialog.getCustomView().findViewById(R.id.listview);
        listview.setLayoutManager(new LinearLayoutManager(_mActivity));// 布局管理器
        positive = (RelativeLayout) dialog.getCustomView().findViewById(R.id.positive);
        selectAdapter = new DialogBankSelectAdapter();

        listview.setAdapter(selectAdapter);
        selectAdapter.setNewData(card__list);
        selectAdapter.setSelectedItem(0);
        selectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                selectAdapter.setSelectedItem(position);
                BankBean bean = (BankBean) adapter.getItem(position);
                commission_bank_text.setText(bean.getCard_info());
                positions = position;
                selectAdapter.setSelectedItem(position);
                commission_rb.setChecked(true);
                dialog.dismiss();
            }
        });
        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                start(BankAddFragment.newInstance());
            }
        });
        dialog.show();

        /////


//        new MaterialDialog.Builder(_mActivity).title("选择银行卡").titleGravity(GravityEnum.CENTER).adapter(adapter, new LinearLayoutManager(_mActivity)).show();
    }

    public static OwnerMoneyFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putSerializable("type", type);
        OwnerMoneyFragment fragment = new OwnerMoneyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private double number = 0;
    private String give_number = "0";

    private int card_id;

    private void commit() {
        String exchange_money = money_number.getText().toString().trim();
        if (payStyle.isEmpty()) {
            showMessage("请选择支付方式！");
            return;
        }
        if (!agree.isChecked()) {
            showMessage(getString(R.string.procotol3));
            return;
        }
        switch (type) {
            case MoneyPresenter.TypeBanlance:
                //余额
                if (number == 0) {
                    showMessage("充值金额不能为0");
                } else {
                    String money = "";
                    if (give_number.equals("0"))
                        money = number + "";
                    else
                        money = (number == (int) number ? (int) number : number) + "=>" + give_number;
                    mPresenter.rechargeBanlance(payStyle, money);
                }
                break;
            case MoneyPresenter.TypeIncome:
                //收入
                if (payStyle == MyConfig.ALIPAY && isAliAccountBinded == false) {
                    showAliBindDialog();
                } else if (payStyle == MyConfig.UNIONPAY && card__list.size() == 0) {
                    showMessage("请先绑定您的银行卡！");
                } else
                    mPresenter.incomeToWithdraw(exchange_money, payStyle, card_id);
                break;
            case MoneyPresenter.TypeJifen:
                //积分
                if (TextUtils.isEmpty(exchange_money)) {
                    showMessage("请输入你要充值的积分");
                    return;
                }

                double exchangeMoney = Double.parseDouble(exchange_money);
                if ((payStyle == MyConfig.SPIPAY) && exchangeMoney / sple_score[1] > myIncome) {
                    showMessage("当前收入金额不足");
                    return;
                }
                if ((payStyle == MyConfig.ICNPAY) && exchangeMoney / sple_score[1] > myBanlance) {
                    showMessage("当前余额金额不足");
                    return;
                }
                int a = sple_score[1];
                double b = exchangeMoney / sple_score[1];
                mPresenter.recharge(payStyle, exchangeMoney / sple_score[0] + "");
                break;
        }
    }


    private void showAliBindDialog() {
        new MaterialDialog.Builder(_mActivity)
                .content("支付宝账号未绑定，是否去绑定？")
                .positiveText("确定")
                .negativeText("取消")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        start(BindAliFragment.newInstance());
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


    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerMoneyComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .moneyModule(new MoneyModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_owner_money, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initView();
        registerReceivers();
        agree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                showMoney(number);
            }
        });
        more_money.requestFocus();
        more_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String s1 = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String s2 = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                String s3 = s.toString();
                double temp = 0d;
                if (s3.isEmpty()) showMoney(0);
                else {
                    double number = 0d;
                    try {
                        temp = Double.valueOf(s3);
                        number = temp;
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (number == (int) number)
                            more_money.setText((int) number + "");
                        else
                            more_money.setText(number + "");
                    }
                    showMoney(number);
                }
            }
        });

        money_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                switch (type) {
                    case MoneyPresenter.TypeBanlance:
                        //余额

                        break;
                    case MoneyPresenter.TypeIncome:
                        //收入
                        try {
                            double num = Double.parseDouble(s.toString());
                            showMoney(num);
                            //这里还需要根据现收入总额判定输入的数字是否小于它，如果不小于则不允许进行提现操作
                        } catch (Exception e) {
                            e.printStackTrace();
                            showMoney(0);
                        }
                        break;
                    case MoneyPresenter.TypeJifen:
                        //积分
                        try {
                            double num = Double.parseDouble(s.toString());
                            if (num == 0) throw new Exception();
                            add_money.setVisibility(View.VISIBLE);
                            add_money.setText("需花费¥" + num / sple_score[1] * sple_score[0]);
                            showMoney(num / sple_score[1] * sple_score[0]);
                            double nn = num / sple_score[1] * sple_score[0];
                            if (nn > learn_balance && balance_rb.isChecked()) {
                                pay.setEnabled(false);
                                pay.setBackgroundColor(getResources().getColor(R.color.color_a5c3eb));
                            } else if (nn > spi_balance && commission_rb.isChecked()) {
                                pay.setEnabled(false);
                                pay.setBackgroundColor(getResources().getColor(R.color.color_a5c3eb));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            add_money.setText("需花费¥0");
                            showMoney(0);
                        }
                        break;
                }

            }
        });

//        mPresenter.getDetails(type);
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

    private double learn_balance, spi_balance;


    private void showMoney(double number) {
        if (number > 0 /*&& ((type == MoneyPresenter.TypeBanlance && agree.isChecked()) || type != MoneyPresenter.TypeBanlance)*/) {
            pay_number.setText("" + number);
            pay.setEnabled(agree.isChecked());
            if (agree.isChecked())
                pay.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            else
                pay.setBackgroundColor(getResources().getColor(R.color.color_a5c3eb));
        } else {
            pay_number.setText("0");
            pay.setEnabled(false);
            pay.setBackgroundColor(getResources().getColor(R.color.color_a5c3eb));
        }
        this.number = number;
    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        mPresenter.getDetails(type);
    }

    private void initView() {
        toolbar_right_text.setText(R.string.bill_details);

        type = getArguments().getInt("type", 0);
        if (type == R.id.balance_block)
            type = mPresenter.TypeBanlance;
        if (type == R.id.recttte_block)
            type = mPresenter.TypeIncome;
        if (type == R.id.integral_block)
            type = mPresenter.TypeJifen;

        switch (type) {
            case MoneyPresenter.TypeBanlance:
                //余额
                setTitle(R.string.owner_banlance);
                head_info.setText("账户余额");
                funcation_name.setText(R.string.recharge);
                banlance.setVisibility(View.VISIBLE);
                recycle_view.setLayoutManager(new GridLayoutManager(_mActivity, 2));// 布局管理器
                recycle_view.setAdapter(adapter);
                adapter.setEditListener(this);
                adapter.setOnItemClickListener(this);
                adapter.setOnItemChildClickListener(this);
                recycle_view.setItemAnimator(new DefaultItemAnimator());
                break;
            case MoneyPresenter.TypeIncome:
                //收入
                setTitle(R.string.owner_income);
                head_info.setText("账户收入");
                funcation_name.setText(R.string.commission_withdraw);
                commission.setVisibility(View.VISIBLE);
                pay_style.setText("提现方式");
                break;
            case MoneyPresenter.TypeJifen:
                //积分
                setTitle(R.string.owner_by_getting_number);
                head_info.setText("账户积分");
                funcation_name.setText(R.string.recharge_integral);
                commission.setVisibility(View.VISIBLE);
                add_money.setVisibility(View.GONE);
                commission_get_money.setVisibility(View.GONE);
                price_symbol.setVisibility(View.GONE);
                break;
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
        pop();
    }


    double myBanlance;
    double myIncome;

    private void showPayType(ArrayList<PayType> list) {
        if (list == null) return;
        boolean haveAliPay = false;
        for (int i = 0; i < list.size(); i++) {
            PayType payType = list.get(i);
            switch (payType.getPay_num()) {
                case "lcnpay"://充积分，余额
                    balance_rb.setVisibility(View.VISIBLE);
                    balance_rb.setText("    (" + payType.getPay_type_note() + ")");
                    if (type == MoneyPresenter.TypeJifen)
                        myBanlance = payType.getBalance();
                    if (i == 0) {
                        balance_rb.setChecked(true);
                        payStyle = MyConfig.ICNPAY;
                    }
                    break;
                case "spipay"://充积分，收入
                    commission_rb.setVisibility(View.VISIBLE);//commission_ll  commission_rb
                    String not = payType.getPay_type_note();
                    commission_rb.setText("    (" + payType.getPay_type_note() + ")");
                    if (type == MoneyPresenter.TypeJifen)
                        myIncome = payType.getBalance();
//                    commission_rb.setText();
                    if (i == 0) {
                        commission_rb.setChecked(true);
                        payStyle = MyConfig.SPIPAY;
                    }
                    break;
                case "unionpay"://提现，银行卡
                    bank_ll.setVisibility(View.VISIBLE);
                    bank_rb.setVisibility(View.VISIBLE);
                    card__list = payType.getCard_list();
                    if (card__list.size() > 0) {
                        card_id = card__list.get(0).getId();
                        commission_bank_text.setText(card__list.get(0).getCard_info());
                    } else
                        commission_bank_text.setText("去绑定");
                    if (i == 0) {
                        bank_rb.setChecked(true);
                        payStyle = MyConfig.UNIONPAY;
                    }
                    break;
                case "alipay"://提现，支付宝
                    haveAliPay = true;
                    alpay_rb.setVisibility(View.VISIBLE);//alpay_ll  alpay_rb
                    if (payType.getPay_type_note() == null || payType.getPay_type_note().trim().isEmpty()) {
//                        alpay_rb.setText("    (去绑定)");
                        isAliAccountBinded = false;
                    } else
                        isAliAccountBinded = true;
                    if (i == 0) {
                        alpay_rb.setChecked(true);
                        payStyle = MyConfig.ALIPAY;
                    }
                    break;
            }
        }
        if (haveAliPay) {
            alpay_rb.setChecked(true);
            payStyle = MyConfig.ALIPAY;
        }
    }

    boolean isAliAccountBinded = false;//只用来判断我的收入 界面中 阿里账号是否绑定

    private ArrayList<BankBean> card__list = new ArrayList<>();

    private void showPay(ArrayList<String> list) {
        if (list == null) return;
        for (int i = 0; i < list.size(); i++) {
            String payType = list.get(i);
            switch (payType) {
                case "cardpay"://充值卡充值
                    card_ll.setVisibility(View.VISIBLE);
                    break;
                case "wxpay"://微信充值
                    wxpay_rb.setVisibility(View.VISIBLE);//wxpay_ll  wxpay_rb
                    if (i == 0) {
                        payStyle = MyConfig.WXPAY;
                        wxpay_rb.setChecked(true);
                    }
                    break;
                case "alipay"://支付宝充值
                    alpay_rb.setVisibility(View.VISIBLE);//alpay_ll  alpay_rb
                    if (i == 0) {
                        payStyle = MyConfig.ALIPAY;
                        alpay_rb.setChecked(true);
                    }
                    break;
            }
        }
    }


    private void invisibleAll() {
        alpay_rb.setVisibility(View.GONE);//alpay_ll  alpay_rb
        wxpay_rb.setVisibility(View.GONE);//wxpay_ll  wxpay_rb
        balance_rb.setVisibility(View.GONE);//balance_ll  balance_rb
        commission_rb.setVisibility(View.GONE);//commission_ll  commission_rb
        bank_ll.setVisibility(View.GONE);
        bank_rb.setVisibility(View.GONE);
        card_ll.setVisibility(View.GONE);

        alpay_rb.setText("");
        wxpay_rb.setText("");
        balance_rb.setText("");
        commission_rb.setText("");
        commission_bank_text.setText("");
    }

    private void showPayMondyCount(ArrayList<BalanceRechangeBean> list) {
        isFirst = false;
        if (list == null) list = new ArrayList<>();
//        list.add(new BalanceRechangeBean());
        if (list.size() > 0) {
            list.get(0).setSelect(true);
            showMoney(list.get(0).getRechange());
            give_number = list.get(0).getGive() + "";
        }
        adapter.setNewData(list);
    }

    public void showPublicInfo(BalanceInfo balanceInfo, String payNote, boolean isBalance) {
        info.setText(payNote);
        if (balanceInfo == null) return;
        if (isBalance)
            head_money.setText(balanceInfo.getBalance() + "");
        else
            head_money.setText(balanceInfo.getScore() + "");
        invisibleAll();
    }

    boolean isFirst = true;

    @Override
    public void showBalance(BalanceDetails balance) {
        showPublicInfo(balance.getLearncoin_info(), balance.getPay_note(), true);
        showPay(balance.getPay());
        if (isFirst)
            showPayMondyCount(balance.getRechange_default());
    }

    SpiltDetails spilt;

    @Override
    public void showSpilt(SpiltDetails spilt) {
        this.spilt = spilt;
        showPublicInfo(spilt.getSpilt_info(), spilt.getPay_note(), true);
        if (spilt.getSpilt_info() != null)
            add_money.setText("当前已得到收入为¥" + spilt.getSpilt_info().getBalance() + " , ");
        showPayType(spilt.getPay_type());
    }

    @Override
    public void showCredit(CreditDetails credit) {
        learn_balance = credit.getCredit_info().getScore();
        String ss = credit.getPay_note();
        int index = ss.lastIndexOf("：");
        String s1 = ss.charAt(index - 1) + "";
        String s2 = ss.substring(index + 1, ss.length());
        sple_score[0] = Integer.parseInt(s1);
        sple_score[1] = Integer.parseInt(s2);
        showPublicInfo(credit.getCredit_info(), credit.getPay_note(), false);
        showPayType(credit.getPay_type());
    }

    @Override
    public void toAliPay(String orderInfo) {
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

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        BalanceRechangeBean balanceRechangeBean = (BalanceRechangeBean) adapter.getItem(position);
        double number = balanceRechangeBean.getRechange();
        give_number = balanceRechangeBean.getGive() + "";
//        pay_number.setText(number+"");
        showMoney(number);
        select(adapter, position);
        isMore = false;
        price_more.setBackgroundResource(R.drawable.shape_money_unselect);
    }

    public void select(BaseQuickAdapter adapter, int position) {
        if (adapter == null) return;
        for (int i = 0; i < adapter.getData().size(); i++) {
            if (adapter.getItem(i) instanceof BalanceRechangeBean)
                if (position == i)
                    ((BalanceRechangeBean) adapter.getItem(i)).setSelect(true);
                else
                    ((BalanceRechangeBean) adapter.getItem(i)).setSelect(false);
        }
        adapter.notifyDataSetChanged();
    }

//    boolean isDefineNumber;
//    int inPutnumberPosition;

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//        if (view.getId() == R.id.more_money) {
//            isDefineNumber = true;
//            inPutnumberPosition = position;
//            String inputNumber = ((EditText)view).getText().toString();
//            if(!inputNumber.isEmpty())
//            number =  Double.parseDouble(inputNumber);
//        }
    }

    @Override
    public void SaveEdit(int position, String string) {
        double number = Double.parseDouble(string);
        give_number = "0";
        showMoney(number);
    }
}
