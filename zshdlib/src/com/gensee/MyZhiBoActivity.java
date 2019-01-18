package com.gensee;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gensee.adapter.UserListAdapter;
import com.gensee.bean.ZSHDLiveBean;
import com.gensee.common.ServiceType;
import com.gensee.entity.ChatMsg;
import com.gensee.entity.InitParam;
import com.gensee.entity.PingEntity;
import com.gensee.entity.QAMsg;
import com.gensee.entity.UserInfo;
import com.gensee.entity.VodObject;
import com.gensee.fragment.ChatFragment;
import com.gensee.fragment.DocFragment;
import com.gensee.fragment.QaFragment;
import com.gensee.fragment.VoteFragment;
import com.gensee.net.AbsRtAction.ErrCode;
import com.gensee.player.OnPlayListener;
import com.gensee.player.Player;
import com.gensee.rtmpresourcelib.R;
import com.gensee.service.LogCatService;
import com.gensee.taskret.OnTaskRet;
import com.gensee.utils.GenseeLog;
import com.gensee.view.GSVideoView;
import com.gensee.vod.VodSite;

import java.util.ArrayList;
import java.util.List;

public class MyZhiBoActivity extends FragmentActivity implements OnPlayListener, View.OnClickListener, OnItemClickListener, VodSite.OnVodListener {
    private RelativeLayout rl_title;
    //    private TextView title;
    private TextView title_back;
    private LinearLayout ll_chat;
    private LinearLayout ll_vote;
    private LinearLayout ll_hands_up;
    private LinearLayout ll_qa;
    private LinearLayout ll_document;
    private DocFragment mDocFragment;
    private VoteFragment mVoteFragment;
    private QaFragment mQaFragment;
    private Player mPlayer;
    private Intent serviceIntent;
    private ChatFragment mChatFragment;
    private static String TAG = "MyZhiBoActivity";
    private FragmentManager mFragmentManager;
    private GSVideoView mGSViedoView;
    //    private Spinner spinnerRate;
    private TextView txtHand;
    private RelativeLayout relTip;
    private TextView txtTip;
    private SharedPreferences preferences;
    private UserListAdapter mChatAdapter;
    private ImageView txtVideo;
    private ImageView txtAudio;
    private ProgressBar mProgressBar;
    private String domain/* = "funmitech.gensee.com"*/;
    private String account/* = "13608178363@qq.com"*/;
    private String pwd/* = "666666"*/;

    private String number/* = "66506007"*/;
    private String nickName /*= "android_test"*/;
    private String joinPwd/* = "495423"*/;

