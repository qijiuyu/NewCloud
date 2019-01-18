package com.seition.cloud.pro.newcloud.home.api.service;

import com.jess.arms.base.bean.DataBean;
import com.seition.cloud.pro.newcloud.app.PayResponse;
import com.seition.cloud.pro.newcloud.app.bean.ChangeFaceResponse;
import com.seition.cloud.pro.newcloud.app.bean.ReceiveGoodsAddress;
import com.seition.cloud.pro.newcloud.app.bean.ReceiveGoodsAddresss;
import com.seition.cloud.pro.newcloud.app.bean.common.FollowState;
import com.seition.cloud.pro.newcloud.app.bean.course.CourseVideoFreeTime;
import com.seition.cloud.pro.newcloud.app.bean.money.Data_BalanceDetails;
import com.seition.cloud.pro.newcloud.app.bean.money.Data_CreditDetails;
import com.seition.cloud.pro.newcloud.app.bean.money.Data_SpiltDetails;
import com.seition.cloud.pro.newcloud.app.bean.money.MoneyDetailResponse;
import com.seition.cloud.pro.newcloud.app.bean.note.NoteComment;
import com.seition.cloud.pro.newcloud.app.bean.note.NoteComments;
import com.seition.cloud.pro.newcloud.app.bean.note.Notes;
import com.seition.cloud.pro.newcloud.app.bean.order.OrderRefund;
import com.seition.cloud.pro.newcloud.app.bean.order.Orders;
import com.seition.cloud.pro.newcloud.app.bean.study.StudyRecord;
import com.seition.cloud.pro.newcloud.app.bean.user.MessageUserInfo;
import com.seition.cloud.pro.newcloud.app.bean.user.User;
import com.seition.cloud.pro.newcloud.app.bean.user.UserAccount;
import com.seition.cloud.pro.newcloud.app.bean.user.UserCount;
import com.seition.cloud.pro.newcloud.app.bean.user.UserMember;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by addis on 2018/5/8.
 */

public interface UserService {
    String StudyList = "user.getRecordList";
    //添加学习记录
    String AddStudy = "user.addRecord";
    //课程免费播放时长/秒
    String VideoFreeTime = "video.getFreeTime";
    //删除学习记录
    String DeleteStudy = "user.deleteRecord";
    //订单列表
    String ExchangeList = "order.getCourseOrderList";
    //兑换记录
    String GetGoodsOrderList = "order.getGoodsOrderList";

    //订单-申请退款
    String OrderRefunded = "order.refund";
    //订单-取消订单
    String OrderCancel = "order.cancel";
    //订单-取消退款申请
    String OrderDrawbackCancel = "order.cancelRefund";
    //订单-继续支付订单
    String OrderPay = "order.pay";


    //订单-订单退款详情
    String RefundInfo = "order.refundInfo";

    @POST(RefundInfo)
    Observable<OrderRefund> refundOrderInfo(@Header("en-params") String en_params, @Header("oauth-token") String token);

    @POST(OrderRefunded)
    Observable<DataBean> orderRefund(@Header("en-params") String en_params, @Header("oauth-token") String token);

    @POST(OrderCancel)
    Observable<DataBean> orderCancel(@Header("en-params") String en_params, @Header("oauth-token") String token);

    @POST(OrderDrawbackCancel)
    Observable<DataBean> cancelApplicationForDrawbackOrder(@Header("en-params") String en_params, @Header("oauth-token") String token);

    @POST(OrderPay)
    Observable<PayResponse> orderPay(@Header("en-params") String en_params, @Header("oauth-token") String token);

    //	用户-获取用户信息
    String UserInfo = "user.getInfo";

    String follow = "user.follow";
    String unFollow = "user.unfollow";

    //用户-获取用户统计数据
    String UserCounT = "user.getCount";
    //用户-获取用户会员信息	user.getUserVip
    String UserVip = "user.getUserVip";

