package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnline;
import com.seition.cloud.pro.newcloud.app.bean.offline.CourseOffline;
import com.seition.cloud.pro.newcloud.app.bean.order.Order;
import com.seition.cloud.pro.newcloud.app.config.MyConfig;
import com.seition.cloud.pro.newcloud.app.utils.GlideLoaderUtil;

public class OrderListRecyclerAdapter extends BaseQuickAdapter<Order, BaseViewHolder> {

    public OrderListRecyclerAdapter() {
        super(R.layout.item_order);
    }

    private boolean isOrganization = false;

    public void setOrganization(boolean organization) {
        isOrganization = organization;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, Order bean) {

        String courseTitle = "";
        String courseInfo = "";
        String payMent = "";
        String paymentType = "";
        String type = "";
        String payStaus = "";

        String schoolTitle = "";
        String schoolImg = "";
        String courseImg = "";

        String exit = "";
        courseTitle = bean.getVideo_name();
        if (bean.getCourse_hour_id() > 0)
            courseTitle += "——" + bean.getCourse_hour_title();
        courseInfo = bean.getVideo_binfo();
        payMent = "¥" + bean.getPrice();
        courseImg = bean.getCover();

        if (MyConfig.isOpenAboutSchool) {
//            schoolTitle = online.getSchool_info().getTitle();
//            schoolImg = online.getSchool_info().getCover();
        }

        switch (bean.getPay_status()) {
            // 1未支付,2已取消,3已支付;4申请退款;5退款成功
            case 1:
                viewHolder.setVisible(R.id.go_pay, bean.getPrice() == 0 ? false : true);
                viewHolder.setVisible(R.id.exit, true);
                viewHolder.setTextColor(R.id.type, viewHolder.itemView.getContext().getResources().getColor(R.color.colorPrimary));
                paymentType = "应付款：";
                type = "未支付";
                payStaus = "去支付";
                exit = "取消订单";
                break;
            case 2:
                viewHolder.setVisible(R.id.go_pay, false);
                viewHolder.setVisible(R.id.exit, false);
                viewHolder.setTextColor(R.id.type, viewHolder.itemView.getContext().getResources().getColor(R.color.colorPrimary));
                paymentType = "应付款：";
                type = "已取消";
//                    payStaus ="已取消";
                break;
            case 3:
                viewHolder.setVisible(R.id.go_pay, MyConfig.isOpenRefund ? bean.getPrice() == 0 ? false : true : false);
                viewHolder.setVisible(R.id.exit, false);
                viewHolder.setTextColor(R.id.type, viewHolder.itemView.getContext().getResources().getColor(R.color.colorPrimary));
                paymentType = "实付款：";
                type = "已支付";
                payStaus = "申请退款";
                break;
            case 4:
                viewHolder.setVisible(R.id.go_pay, false);
                viewHolder.setVisible(R.id.exit, true);
                viewHolder.setTextColor(R.id.type, viewHolder.itemView.getContext().getResources().getColor(R.color.colorPrimary));
                paymentType = "实付款：";
                type = "退款审核中";
                exit = "取消申请";
                break;
            case 5:
                viewHolder.setVisible(R.id.go_pay, false);
                viewHolder.setVisible(R.id.exit, false);
                viewHolder.setTextColor(R.id.type, viewHolder.itemView.getContext().getResources().getColor(R.color.colorPrimary));
                paymentType = "实付款：";
                type = "退款成功";
                break;
            case 6:
                viewHolder.setVisible(R.id.go_pay, MyConfig.isOpenRefund ? bean.getPrice() == 0 ? false : true : false);
                viewHolder.setVisible(R.id.exit, true);
                viewHolder.setTextColor(R.id.type, viewHolder.itemView.getContext().getResources().getColor(R.color.red));
                type = "被驳回";
                exit = "查看原因";
                payStaus = "重新申请";
                break;
            case 7:
                viewHolder.setVisible(R.id.go_pay, false);
                paymentType = "实付款：";
                type = "已失效";
                break;
        }

        viewHolder.setText(R.id.course_title, courseTitle)
                .setText(R.id.course_info, courseInfo)
                .setText(R.id.payment, payMent)
                .setText(R.id.go_pay, payStaus)
                .setText(R.id.exit, exit)
                .setText(R.id.payment_type, paymentType)
                .setText(R.id.type, type)
                .addOnClickListener(R.id.exit)
                .addOnClickListener(R.id.go_pay)
                .addOnClickListener(R.id.info)
                .addOnClickListener(R.id.course)
                .addOnClickListener(R.id.school_title);
        if (isOrganization) {
            viewHolder.setVisible(R.id.go_pay, false);
            viewHolder.setVisible(R.id.exit, false);
        }
        if (MyConfig.isOpenAboutSchool && false) {
            viewHolder.setText(R.id.school_title, schoolTitle);
            GlideLoaderUtil.LoadImage(viewHolder.itemView.getContext(), schoolImg, (ImageView) viewHolder.getView(R.id.school_img));
        }
        GlideLoaderUtil.LoadImage(viewHolder.itemView.getContext(), courseImg, (ImageView) viewHolder.getView(R.id.course_img));

    }


}
