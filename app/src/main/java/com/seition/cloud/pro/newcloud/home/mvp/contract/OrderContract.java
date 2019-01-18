package com.seition.cloud.pro.newcloud.home.mvp.contract;

import com.jess.arms.base.bean.DataBean;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.seition.cloud.pro.newcloud.app.PayResponse;
import com.seition.cloud.pro.newcloud.app.bean.FragmentBean;
import com.seition.cloud.pro.newcloud.app.bean.UploadResponse;
import com.seition.cloud.pro.newcloud.app.bean.config.RefundConfig;
import com.seition.cloud.pro.newcloud.app.bean.login.RegisterTypeInit;
import com.seition.cloud.pro.newcloud.app.bean.money.BalanceDetails;
import com.seition.cloud.pro.newcloud.app.bean.money.Data_BalanceDetails;
import com.seition.cloud.pro.newcloud.app.bean.order.Order;
import com.seition.cloud.pro.newcloud.app.bean.order.OrderRefund;
import com.seition.cloud.pro.newcloud.app.bean.order.Orders;

import java.util.ArrayList;

import io.reactivex.Observable;
import okhttp3.MultipartBody;


public interface OrderContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void showFragment(ArrayList<FragmentBean> fragmenList);

        void setDatas(ArrayList<Order> orders, boolean pull);

        void showDialog(BalanceDetails balanceDetails);

        void showPayResult(PayResponse data);

        void showRefundOrderInfo(OrderRefund info);

        void reload();

        void showUploadAttachId(String attach_id);

        void notificationListData();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<RefundConfig> getInitRefundConfig();

        Observable<PayResponse> buyCourseVideoItem(String vids, String sid, String pay_for, int vtype);

        Observable<Orders> getOrders(String type, String pay_status, String orderType, String schoolId, int page, int count, boolean isCache);

        Observable<OrderRefund> refundOrderInfo(int order_type, int order_id);

        Observable<DataBean> orderRefund(int order_type, int order_id, String refund_reason, String refund_note, String voucher);

        Observable<DataBean> orderCancel(int order_type, int order_id);

        Observable<DataBean> cancelApplicationForDrawbackOrder(int order_type, int order_id);

        Observable<PayResponse> orderPay(int order_type, int order_id, int coupon_id, String pay_for);

        Observable<Data_BalanceDetails> getBanlanceConfig();

        Observable<PayResponse> buyCourseVideo(String vids, String pay_for, int coupon_id);

        Observable<PayResponse> buyCourseLive(String live_id, String pay_for, int coupon_id);

        Observable<PayResponse> buyCourseOffline(String vids, String pay_for, int coupon_id);

        Observable<DataBean> buyCourseOffline1(String vids, String pay_for, int coupon_id);//其他支付

        Observable<UploadResponse> uploadFile(MultipartBody.Part file);
    }
}