    //用户-获取当前用户的流水等数据	user.getAccount
    String UserAccount = "user.getAccount";

    String BANLANCE_CONFIG = "user.balanceConfig";//余额配置
    String SPILT_CONFIG = "user.spiltConfig";//收入配置
    String CREDIT_CONFIG = "user.creditConfig";//积分配置


    //用户-获取账户余额流水列表
    String BalanceList = "user.getBalanceList";
    //用户-获取收入流水列表
    String SpiltList = "user.getSpiltList";
    //用户-获取积分流水列表
    String CreditList = "user.getCreditList";


    String SetUserInfo = "user.setInfo";//设置用户信息
    String SetUserFace = "user.setFace";//修改用户头像

    @POST(UserInfo)
    Observable<MessageUserInfo> getUserInfo(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    @POST(UserCounT)
    Observable<UserCount> getUserCount(@Header("oauth-token") String oauthToken);

    @POST(UserAccount)
    Observable<UserAccount> getUserAccount(@Header("oauth-token") String oauthToken);

    @POST(BANLANCE_CONFIG)
    Observable<Data_BalanceDetails> getBanlanceConfig(@Header("oauth-token") String oauthToken);

    @POST(SPILT_CONFIG)
    Observable<Data_SpiltDetails> getSpiltConfig(@Header("oauth-token") String oauthToken);

    @POST(CREDIT_CONFIG)
    Observable<Data_CreditDetails> getCreditConfig(@Header("oauth-token") String oauthToken);

    @POST(BalanceList)
    Observable<MoneyDetailResponse> getBalanceList(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    @POST(SpiltList)
    Observable<MoneyDetailResponse> getSpiltList(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    @POST(CreditList)
    Observable<MoneyDetailResponse> getCreditList(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);


    @POST(UserVip)
    Observable<UserMember> getUserVip(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    @POST(StudyList)
    Observable<StudyRecord> getStudyRecord(@Header("oauth-token") String oauthToken);

    @POST(AddStudy)
    Observable<DataBean> addStudyRecord(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    @POST(VideoFreeTime)
    Observable<CourseVideoFreeTime> getVideoFreeTime(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    @POST(DeleteStudy)
    Observable<DataBean> deleteStudyRecord(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    @POST(GetGoodsOrderList)
    Observable<Orders> getExchangeRecord(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);


    @Multipart
    @POST(SetUserFace)
    Observable<ChangeFaceResponse> setUserFace(@Header("oauth-token") String oauthToken, @Part() MultipartBody.Part fils);

    @POST(SetUserInfo)
    Observable<DataBean> setUserInfo(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    @POST(follow)
    Observable<FollowState> doFollow(@Header("en-params") String en_params, @Header("oauth-token") String token);

    @POST(unFollow)
    Observable<FollowState> cancelFollow(@Header("en-params") String en_params, @Header("oauth-token") String token);

    // 收货地址-获取我的收货地址列表
    String ReceiveAddress = "address.getMyList";

    @POST(ReceiveAddress)
    Observable<ReceiveGoodsAddresss> receiveAddresses(@Header("oauth-token") String token);

    // 收货地址-添加
    String ReceiveAddressAdd = "address.add";

    @POST(ReceiveAddressAdd)
    Observable<ReceiveGoodsAddress> addReceiveAddress(@Header("en-params") String en_params, @Header("oauth-token") String token);

    // 收货地址-修改
    String ReceiveAddressChange = "address.set";

    @POST(ReceiveAddressChange)
    Observable<ReceiveGoodsAddress> changeReceiveAddress(@Header("en-params") String en_params, @Header("oauth-token") String token);

    // 收货地址-删除
    String ReceiveAddressDelete = "address.delete"; // 收货地址-获取我的收货地址列表

    @POST(ReceiveAddressDelete)
    Observable<ReceiveGoodsAddress> deleteReceiveAddress(@Header("en-params") String en_params, @Header("oauth-token") String token);

    // 收货地址-默认
    String ReceiveAddressDefault = "address.setDefault";

    @POST(ReceiveAddressDefault)
    Observable<ReceiveGoodsAddress> setDefaultReceiveAddress(@Header("en-params") String en_params, @Header("oauth-token") String token);


    //我的笔记列表
    String NoteList = "notes.getMyList";
    //二级数据列表
    String NoteCommentList = "notes.getList";
    //二级数据列表
    String NoteAdd = "notes.add";

    @POST(NoteList)
    Observable<Notes> getMyNotes(@Header("en-params") String en_params, @Header("oauth-token") String token);

    @POST(NoteCommentList)
    Observable<NoteComments> getMyNoteComments(@Header("en-params") String en_params, @Header("oauth-token") String token);

    @POST(NoteAdd)
    Observable<NoteComment> addNoteComments(@Header("en-params") String en_params, @Header("oauth-token") String token);


    @POST(ExchangeList)
    Observable<Orders> getOrders(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    //全局-意见反馈
    String FeedBack = "home.feedback";

    @POST(FeedBack)
    Observable<DataBean> feedBack(@Header("en-params") String en_params, @Header("oauth-token") String token);

    //购买-线下课程
    String BuyCourseOffline = "course.buyLineCourse";
    //购买-点播课程
    String BuyCourseVideo = "course.buyVideo";
    //购买-点播课程课时
    String BuyCourseVideoItem = "course.buyCourseHourById";
    //购买-直播课程
    String BuyCourseLive = "course.buyLive";
    //订单-加入看单
    String AddFreeOrder = "order.addFreeOrder";

    @POST(AddFreeOrder)
    Observable<DataBean> addFreeOrder(@Header("en-params") String en_params, @Header("oauth-token") String token);

    @POST(BuyCourseOffline)
    Observable<PayResponse> buCourseOffline(@Header("en-params") String en_params, @Header("oauth-token") String token);

    @POST(BuyCourseOffline)
    Observable<DataBean> buCourseOffline1(@Header("en-params") String en_params, @Header("oauth-token") String token);

    @POST(BuyCourseVideo)
    Observable<PayResponse> buCourseVideo(@Header("en-params") String en_params, @Header("oauth-token") String token);

    @POST(BuyCourseVideoItem)
    Observable<PayResponse> buyCourseVideoItem(@Header("en-params") String en_params, @Header("oauth-token") String token);

    @POST(BuyCourseVideo)
    Observable<DataBean> buCourseVideoNoWxOrAli(@Header("en-params") String en_params, @Header("oauth-token") String token);

    @POST(BuyCourseLive)
    Observable<PayResponse> buCourseLive(@Header("en-params") String en_params, @Header("oauth-token") String token);


    // 充值余额
    String RechargeBanlance = "user.rechargeBalance";
    // 用户 -充值
    String Recharge = "user.recharge";

    String RechargeJifen = "user.rechargeScore";

    //	处理收入 转为余额/提现
    String Withdraw = "user.applySpiltWithdraw";

    @POST(RechargeBanlance)
    Observable<PayResponse> rechargeBanlance(@Header("en-params") String en_params, @Header("oauth-token") String token);//余额

    @POST(Recharge)
    Observable<DataBean> recharge(@Header("en-params") String en_params, @Header("oauth-token") String token);//积分

    @POST(RechargeJifen)
    Observable<DataBean> rechargeJifen(@Header("en-params") String en_params, @Header("oauth-token") String token);//积分

    @POST(Withdraw)
    Observable<DataBean> incomeToWithdraw(@Header("en-params") String en_params, @Header("oauth-token") String token);//收入


    @GET("https://graph.qq.com/oauth2.0/me?unionid=1")
    Observable<DataBean> getUnionId(@Query("access_token") String access_token);

    String RechargeVip = "user.rechargeVip";

    @POST(RechargeVip)
    Observable<PayResponse> rechargeVip(@Header("en-params") String en_params, @Header("oauth-token") String token);
}
