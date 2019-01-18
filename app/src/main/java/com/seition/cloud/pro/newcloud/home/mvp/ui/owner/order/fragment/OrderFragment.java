package com.seition.cloud.pro.newcloud.home.mvp.ui.owner.order.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.pay.ali.AliPayUtils;
import com.jess.arms.base.BaseBackFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.PayResponse;
import com.seition.cloud.pro.newcloud.app.bean.FragmentBean;
import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnline;
import com.seition.cloud.pro.newcloud.app.bean.money.BalanceDetails;
import com.seition.cloud.pro.newcloud.app.bean.offline.CourseOffline;
import com.seition.cloud.pro.newcloud.app.bean.order.Order;
import com.seition.cloud.pro.newcloud.app.bean.order.OrderRefund;
import com.seition.cloud.pro.newcloud.app.config.MyConfig;
import com.seition.cloud.pro.newcloud.app.utils.AdapterViewUtils;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.app.utils.WXPayUtils;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerOrderComponent;
import com.seition.cloud.pro.newcloud.home.di.module.OrderModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.OrderContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.OrderPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.course.activity.CourseDetailsFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.offline.fragment.OfflineDetailsFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.organization.fragment.OrganizationDetailsFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.owner.order.activity.OwnerOrderFragment;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.OrderListRecyclerAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * Created by addis on 2018/4/12.
 */

public class OrderFragment extends BaseBackFragment<OrderPresenter> implements OrderContract.View, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener, View.OnClickListener {
    @BindView(R.id.springview)
    SpringView springView;
    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    @Inject
    OrderListRecyclerAdapter adapter;

    private int pay_status;
    private String order_type;
    private String schoolId;

    public static OrderFragment newInstance(int pay_status, String order_type, String schoolId) {
        Bundle args = new Bundle();
        args.putInt("pay_status", pay_status);
        args.putString("order_type", order_type);
        args.putString("schoolId", schoolId);
        OrderFragment fragment = new OrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
        loadData(true);
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerOrderComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .orderModule(new OrderModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order_list, container, false);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (PreferenceUtil.getInstance(_mActivity).getBoolean("OdersNeedReload", false)) ;
        loadData(true);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        pay_status = getArguments().getInt("pay_status");
        order_type = getArguments().getString("order_type");
        schoolId = getArguments().getString("schoolId");
        initList();
        initPayDialog();

    }

