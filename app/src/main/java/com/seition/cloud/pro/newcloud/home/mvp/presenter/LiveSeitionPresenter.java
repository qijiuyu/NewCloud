package com.seition.cloud.pro.newcloud.home.mvp.presenter;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import com.bokecc.dwlivedemo_new.activity.PcLivePlayActivity;
import com.bokecc.dwlivedemo_new.activity.ReplayActivity;
import com.bokecc.dwlivedemo_new.base.BasePopupWindow;
import com.bokecc.dwlivedemo_new.popup.TxtLoadingPopup;
import com.bokecc.sdk.mobile.live.DWLive;
import com.bokecc.sdk.mobile.live.DWLiveLoginListener;
import com.bokecc.sdk.mobile.live.Exception.DWLiveException;
import com.bokecc.sdk.mobile.live.pojo.PublishInfo;
import com.bokecc.sdk.mobile.live.pojo.RoomInfo;
import com.bokecc.sdk.mobile.live.pojo.TemplateInfo;
import com.bokecc.sdk.mobile.live.pojo.Viewer;
import com.bokecc.sdk.mobile.live.replay.DWLiveReplay;
import com.bokecc.sdk.mobile.live.replay.DWLiveReplayLoginListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.seition.cloud.pro.newcloud.app.bean.common.Section;
import com.seition.cloud.pro.newcloud.app.bean.course.SeitionDetails;
import com.seition.cloud.pro.newcloud.app.bean.course.SeitionDetailsBean;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.home.mvp.contract.CourseContract;
import com.seition.cloud.pro.newcloud.home.mvp.ui.login.activity.LoginActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

import static com.bokecc.dwlivedemo_new.util.LoginUtil.toast;
import static com.jess.arms.utils.ArmsUtils.startActivity;