    private LinearLayout ll_bottom;
    private RelativeLayout fl_video;
    private boolean isOne = false;
    private Runnable fullScreenRun;
    private boolean isFullScreen = false;
    private boolean isGoneHeaderTitle = false;
    private Runnable goneHeaderTitleRun;
    private int height;
    private int width;
    ZSHDLiveBean zbrb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_myzhibo);
        mFragmentManager = getSupportFragmentManager();
        preferences = getPreferences(MODE_PRIVATE);
        zbrb = (ZSHDLiveBean) getIntent().getSerializableExtra("bean");
        number = zbrb.getNumber();
        joinPwd = zbrb.getJoinPwd();
        domain = getDomain(zbrb.getDomain());
        account = zbrb.getAccount();
        pwd = zbrb.getPwd();
        nickName = zbrb.getNickName();
        startLogService();
        initView();
        initListener();
        initRateSelectView();
        initModule();
        initInitParam();
    }

    private String getDomain(String domain) {
        int index = 0;
        String s2 = "http://";
        String s4 = "https://";
        if (domain.contains(s2))
            index = s2.length();
        else if (domain.contains(s4))
            index = s4.length();
        String s3 = domain.substring(index, domain.length());
        return s3.substring(0, s3.indexOf("/"));
    }

    private void initListener() {
        // TODO Auto-generated method stub
        title_back.setOnClickListener(this);
        ll_chat.setOnClickListener(this);
        ll_vote.setOnClickListener(this);
        ll_hands_up.setOnClickListener(this);
        ll_qa.setOnClickListener(this);
        ll_document.setOnClickListener(this);
        txtVideo.setOnClickListener(this);
        txtAudio.setOnClickListener(this);
        mGSViedoView.setOnClickListener(this);
    }

    private void initView() {
        // TODO Auto-generated method stub
        mPlayer = new Player();
        mProgressBar = (ProgressBar) findViewById(R.id.progress);
        txtVideo = (ImageView) findViewById(R.id.txtVideo);
        txtAudio = (ImageView) findViewById(R.id.txtAudio);
        txtHand = (TextView) findViewById(R.id.txtHand);
        relTip = (RelativeLayout) findViewById(R.id.rl_tip);
        txtTip = (TextView) findViewById(R.id.tv_tip);
        fl_video = (RelativeLayout) findViewById(R.id.fl_video);
        mGSViedoView = (GSVideoView) findViewById(R.id.imvideoview);
        rl_title = (RelativeLayout) findViewById(R.id.rl_title);
//        title = (TextView) findViewById(R.id.title);
        title_back = (TextView) findViewById(R.id.title_back);
        ll_chat = (LinearLayout) findViewById(R.id.ll_chat);
        ll_vote = (LinearLayout) findViewById(R.id.ll_vote);
        ll_bottom = (LinearLayout) findViewById(R.id.ll_bottom);
        ll_hands_up = (LinearLayout) findViewById(R.id.ll_hands_up);
        ll_qa = (LinearLayout) findViewById(R.id.ll_qa);
        ll_document = (LinearLayout) findViewById(R.id.ll_document);
        mPlayer.setGSVideoView(mGSViedoView);
    }

    private void videoClick() {
        if (!isOne) {
            isOne = true;
            fullScreenRun = new Runnable() {
                private int time = 2;

                @Override
                public void run() {
                    time--;
                    if (time >= 0)
                        mGSViedoView.postDelayed(this, 1000);
                    else
                        isOne = false;
                }
            };
            mGSViedoView.post(fullScreenRun);
            if (rl_title.getVisibility() == View.VISIBLE) {
                isGoneHeaderTitle = true;
                rl_title.setVisibility(View.GONE);
            } else {
                isGoneHeaderTitle = false;
                rl_title.setVisibility(View.VISIBLE);
                goneHeaderTitle();
            }
        } else {
            isOne = false;
            fullScreenRun = null;
            LayoutParams params = fl_video.getLayoutParams();
            if (isFullScreen) {
                isFullScreen = false;
                // 解除全屏
                params.height = height;
                params.width = width;
                ll_bottom.setVisibility(View.VISIBLE);
            } else {
                isFullScreen = true;
                // 全屏
                height = fl_video.getHeight();
                width = fl_video.getWidth();
                ll_bottom.setVisibility(View.GONE);
                params.height = LayoutParams.MATCH_PARENT;
                params.width = LayoutParams.MATCH_PARENT;
            }
            fl_video.setLayoutParams(params);
            onResume();
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        if (isFullScreen)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        else
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        super.onResume();
    }

    private void goneHeaderTitle() {
        goneHeaderTitleRun = new Runnable() {
            private int time = 5;

            @Override
            public void run() {
                time--;
                if (time >= 0) {
                    if (!isGoneHeaderTitle) {
                        rl_title.postDelayed(this, 1000);
                    }
                } else {
                    isGoneHeaderTitle = true;
                    rl_title.setVisibility(View.GONE);
                }
            }
        };
        rl_title.post(goneHeaderTitleRun);
    }

    private boolean isNumber(String number) {
        try {
            Long.parseLong(number);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //    InitParam initParam;
    public void initInitParam() {
        if ("".equals(domain) || "".equals(number) || "".equals(nickName)) {
            toastMsg("域名/编号/昵称 都不能为空");
            return;
        }
        if (number == null || joinPwd == null) {
            finish();
            toastMsg("直播数据错误，退出直播。");
            return;
        }
        InitParam initParam = new InitParam();
        // 设置域名
        initParam.setDomain(domain);

        if (number.length() == 8 && isNumber(number)) {// 此判断是为了测试方便，请勿模仿，实际使用时直接使用其中一种设置
            // 设置编号,8位数字字符串，
            initParam.setNumber(number);
        } else {
            // 如果只有直播间id（混合字符串
            // 如：8d5261f20870499782fb74be021a7e49）可以使用setLiveId("")代替setNumber()
            String liveId = number;
            initParam.setLiveId(liveId);
        }
        // 设置站点登录帐号（根据配置可选）
        initParam.setLoginAccount(account);
        // 设置站点登录密码（根据配置可选）
        initParam.setLoginPwd(pwd);
        // 设置显示昵称 不能为空,请传入正确的昵称，有显示和统计的作用
        initParam.setNickName(nickName);
        // 设置加入口令（根据配置可选）
        initParam.setJoinPwd(joinPwd);
        // 设置服务类型，如果站点是webcast类型则设置为ServiceType.ST_CASTLINE，
        // training类型则设置为ServiceType.ST_TRAINING
        initParam.setServiceType(serviceType);
        // 如果启用第三方认证，必填项，且要正确有效
        // initParam.setK("");
        showTip(true, "正在玩命加入...");

        initPlayer(initParam);
    }

    private void startLogService() {
        serviceIntent = new Intent(this, LogCatService.class);
        startService(serviceIntent);
    }

    private void stopLogService() {
        if (null != serviceIntent) {
            stopService(serviceIntent);
        }
    }

    private void processQaFragment(FragmentTransaction ft) {
        if (mQaFragment == null) {
            mQaFragment = new QaFragment(mPlayer);
            ft.add(R.id.fragement_update, mQaFragment);
        } else {
            ft.show(mQaFragment);
        }
    }

    private void processVoteFragment(FragmentTransaction ft) {
        if (mVoteFragment == null) {
            mVoteFragment = new VoteFragment(mPlayer);
            ft.add(R.id.fragement_update, mVoteFragment);
        } else {
            ft.show(mVoteFragment);
        }
    }

    private void processDocFragment(FragmentTransaction ft) {
        if (mDocFragment == null) {
            mDocFragment = new DocFragment(mPlayer);
            ft.add(R.id.fragement_update, mDocFragment);
        } else {
            ft.show(mDocFragment);
        }

//        if (null != mViedoFragment) {
//            setVideoViewVisible(false);
//        }
    }

    private void processChatFragment(FragmentTransaction ft) {
        if (mChatFragment == null) {
            mChatFragment = new ChatFragment(mPlayer);
            ft.add(R.id.fragement_update, mChatFragment);
        } else {
            ft.show(mChatFragment);
        }
    }

    public void hideFragment(FragmentTransaction ft) {

//        if (mViedoFragment != null) {
//            ft.hide(mViedoFragment);
//        }
        if (mVoteFragment != null) {
            ft.hide(mVoteFragment);
        }
        if (mChatFragment != null) {
            ft.hide(mChatFragment);
        }
        if (mQaFragment != null) {
            ft.hide(mQaFragment);
        }
        if (mDocFragment != null) {
            ft.hide(mDocFragment);
        }
    }

    private ServiceType serviceType = ServiceType.ST_TRAINING;
    private boolean bJoinSuccess = false;

    private AlertDialog dialog;
    private int inviteMediaType;

    @Override
    public void onChatHistory(String s, List<ChatMsg> list, int i, boolean b) {

    }

    @Override
    public void onQaHistory(String s, List<QAMsg> list, int i, boolean b) {

    }

    private String getErrMsg(int errCode) {
        String msg = "";
        switch (errCode) {
            case ERR_DOMAIN:
                msg = "domain 不正确";
                break;
            case ERR_TIME_OUT:
                msg = "超时";
                break;
            case ERR_SITE_UNUSED:
                msg = "站点不可用";
                break;
            case ERR_UN_NET:
                msg = "无网络请检查网络连接";
                break;
            case ERR_DATA_TIMEOUT:
                msg = "数据过期";
                break;
            case ERR_SERVICE:
                msg = "请检查填写的serviceType";
                break;
            case ERR_PARAM:
                msg = "请检查参数";
                break;
            case ERR_UN_INVOKE_GETOBJECT:
                msg = "请先调用getVodObject";
                break;
            case ERR_VOD_INTI_FAIL:
                msg = "调用getVodObject失败";
                break;
            case ERR_VOD_NUM_UNEXIST:
                msg = "点播编号不存在或点播不存在";
                break;
            case ERR_VOD_PWD_ERR:
                msg = "点播密码错误";
                break;
            case ERR_VOD_ACC_PWD_ERR:
                msg = "登录帐号或登录密码错误";
                break;
            case ERR_UNSURPORT_MOBILE:
                msg = "不支持移动设备";
                break;

            default:
                break;
        }
        return msg;
    }

    public interface RESULT {
        int DOWNLOAD_ERROR = 2;
        int DOWNLOAD_STOP = 3;
        int DOWNLOADER_INIT = 4;
        int DOWNLOAD_START = 5;
        int ON_GET_VODOBJ = 100;
    }

    @Override
    public void onVodErr(final int i) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                String msg = getErrMsg(i);
                if (!"".equals(msg)) {
                    Toast.makeText(MyZhiBoActivity.this, msg, Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });

    }

    @Override
    public void onVodObject(String vodId) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("domain", domain);
        editor.putString("number", number);
        editor.putString("account", account);
        editor.putString("accPwd", pwd);
        editor.putString("nickname", nickName);
        editor.putString("vodPwd", joinPwd);
        // 记住本次使用的参数 免得下次输入
        editor.commit();

        mHandler.sendMessage(mHandler
                .obtainMessage(RESULT.ON_GET_VODOBJ, vodId));

//		vodSite.getChatHistory(vodId, 1);//获取聊天历史记录，起始页码1
//		vodSite.getQaHistory(vodId, 1);//获取问答历史记录，起始页码1
    }

    @Override
    public void onVodDetail(VodObject vodObj) {
        if (vodObj != null) {
            vodObj.getDuration();// 时长
            vodObj.getEndTime();// 录制结束时间 始于1970的utc时间毫秒数
            vodObj.getStartTime();// 录制开始时间 始于1970的utc时间毫秒数
            vodObj.getStorage();// 大小 单位为Byte
        }
    }

    interface HANDlER {
        int USERINCREASE = 1;
        int USERDECREASE = 2;
        int USERUPDATE = 3;
        int SUCCESSJOIN = 4;
        int SUCCESSLEAVE = 5;
        int CACHING = 6;
        int CACHING_END = 7;
        int RECONNECTING = 8;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {

                case HANDlER.USERINCREASE:
                    mChatAdapter.addInfo((UserInfo) (msg.obj));
                    break;
                case HANDlER.USERDECREASE:
                    mChatAdapter.leaveInfo((UserInfo) (msg.obj));
                    break;
                case HANDlER.USERUPDATE:
                    mChatAdapter.addInfo((UserInfo) (msg.obj));
                    break;
                case HANDlER.SUCCESSJOIN:
                    mProgressBar.setVisibility(View.GONE);
                    bJoinSuccess = true;
                    onJoin(bJoinSuccess);
                    break;
                case HANDlER.SUCCESSLEAVE:
                    dialog();
                    break;
                case HANDlER.CACHING:
                    showTip(true, "正在缓冲...");
                    relTip.setVisibility(View.VISIBLE);
                    break;
                case HANDlER.CACHING_END:
                    showTip(false, "");
                    break;
                case HANDlER.RECONNECTING:
                    showTip(true, "正在重连...");
                    break;
                case RESULT.ON_GET_VODOBJ:

                    final String vodId = (String) msg.obj;
                    System.out.println("vodId===" + vodId);//eR8e82pjMV
                    // download(vodId);
                    // notifyData();
                    // 在线播放
                    Intent i = new Intent(MyZhiBoActivity.this,
                            PlayActivity.class);
                    i.putExtra("play_param", vodId);
                    startActivity(i);


                    break;

                default:
                    break;
            }
            super.handleMessage(msg);
        }

    };
    private Runnable handRun;

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void initPlayer(InitParam p) {
        mPlayer.join(getApplicationContext(), p, this);
    }

    private void initModule() {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        processChatFragment(ft);
        processQaFragment(ft);
        processVoteFragment(ft);
        processDocFragment(ft);
        hideFragment(ft);
        ft.commit();
        onClick(ll_chat);
    }

    private void showTip(final boolean isShow, final String tip) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (isShow) {
                    if (relTip.getVisibility() != View.VISIBLE) {
                        relTip.setVisibility(View.VISIBLE);
                    }
                    txtTip.setText(tip);
                } else {
                    relTip.setVisibility(View.GONE);
                }

            }
        });
    }


    @Override
    public void onJoin(int result) {
        System.out.println("回调 onJoin  :" + result);
        String msg = null;
        switch (result) {
            case JOIN_OK:
                goneHeaderTitle();
                msg = "加入成功";
                mHandler.sendEmptyMessage(HANDlER.SUCCESSJOIN);
                showTip(false, "");
                return;
//                mHandler.postDelayed(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        releasePlayer();
//                    }
//                }, 2000);
//                break;
            case JOIN_CONNECTING:
                msg = "正在加入";
                break;
            case JOIN_CONNECT_FAILED:
                msg = "连接失败";
                break;
            case JOIN_RTMP_FAILED:
                msg = "连接服务器失败";
                break;
            case JOIN_TOO_EARLY:
                msg = "直播还未开始";
                break;
            case JOIN_LICENSE:
                msg = "人数已满";
                break;
            default:
                msg = "加入返回错误" + result;
                break;
        }
        showTip(false, "");
        toastMsg(msg);
    }

    @Override
    public void onUserJoin(UserInfo info) {
        // 用户加入
        mHandler.sendMessage(mHandler.obtainMessage(HANDlER.USERINCREASE, info));
    }

    @Override
    public void onUserLeave(UserInfo info) {
        // 用户离开
        mHandler.sendMessage(mHandler.obtainMessage(HANDlER.USERDECREASE, info));
    }

    @Override
    public void onUserUpdate(UserInfo info) {
        // 用户更新
        mHandler.sendMessage(mHandler.obtainMessage(HANDlER.USERUPDATE, info));
    }

    @Override
    public void onReconnecting() {
        GenseeLog.d(TAG, "onReconnecting");
        // 断线重连
        mHandler.sendEmptyMessage(HANDlER.RECONNECTING);
    }

    @Override
    public void onLeave(int reason) {
        // 当前用户退出
        // bJoinSuccess = false;
        String msg = null;
        switch (reason) {
            case LEAVE_NORMAL:
                msg = "您已经退出直播间";
                break;
            case LEAVE_KICKOUT:
                msg = "您已被踢出直播间";
                mHandler.sendEmptyMessage(HANDlER.SUCCESSLEAVE);
                break;
            case LEAVE_TIMEOUT:
                msg = "连接超时，您已经退出直播间";
                break;
            case LEAVE_CLOSE:
                msg = "直播已经停止";
                break;
            case LEAVE_UNKNOWN:
                msg = "您已退出直播间，请检查网络、直播间等状态";
                break;
            default:
                break;
        }
        if (null != msg) {
            showErrorMsg(msg);
        }
        if (mPlayer != null) {
            mPlayer.release(getApplicationContext());
        }
        toastMsg(msg);
    }

    /**
     * 缓冲变更
     *
     * @param isCaching true 缓冲/false 缓冲完成
     */
    @Override
    public void onCaching(boolean isCaching) {
        GenseeLog.d(TAG, "onCaching isCaching = " + isCaching);
        mHandler.sendEmptyMessage(isCaching ? HANDlER.CACHING : HANDlER.CACHING_END);
//        toastMsg(isCaching ? "正在缓冲" : "缓冲完成");
    }

    /**
     * 文档切换
     *
     * @param docType 文档类型（ppt、word、txt、png）
     * @param docName 文档名称
     */
    @Override
    public void onDocSwitch(int docType, String docName) {
    }

    /**
     * 视频开始
     */
    @Override
    public void onVideoBegin() {
        GenseeLog.d(TAG, "onVideoBegin");
//        toastMsg("视频开始");
    }

    /**
     * 视频结束
     */
    @Override
    public void onVideoEnd() {
        GenseeLog.d(TAG, "onVideoEnd");
//        toastMsg("视频已停止");
    }

    /**
     * 音频电频值
     */
    @Override
    public void onAudioLevel(int level) {

    }


    /**
     * 错误响应
     *
     * @param errCode 错误码 请参考文档
     */
    @Override
    public void onErr(int errCode) {
        System.out.println("回调 onErr  :" + errCode);
        String msg = null;
        switch (errCode) {
            case ErrCode.ERR_DOMAIN:
                msg = "域名domain不正确";
                break;
            case ErrCode.ERR_TIME_OUT:
                msg = "请求超时，稍后重试";
                break;
            case ErrCode.ERR_SITE_UNUSED:
                msg = "站点不可用，请联系客服或相关人员";
                break;
            case ErrCode.ERR_UN_NET:
                msg = "网络不可用，请检查网络连接正常后再试";
                break;
            case ErrCode.ERR_SERVICE:
                msg = "service  错误，请确认是webcast还是training";
                break;
            case ErrCode.ERR_PARAM:
                msg = "initparam参数不全";
                break;
            case ErrCode.ERR_THIRD_CERTIFICATION_AUTHORITY:
                msg = "第三方认证失败";
                break;
            case ErrCode.ERR_NUMBER_UNEXIST:
                msg = "编号不存在";
                break;
            case ErrCode.ERR_TOKEN:
                msg = "口令错误";
                break;
            case ErrCode.ERR_LOGIN:
                msg = "站点登录帐号或登录密码错误";
                break;
            case ErrCode.ERR_WEBCAST_UNSTART:
                msg = "直播过期";
                InitParam initParam = new InitParam();


                // domain 域名
                initParam.setDomain(domain);
                //8个数字的字符串为编号
                if (number.length() == 8) {
                    // 点播编号 （不是点播id）
                    initParam.setNumber(number);
                } else {
                    // 设置点播id，和点播编号对应，两者至少要有一个有效才能保证成功
                    String liveId = number;
                    initParam.setLiveId(liveId);
                }
                // 站点认证帐号 ，后台启用需要登录时必填，没启用时可以不填
//                initParam.setLoginAccount(account);
                // 站点认证密码，后台启用需要登录时必填
//                initParam.setLoginPwd(pwd);
                // 点播口令，后台启用密码保护时必填且要正确填写
                initParam.setVodPwd(joinPwd);
                // 昵称 用于统计和显示，必填
                initParam.setNickName(nickName);
                // 服务类型（站点类型）
                // webcast - ST_CASTLINE
                // training - ST_TRAINING
                // meeting - ST_MEETING
                initParam.setServiceType(serviceType);
                //站点 系统设置 的 第三方集成 中直播模块 “认证“  启用时请确保”第三方K值“（你们的k值）的正确性 ；如果没有启用则忽略这个参数
//                initParam.setK(k);
                vodSite = new VodSite(this);
                vodSite.setVodListener(this);
                vodSite.getVodObject(initParam);
                break;
            default:
                msg = "错误：errCode = " + errCode;
                break;
        }
        showTip(false, "");
        if (msg != null) {
            toastMsg(msg);
        }
    }

    private VodSite vodSite;

    public void exit() {
        Intent mHomeIntent = new Intent(Intent.ACTION_MAIN);
        mHomeIntent.addCategory(Intent.CATEGORY_HOME);
        mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        startActivity(mHomeIntent);
    }

    protected void dialog() {

        Builder builder = new Builder(this);
        builder.setMessage("你已经被踢出");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                finish();
//                onFinshAll();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    public void dialogLeave() {
        Builder builder = new Builder(this);
        builder.setMessage("确定离开");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

//                release();
                dialog.dismiss();
                finish();
            }
        });
        builder.setNegativeButton("取消", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

//                mPlayer.leave();
//                mPlayer.release();
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        back();
    }

    private void back() {
        if (bJoinSuccess) {
            dialogLeave();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        stopLogService();
        if (null != mChatAdapter) {
            mChatAdapter.clear();
        }
        releasePlayer();
//        onFinshAll();
        super.onDestroy();
    }

    private void releasePlayer() {
        if (null != mPlayer && bJoinSuccess) {
            mPlayer.leave();
            mPlayer.release(this);
            bJoinSuccess = false;
        }
//        initInitParam();

    }

    private void showErrorMsg(final String sMsg) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Builder builder = new Builder(MyZhiBoActivity.this);
                builder.setTitle("提示");
                builder.setMessage(sMsg);
                builder.setPositiveButton("确认", new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        finish();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
            }
        });

    }

    private void toastMsg(final String msg) {
        if (msg != null) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            videoFullScreen();
//        } else {
//            videoNormalScreen();
//        }
//    }

//    private void videoFullScreen() {
//        lyTop1.setVisibility(View.GONE);
//        lyTop2.setVisibility(View.GONE);
//    }
//
//    private void videoNormalScreen() {
//        lyTop1.setVisibility(View.VISIBLE);
//        lyTop2.setVisibility(View.VISIBLE);
//    }

    @Override
    public void onPublish(boolean isPlaying) {
//		toastMsg(isPlaying ? "直播（上课）中" : "直播暂停（下课）");
    }

    @Override
    public void onPageSize(int pos, int w, int h) {
        // 文档开始显示
//        toastMsg("文档分辨率 w = " + w + " h = " + h);
    }

    /**
     * 直播主题
     */
    @Override
    public void onSubject(String subject) {
        GenseeLog.d(TAG, "onSubject subject = " + subject);

    }

    /**
     * 在线人数
     *
     * @param total
     */
    public void onRosterTotal(int total) {
        GenseeLog.d(TAG, "onRosterTotal total = " + total);
    }

    /**
     * 系统广播消息
     */
    @Override
    public void onPublicMsg(long userId, String msg) {
        GenseeLog.d(TAG, "onPublicMsg userId = " + userId + " msg = " + msg);
    }

    // int INVITE_AUIDO = 1;//only audio
    // int INVITE_VIDEO = 2;//only video
    // int INVITE_MUTI = 3;//both audio and video
    @Override
    public void onInvite(final int type, final boolean isOpen) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                postInvite(type, isOpen);
            }
        });
    }

    private void postInvite(int type, boolean isOpen) {
        if (isOpen) {
            inviteMediaType = type;
            String media = "音频";
            if (type == INVITE_AUIDO) {
                media = "音频";
            } else if (type == INVITE_VIDEO) {
                media = "视频";
            } else {
                media = "音视频";
            }
            if (dialog == null) {
                dialog = new Builder(this).setPositiveButton("接受", new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        accept(true);
                    }
                }).setNegativeButton("拒绝", new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        accept(false);
                    }
                }).create();
            }
            dialog.setMessage("老师邀请你打开" + media);
            dialog.show();
        } else {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
            accept(false);
        }
    }

    private void accept(boolean isAccept) {
        mPlayer.openMic(this, isAccept, null);
    }

    @Override
    public void onMicNotify(int notify) {
        GenseeLog.d(TAG, "onMicNotify notify = " + notify);
        switch (notify) {
            case MicNotify.MIC_COLSED:
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

//                        onMicColesed();
                    }
                });
                mPlayer.inviteAck(inviteMediaType, false, null);
                break;
            case MicNotify.MIC_OPENED:
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
//                        onMicOpened(inviteMediaType);
                    }
                });
                mPlayer.inviteAck(inviteMediaType, true, null);
                break;
            case MicNotify.MIC_OPEN_FAILED:
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        toastMsg("麦克风打开失败，请重试并允许程序打开麦克风");
                    }
                });
                mPlayer.openMic(this, false, null);
                mPlayer.inviteAck(inviteMediaType, false, null);
                break;

            default:
                break;
        }
    }

    @Override
    public void onScreenStatus(boolean b) {

    }

    @Override
    public void onModuleFocus(int i) {

    }

    @Override
    public void onIdcList(List<PingEntity> list) {

    }

    @Override
    public void onLiveText(String language, String text) {
        toastMsg("文字直播\n语言：" + language + "\n内容：" + text);
    }

    @Override
    public void onLottery(int cmd, String info) {
        // cmd 1:start, 2: stop, 3: abort
        toastMsg("抽奖\n指令：" + (cmd == 1 ? "开始" : (cmd == 2 ? "结束" : "取消")) + "\n结果：" + info);
    }

    @Override
    public void onRollcall(final int timeOut) {
        mHandler.post(new Runnable() {
            private AlertDialog dialog = null;
            private int itimeOut;

            private void rollcallAck(final boolean isAccept) {
                mHandler.removeCallbacks(this);
                mPlayer.rollCallAck(isAccept, new OnTaskRet() {

                    @Override
                    public void onTaskRet(boolean arg0, int arg1, String arg2) {
                        toastMsg(arg0 ? (isAccept ? "本次签到成功" : "您本次未签到") : "操作失败");
                    }
                });
            }

            @Override
            public void run() {
                if (dialog == null) {
                    this.itimeOut = timeOut;
                    dialog = new Builder(MyZhiBoActivity.this).setMessage("")
                            .setPositiveButton("签到", new OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    rollcallAck(true);
                                }
                            }).setCancelable(false).create();
                    dialog.show();
                }
                dialog.setMessage("点名倒计时剩余秒数：" + itimeOut);
                itimeOut--;
                if (itimeOut < 0) {
                    dialog.dismiss();
                    rollcallAck(false);
                } else {
                    mHandler.postDelayed(this, 1000);
                }
            }
        });

    }


    @Override
    public void onFileShare(int cmd, String fileName, String fileUrl) {
        // cmd:1:add, 2: remove
        // TODO 应用层根据需要进行界面显示后可以调用 player的
    }

    @Override
    public void onFileShareDl(int ret, String fileUrl, String filePath) {

    }

    @Override
    public void onVideoSize(int width, int height, boolean iaAs) {
        // TODO Auto-generated method stub

    }

    public void onJoin(boolean isJoined) {
        if (txtAudio != null) {
            txtAudio.setEnabled(isJoined);
            txtVideo.setEnabled(isJoined);
        }
    }

    public void setVideoViewVisible(boolean bVisible) {
        if (bVisible) {
            mGSViedoView.setVisibility(View.VISIBLE);
        } else {
            mGSViedoView.setVisibility(View.GONE);
        }
    }

    private void handDown() {
        txtHand.setText("举手");
        mPlayer.handUp(false, null);
        txtHand.setSelected(false);
        isHandUp = false;
    }