    private void initList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));// 布局管理器
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        springView.setType(SpringView.Type.FOLLOW);

        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                springView.setEnableFooter(false);
                loadData(true);
            }

            @Override
            public void onLoadmore() {
                loadData(false);
            }
        });

        springView.setEnableFooter(false);
        adapter.setOnItemChildClickListener(this);
        adapter.setOnItemClickListener(this);
        springView.setHeader(new DefaultHeader(getActivity()));   //参数为：logo图片资源，是否显示文字
        springView.setFooter(new DefaultFooter(getActivity()));
        loadData(true);
    }

    /**
     * 此方法是让外部调用使fragment做一些操作的,比如说外部的activity想让fragment对象执行一些方法,
     * 建议在有多个需要让外界调用的方法时,统一传Message,通过what字段,来区分不同的方法,在setData
     * 方法中就可以switch做不同的操作,这样就可以用统一的入口方法做不同的事
     * <p>
     * 使用此方法时请注意调用时fragment的生命周期,如果调用此setData方法时onCreate还没执行
     * setData里却调用了presenter的方法时,是会报空的,因为dagger注入是在onCreated方法中执行的,然后才创建的presenter
     * 如果要做一些初始化操作,可以不必让外部调setData,在initData中初始化就可以了
     *
     * @param data
     */

    @Override
    public void setData(Object data) {

    }

    private void loadData(boolean pull) {
        if (schoolId != null && !schoolId.isEmpty())
            adapter.setOrganization(true);
        mPresenter.getOrders("course", pay_status + "", order_type, schoolId, true, pull);
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (pay_status == 3)//已完成的情况才请求数据
            mPresenter.getInitRefundConfig();
    }

    @Override
    public void hideLoading() {
        springView.onFinishFreshAndLoad();
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

    }

    @Override
    public void showFragment(ArrayList<FragmentBean> fragmenList) {

    }

    @Override
    public void setDatas(ArrayList<Order> datas, boolean pull) {
        if (pull) {
            adapter.setNewData(datas);
            if (datas.size() > 0) {
                if (datas.size() < mPresenter.count) {
                    if (adapter.getFooterViewsCount() == 0)
                        adapter.addFooterView(AdapterViewUtils.getNoDataViwe(_mActivity));
                    springView.setEnableFooter(false);//springView不可上拉
                } else {
                    adapter.removeAllFooterView();
                    springView.setEnableFooter(true);//springView可上拉
                }
            } else
                adapter.setEmptyView(AdapterViewUtils.getEmptyViwe(_mActivity));
        } else {
            adapter.addData(datas);
            if (datas.size() < mPresenter.count) {
                if (adapter.getFooterViewsCount() == 0)
                    adapter.addFooterView(AdapterViewUtils.getNoDataViwe(_mActivity));
                springView.setEnableFooter(false);
            } else {
                springView.setEnableFooter(true);
            }
        }
    }

    @Override
    public void showDialog(BalanceDetails balanceDetails) {
        ((TextView) bottomSheetDialog.findViewById(R.id.balance)).setText("   (当前账户余额为¥" + balanceDetails.getLearncoin_info().getBalance() + ")");
        ((TextView) bottomSheetDialog.findViewById(R.id.pay_price)).setText(bean.getPrice() + "");
        bottomSheetDialog.show();
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

    @Override
    public void showRefundOrderInfo(OrderRefund info) {

    }

    @Override
    public void reload() {
        loadData(true);
    }

    @Override
    public void showUploadAttachId(String attach_id) {

    }

    @Override
    public void notificationListData() {
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }

    private void toPayFromAliPayFromService(String orderInfo) {

        AliPayUtils aliPayUtils = new AliPayUtils(_mActivity);
        aliPayUtils.requestPayFromServiceSide(orderInfo);
        aliPayUtils.setPayListener(new AliPayUtils.OnAlipayListener() {
            @Override
            public void onSuccess() {
                super.onSuccess();
                loadData(true);
                showMessage("支付成功");
            }

            @Override
            public void onCancel() {
                super.onCancel();
                showMessage("取消支付");
            }
        });
    }

    Order bean;

    public void showReject(Order bean) {
        if (bean == null) return;
        AlertDialog.Builder builder = new AlertDialog.Builder(_mActivity);
        View dialogVview = getLayoutInflater().inflate(R.layout.dialog_message, null);
        TextView content = (TextView) dialogVview.findViewById(R.id.content);
        content.setText(bean.getReject_info());
        builder.setView(dialogVview);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        bean = (Order) adapter.getItem(position);
        switch (view.getId()) {
            case R.id.exit:
                switch (bean.getPay_status()) {
                    // 1未支付,2已取消,3已支付;4申请退款;5退款成功;6被驳回
                    case 1:
                        cancelOrder(bean.getOrder_type(), bean.getId());
                        break;
                    case 4://取消申请
//                        ((OwnerOrderFragment) getParentFragment()).startBrotherFragment(OrderReimburseFragment.newInstance(bean));
                        cancelApplicationForDrawbackOrder(bean.getOrder_type(), bean.getId());
//                        showMessage("暂不支持");
                        break;
                    case 6://查看驳回原因
//                        ((OwnerOrderFragment) getParentFragment()).startBrotherFragment(OrderReimburseFragment.newInstance(bean));
                        showReject(bean);
                        break;
                }

                break;
            case R.id.go_pay:
                switch (bean.getPay_status()) {
                    // 1未支付,2已取消,3已支付;4申请退款;5退款成功
                    case 1:
                        mPresenter.getBanlanceConfig();
                        break;
                    case 3:
                    case 6://去提交信息申请退款
                        ((OwnerOrderFragment) getParentFragment()).startBrotherFragment(OrderReimburseFragment.newInstance(bean));
                        break;
                }
                break;
            case R.id.info:
                break;
            case R.id.course:
                if (bean.getOrder_type() == 5) {
                    ((OwnerOrderFragment) getParentFragment()).startBrotherFragment(OfflineDetailsFragment.newInstance(bean.getVideo_id() + ""));
                } else {
                    String courseId = "";
                    boolean isLive = false;
                    if (bean.getOrder_type() == 4) {
                        courseId = bean.getVideo_id() + "";
                        isLive = false;
                    } else if (bean.getOrder_type() == 3) {
                        courseId = bean.getLive_id() + "";
                        isLive = true;
                    }

                    ((OwnerOrderFragment) getParentFragment()).startBrotherFragment(CourseDetailsFragment.newInstance(courseId, isLive, "", null));
//                    Intent intent = new Intent(_mActivity, CourseDetailsActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent.putExtra("courseId", courseId);
//                    intent.putExtra("isLive", isLive);
//                    launchActivity(intent);
                }
                break;
            case R.id.school_title:
//                String school_id = "";
//                if (bean.getOrder_type() == 5) {
//                    school_id = bean.getLine_class().getSchool_info().getSchool_id() + "";
//                } else {
//                    school_id = bean.getSource_info().getSchool_info().getSchool_id() + "";
//                }
//                ((OwnerOrderFragment) getParentFragment()).startBrotherFragment(OrganizationDetailsFragment.newInstance(school_id));
                break;
        }
    }

    private void cancelApplicationForDrawbackOrder(int order_type, int order_id) {
        new MaterialDialog.Builder(_mActivity)
                .content("是否取消退款申请？")
                .positiveText("确定")
                .negativeText("取消")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        mPresenter.cancelApplicationForDrawbackOrder(order_type, order_id);
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

    private void cancelOrder(int order_type, int order_id) {
        new MaterialDialog.Builder(_mActivity)
                .content("是否取消订单")
                .positiveText("确定")
                .negativeText("取消")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        mPresenter.orderCancel(order_type, order_id);
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

    BottomSheetDialog bottomSheetDialog;

    public void initPayDialog() {
        bottomSheetDialog = new BottomSheetDialog(_mActivity);
        bottomSheetDialog.setContentView(R.layout.dialog_select_pay);
        bottomSheetDialog.findViewById(R.id.alpay).setOnClickListener(this);
        bottomSheetDialog.findViewById(R.id.wxpay).setOnClickListener(this);
        bottomSheetDialog.findViewById(R.id.balance).setOnClickListener(this);
        bottomSheetDialog.findViewById(R.id.buy).setOnClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    String payStyle = MyConfig.ALIPAY;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.alpay:
                payStyle = MyConfig.ALIPAY;
                break;
            case R.id.wxpay:
                payStyle = MyConfig.WXPAY;
                break;
            case R.id.balance:
                payStyle = MyConfig.ICNPAY;
                break;
            case R.id.buy:
                bottomSheetDialog.dismiss();
                pay(payStyle);
                break;

        }
    }


    private void pay(String payStyle) {
        if (payStyle.isEmpty()) {
            showMessage("请选择支付方式！");
            return;
        }

        if (bean.getOrder_type() == 4)
            if (bean.getCourse_hour_id() > 0)
                mPresenter.buyCourseVideoItemWithWxOrAli(bean.getVideo_id() + "", bean.getCourse_hour_id() + "", payStyle, 1);
            else
                mPresenter.buyCourseVideo(bean.getVideo_id() + "", payStyle, bean.getCoupon_id());
        else if (bean.getOrder_type() == 3)
            if (bean.getCourse_hour_id() > 0)
                mPresenter.buyCourseVideoItemWithWxOrAli(bean.getLive_id() + "", bean.getCourse_hour_id() + "", payStyle, 2);
            else
                mPresenter.buyCourseLive(bean.getLive_id() + "", payStyle, bean.getCoupon_id());
        else if (bean.getOrder_type() == 5)
            mPresenter.buyCourseOffline(bean.getVideo_id() + "", payStyle, bean.getCoupon_id());
        else
            mPresenter.orderPay(bean.getOrder_type(), bean.getId(), bean.getCoupon_id(), payStyle);
    }
}