@ActivityScope
public class LiveSeitionPresenter extends BasePresenter<CourseContract.Model, CourseContract.LiveSeitionView>
        implements BaseQuickAdapter.OnItemClickListener/*, VodSite.OnVodListener*/ {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private ArrayList<Section> sections;
    private boolean isBuy;
    private boolean isFree;

    @Inject
    public LiveSeitionPresenter(CourseContract.Model model, CourseContract.LiveSeitionView rootView) {
        super(model, rootView);
    }

    public void setLiveSeition(ArrayList<Section> sections, boolean isBuy, boolean free) {
        this.sections = sections;
        this.isBuy = isBuy;
        this.isFree = free;
        mRootView.showSeition(sections);
    }

    public void getLiveSeitionDetails(Section section) {
        mRootView.showLoading();
        mModel.getLiveSeitionDetails(section.getLive_id(), section.getId())
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (mRootView != null)
                        mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<SeitionDetailsBean>(mErrorHandler) {
                    @Override
                    public void onNext(SeitionDetailsBean data) {
                        if (data.getData() != null && data.getData().getBody() != null)
                            startToLive(data.getData());
                        else mRootView.showMessage(data.getMsg());
                    }
                });
    }

    public void startToLive(SeitionDetails seitionDetails) {
        switch (seitionDetails.getType()) {
            case 1://展示互动
//                startZSHD(seitionDetails);
//                break;
//            case 3://光慧直播（已取消）
//
//                break;
            case 4://CC直播
                initCCLive();
                startCC(seitionDetails);
                break;
//            case 5://微吼直播
//                startVH(seitionDetails);
//                break;
//            case 6://CC小班课
//                startCCSmallClass(seitionDetails);
//                break;
            default:
                mRootView.showMessage("暂不支持此直播类型！");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        //直播课时播放
        if (TextUtils.isEmpty(PreferenceUtil.getInstance(mApplication).getString("oauth_token", null)))
            mRootView.launchActivity(new Intent(mApplication, LoginActivity.class).putExtra("SkipToHome", false));
        else {
            Section section = (Section) adapter.getItem(position);
            if (isBuy || section.getIs_buy() == 1)
                if (!section.getNote().equals("已结束"))
                    getLiveSeitionDetails(section);
                else mRootView.showMessage("该直播已结束");
            else if (isFree) mRootView.showMessage("该直播需要购买才可观看");
            else if (section.getCourse_hour_price() > 0)
                mRootView.toBuySection(section);//去购买
            else mRootView.showMessage("该直播需要购买才可观看");
        }
    }
    /*

     */
/**
 * 进入CC小班课
 *
 * @param seitionDetails
 *//*

    public void startCCSmallClass(SeitionDetails seitionDetails) {
        String url = "";
        String pwd = "";
        if (1 == seitionDetails.getBody().getIs_teacher()) {
            url = seitionDetails.getBody().getTeacher_join_url();
            pwd = seitionDetails.getBody().getTeacher_join_pwd();
        } else {
            url = seitionDetails.getBody().getStudent_join_url();
            pwd = seitionDetails.getBody().getStudent_join_pwd();
        }
        parseUrl(seitionDetails.getBody().getAccount(), url, pwd);
    }

    private void parseUrl(String name, String url, String pwd) {
        ParseMsgUtil.parseUrl(mApplication, name, pwd, url, new ParseMsgUtil.ParseCallBack() {
            @Override
            public void onStart() {
//                if (mProgressDialog.isShowing()) {
//                    return;
//                }
//                mProgressDialog.show();
            }

            @Override
            public void onSuccess() {
//                dismissProgress();
//                if (mProgressDialog.isShowing()) {
//                    mProgressDialog.dismiss();
//                }
            }

            @Override
            public void onFailure(String err) {
                mRootView.showMessage(err);
//                toastOnUiThread(err);
//                dismissProgress();
            }
        });
    }
*/


/*

    View view;

    public void setView(View view) {
        this.view = view;
    }

    */

    /**
     * 进入微吼直播
     *
     * @param seitionDetails
     */
/*
    public void startVH(SeitionDetails seitionDetails) {
        this.seitionDetails = seitionDetails;
        getVHId();
    }

    SeitionDetails seitionDetails;

    public void VHlogin(String username, String userpass) {
        VhallSDK.login(username, userpass, new UserInfoDataSource.UserInfoCallback() {
            @Override
            public void onSuccess(UserInfo userInfo) {
                if (intent != null || param != null) {
                    param.userCustomId = userInfo.user_id;
                    param.userName = userInfo.nick_name;
                    intent.putExtra("param", param);
                    startActivity(intent);
                } else mRootView.showMessage("直播开启失败！");
                param = null;
                intent = null;
            }

            @Override
            public void onError(int errorCode, String reason) {
                mRootView.showMessage(reason);
                param = null;
                intent = null;
            }
        });
    }

    Param param = null;
    Intent intent = null;

    private void VHStart(VHBean data) {
        intent = new Intent(mApplication, WatchActivity.class);
        param = new Param();
        param.userName = data.getName();
//            if (data.getUser_id() != null && !data.getUser_id().isEmpty()) param.userVhallId = data.getUser_id();
//            else if (data.getEmail() == null || data.getEmail().equals("null") || data.getEmail().isEmpty())
//                param.userVhallId = "eduline@eduline.com";
//            else param.userVhallId = data.getEmail();
//        if (Build.VERSION.SDK_INT > 23)
//            if (ActivityCompat.checkSelfPermission(mApplication, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                param.userCustomId = telephonyMgr.getDeviceId();
//
//            } else param.userCustomId = "未获取到权限!";
//        else param.userCustomId = telephonyMgr.getDeviceId();

        param.key = seitionDetails.getBody().getK();
//        param.userCustomId = data.getEmail();
//        param.userCustomId = data.getUser_id();
        //            param.userAvatar = ChuYouApp.getMy().getUserFace();
        param.watchId = seitionDetails.getBody().getNumber();

        if (seitionDetails.getBody().getIs_live() == 1)
            intent.putExtra("type", VhallUtil.WATCH_LIVE);
        else
            intent.putExtra("type", VhallUtil.WATCH_PLAYBACK);
//        String en_params = "";
//        try {
//            en_params = M.getEncryptData(MApplication.getCodedLock(), "eduline-wh" + data.getUser_id() + data.getPwd_str());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        VHlogin(data.getUser_id(), "eduline-wh" + data.getUser_id() + data.getPwd_str());
    }

    public void getVHId() {
        mRootView.showLoading();
        mModel.getVHId()
                .subscribeOn(Schedulers.io())
//                .retryWhen(new RetryWithDelay(1, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    //错误
                    if (mRootView != null)
                        mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<VH>(mErrorHandler) {
                    @Override
                    public void onNext(VH data) {
                        //拿到数据
                        VHStart(data.getData());
                    }
                });
    }*/


    //以下CC直播
    private DWLive dwLive = DWLive.getInstance();
    private DWLiveReplay dwLiveReplay = DWLiveReplay.getInstance();


    /*   进入CC直播*/

    View view;
    boolean isSuccessed = false;

    public void setView(View view) {
        this.view = view;
    }

    public void startCC(SeitionDetails seitionDetails) {
        boolean isLive = seitionDetails.getBody().getIs_live() == 1 ? true : false;
        String livePlayback = seitionDetails.getBody().getLivePlayback();
        String userId = seitionDetails.getBody().getUserid();
        String roomId = seitionDetails.getBody().getRoomid();
        String password = seitionDetails.getBody().getPwd();
        String viewerName = seitionDetails.getBody().getAccount();
        if (isLive) {//直播
            mLoadingPopup.show(view);
            if (password == null || "".equals(password)) {
                dwLive.setDWLiveLoginParams(dwLiveLoginListener, userId, roomId, viewerName);
            } else {
                dwLive.setDWLiveLoginParams(dwLiveLoginListener, userId, roomId, viewerName, password);
            }
            dwLive.startLogin();
        } else {//回放
            mLoadingPopup2.show(view);
//            Log.i("info", "userId=" + userId + "roomId=" + roomId + "livePlayback=" + livePlayback + "viewerName=" + viewerName);
            if (livePlayback != null && !livePlayback.isEmpty() && !livePlayback.equals("null")) {
                if (password == null || "".equals(password)) {
                    dwLiveReplay.setLoginParams(dwLiveReplayLoginListener, false, userId, roomId, livePlayback, viewerName);
                } else {
                    dwLiveReplay.setLoginParams(dwLiveReplayLoginListener, false, userId, roomId, livePlayback, viewerName, password);
                }
                dwLiveReplay.startLogin();
            } else {
                Message msg = new Message();
                msg.what = -2;
                msg.obj = "该课时暂时没有回放";
                if (mLoadingPopup2 != null)
                    mLoadingPopup2.dismiss();
                ccHandler.sendMessage(msg);
            }
        }
    }

    private TxtLoadingPopup mLoadingPopup;
    private TxtLoadingPopup mLoadingPopup2;
    private boolean isLive = true;
    private List<String> liveIds = new ArrayList<String>();
    private String currentLiveId;
    @SuppressLint("HandlerLeak")
    private Handler ccHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Map<String, String> map = (Map) msg.obj;
                    String userId = map.get("userid");
                    if (userId != null) {
//                        etUser.setText(userId);
                    }
                    String roomId = map.get("roomid");
                    if (roomId != null) {
//                        etRoomid.setText(roomId);
                    }
                    break;
                case 1:
                    if (isLive) {
                        return;
                    }

                    liveIds.clear(); //清空liveIds列表
                    List<String> ccList = (ArrayList) msg.obj;
                    for (String liveId : ccList) {
                        liveIds.add(liveId);
                    }
                    currentLiveId = liveIds.get(0);
//                    spinnerAdapter.notifyDataSetChanged();
                    break;
                case -1:
                    String failLiveReason = (String) msg.obj;
                    mRootView.showMessage(failLiveReason);
                    break;
                case -2:
                    String failReplayReason = (String) msg.obj;
                    mRootView.showMessage(failReplayReason);
                    break;
            }
        }
    };

    private void initCCLive() {
        mLoadingPopup2 = new TxtLoadingPopup(mApplication);
        mLoadingPopup2.setKeyBackCancel(true);
        mLoadingPopup2.setOutsideCancel(true);
        mLoadingPopup2.setTipValue("正在打开回放...");
        mLoadingPopup2.setOnPopupDismissListener(new BasePopupWindow.OnPopupDismissListener() {
            @Override
            public void onDismiss() {
                if (isSuccessed) {
                    Intent intent = new Intent(mApplication, ReplayActivity.class);
                    startActivity(intent);
                }
            }
        });

        mLoadingPopup = new TxtLoadingPopup(mApplication);
        mLoadingPopup.setKeyBackCancel(true);
        mLoadingPopup.setOutsideCancel(true);
        mLoadingPopup.setTipValue("正在打开直播...");
        mLoadingPopup.setOnPopupDismissListener(new BasePopupWindow.OnPopupDismissListener() {
            @Override
            public void onDismiss() {
                if (isSuccessed) {
                    Intent intent = new Intent(mApplication, PcLivePlayActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private DWLiveReplayLoginListener dwLiveReplayLoginListener = new DWLiveReplayLoginListener() {
        @Override
        public void onException(final DWLiveException exception) {
            isSuccessed = false;
            toast(mApplication, exception.getLocalizedMessage());
            handler.sendEmptyMessage(2);
        }

        @Override
        public void onLogin(TemplateInfo templateInfo) {
            isSuccessed = true;
            handler.sendEmptyMessage(2);
        }
    };

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (mLoadingPopup != null)
                        mLoadingPopup.dismiss();
                    break;
                case 2:
                    if (mLoadingPopup2 != null)
                        mLoadingPopup2.dismiss();
                    break;
            }
        }
    };

    private DWLiveLoginListener dwLiveLoginListener = new DWLiveLoginListener() {

        @Override
        public void onLogin(TemplateInfo templateInfo, Viewer viewer, RoomInfo roomInfo, PublishInfo publishInfo) {
            isSuccessed = true;
//            info = publishInfo;
            handler.sendEmptyMessage(1);
        }

        @Override
        public void onException(final DWLiveException exception) {
            isSuccessed = false;
            toast(mApplication, exception.getLocalizedMessage());
            handler.sendEmptyMessage(1);
            Message msg = new Message();
            msg.what = -1;
            msg.obj = exception.getMessage();
            ccHandler.sendMessage(msg);
        }
    };



    /*进入展示互动直播，以下展示互动相关*//*
    private String getDomain(String domain) {
        String s2 = "http://";
        String s3 = domain.substring(s2.length(), domain.length());
        return s3.substring(0, s3.indexOf("/"));
    }

    private VodSite vodSite;

    public void startZSHD(SeitionDetails seitionDetails) {
        if (seitionDetails.getLivePlayback() != null) {//展示互动 点播（回放）
            // initParam的构造
            InitParam initParam = new InitParam();
            // domain 域名
            initParam.setDomain(getDomain(seitionDetails.getBody().getDomain()));
            //8个数字的字符串为编号
            if (seitionDetails.getLivePlayback().getNumber().length() == 8) {
                // 点播编号 （不是点播id）
                initParam.setNumber(seitionDetails.getLivePlayback().getNumber());
            } else {
                // 设置点播id，和点播编号对应，两者至少要有一个有效才能保证成功
                String liveId = seitionDetails.getLivePlayback().getNumber();
                initParam.setLiveId(liveId);
            }
            // 站点认证帐号 ，后台启用需要登录时必填，没启用时可以不填
            initParam.setLoginAccount("");
            // 站点认证密码，后台启用需要登录时必填
            initParam.setLoginPwd("");
            // 点播口令，后台启用密码保护时必填且要正确填写
            initParam.setVodPwd(seitionDetails.getLivePlayback().getToken());
            // 昵称 用于统计和显示，必填
            initParam.setNickName("ccc");
            // 服务类型（站点类型）
            // webcast - ST_CASTLINE
            // training - ST_TRAINING
            // meeting - ST_MEETING
            initParam.setServiceType(ServiceType.TRAINING);
            //站点 系统设置 的 第三方集成 中直播模块 “认证“  启用时请确保”第三方K值“（你们的k值）的正确性 ；如果没有启用则忽略这个参数
            initParam.setK("");
            vodSite = new VodSite(mApplication);
            vodSite.setVodListener(this);
            vodSite.getVodObject(initParam);
        } else {
            ZSHDLiveBean bean = new ZSHDLiveBean();
            bean.setAccount(seitionDetails.getBody().getAccount());
            bean.setNumber(seitionDetails.getBody().getNumber());
            bean.setJoinPwd(seitionDetails.getBody().getJoin_pwd());
            bean.setDomain(seitionDetails.getBody().getDomain());
            bean.setPwd(seitionDetails.getBody().getPwd());
            bean.setNickName(seitionDetails.getBody().getAccount());
            Intent intent = new Intent(mApplication, MyZhiBoActivity.class);//展示互动 直播
            intent.putExtra("bean", bean);
            mRootView.launchActivity(intent);
        }
    }

    @Override
    public void onChatHistory(String s, List<ChatMsg> list, int i, boolean b) {

    }

    @Override
    public void onQaHistory(String s, List<QAMsg> list, int i, boolean b) {

    }

    @Override
    public void onVodErr(int i) {

    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case MyZhiBoActivity.RESULT.DOWNLOAD_ERROR:
                    // notifyData();
//                    Toast.makeText(getApplicationContext(), "下载出错", DURTIME).show();
                    break;
                case MyZhiBoActivity.RESULT.DOWNLOAD_STOP:
//                    notifyData();
//                    Toast.makeText(getApplicationContext(), "下载停止", DURTIME).show();

                    break;
                case MyZhiBoActivity.RESULT.DOWNLOAD_START:
//                    notifyData();
//                    Toast.makeText(getApplicationContext(), "下载开始", DURTIME).show();
                    break;
                case MyZhiBoActivity.RESULT.ON_GET_VODOBJ:

//                    optionSelect.setVisibility(View.VISIBLE);
                    final String vodId = (String) msg.obj;

                    System.out.println("vodId===" + vodId);//eR8e82pjMV
                    // download(vodId);
                    // notifyData();
                    // 在线播放


                    Intent i = new Intent(mApplication, PlayActivity.class);
                    i.putExtra("play_param", vodId);
                    startActivity(i);


                    break;
                case MyZhiBoActivity.RESULT.DOWNLOADER_INIT:
//                    if (mVodDownLoader != null) {
//                        mMyAdapter.notifyData(mVodDownLoader.getDownloadList());
//                    }
                    break;
                default:
                    break;
            }
        }

    };

    @Override
    public void onVodObject(String s) {
        mHandler.sendMessage(mHandler
                .obtainMessage(MyZhiBoActivity.RESULT.ON_GET_VODOBJ, s));
    }

    @Override
    public void onVodDetail(VodObject vodObject) {
        if (vodObject != null) {
            vodObject.getDuration();// 时长
            vodObject.getEndTime();// 录制结束时间 始于1970的utc时间毫秒数
            vodObject.getStartTime();// 录制开始时间 始于1970的utc时间毫秒数
            vodObject.getStorage();// 大小 单位为Byte
        }
    }*/
}