//    public void onMicColesed() {
//        txtMic.setVisibility(View.GONE);
//    }
//
//    public void onMicOpened(int inviteMediaType) {
//        txtMic.setTag(inviteMediaType);
//        txtMic.setVisibility(View.VISIBLE);
//    }

    private void initRateSelectView() {
        List<String> list = new ArrayList<String>();
        list.add(getString(R.string.video_rate_nor));
        list.add(getString(R.string.video_rate_low));
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_layout, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerRate.setAdapter(adapter);
//        spinnerRate.setOnItemSelectedListener(new
//                                                      Spinner.OnItemSelectedListener() {
//                                                          public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
//                                                                                     long arg3) {
//                                                              if (mPlayer != null) {
//                                                                  switch (arg2) {
//                                                                      case 0:
//                                                                          mPlayer.switchRate(VideoRate.RATE_NORMAL, null);
//                                                                          break;
//                                                                      case 1:
//                                                                          mPlayer.switchRate(VideoRate.RATE_LOW, null);
//                                                                          break;
//                                                                  }
//                                                              }
//                                                          }
//
//                                                          public void onNothingSelected(AdapterView<?> arg0) {
//                                                          }
//                                                      });
    }

    private boolean isHandUp = false;

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int i = v.getId();
        if (i == R.id.title_back) {// 退出
            back();

        } else if (i == R.id.ll_chat) {// 聊天
            FragmentTransaction ftChat = mFragmentManager.beginTransaction();
            hideFragment(ftChat);
            processChatFragment(ftChat);
            ftChat.commit();

        } else if (i == R.id.ll_vote) {// 投票
            FragmentTransaction ftvote = mFragmentManager.beginTransaction();
            hideFragment(ftvote);
            processVoteFragment(ftvote);
            ftvote.commit();

        } else if (i == R.id.ll_hands_up) {// 举手
            if (isHandUp)
                return;
            isHandUp = true;
            if (handRun != null) {
                txtHand.removeCallbacks(handRun);
            }
            if (!v.isSelected()) {
                mPlayer.handUp(true, null);
                handRun = new Runnable() {
                    private int time = 60;

                    @Override
                    public void run() {
                        txtHand.setText("举手(" + time + ")");
                        time--;
                        if (time < 0) {
                            handDown();
                        } else {
                            txtHand.postDelayed(this, 1000);
                        }
                    }
                };
                txtHand.post(handRun);
            } else {
                handDown();
            }

        } else if (i == R.id.ll_qa) {// 问答
            FragmentTransaction ftqa = mFragmentManager.beginTransaction();
            hideFragment(ftqa);
            processQaFragment(ftqa);
            ftqa.commit();

        } else if (i == R.id.ll_document) {// 文档
            FragmentTransaction ftdoc = mFragmentManager.beginTransaction();
            hideFragment(ftdoc);
            processDocFragment(ftdoc);
            ftdoc.commit();

        } else if (i == R.id.imvideoview) {
            int orientation = this.getRequestedOrientation();
            if (orientation ==
                    ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
                    || orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                orientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;
            } else {
                orientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;
            }
            this.setRequestedOrientation(orientation);
            videoClick();

//            case R.id.txtMic://说话，语音
//                if (mPlayer != null) {
//                    mPlayer.openMic(this, false, null);
//                    mPlayer.inviteAck((Integer) v.getTag(), false, null);
//                }
//                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // TODO Auto-generated method stub
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        UserInfo chatTarget = mChatAdapter.getmList().get(position);
        hideFragment(ft);
        if (mChatFragment == null) {
            mChatFragment = new ChatFragment(mPlayer);
            ft.add(R.id.fragement_update, mChatFragment);
        } else {
            ft.show(mChatFragment);
        }

        if (chatTarget.getUserId() == UserListAdapter.PUBLIC_CHAT_TAG) {
            // 切换为公共聊天
            mChatFragment.setChatPerson(null);
        } else {
            // 切换为与chatTarget聊天
            mChatFragment.setChatPerson(chatTarget);
        }
        ft.commit();
    }
}
