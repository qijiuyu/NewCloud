package com.seition.addis.umeng;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.Toast;

import com.seition.addis.umeng.login.AuthCallback;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.media.UMusic;
import com.umeng.socialize.shareboard.ShareBoardConfig;

import java.util.Map;

/**
 * Created by Administrator on 2017/2/14 0014.
 */

public class UmengUtil {
    private static Context context;
    private static UMShareAPI umShareAPI;
    private static SHARE_MEDIA[] shareMedias;//SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN,SHARE_MEDIA.QZONE,SHARE_MEDIA.WEIXIN_CIRCLE


    public static void init(Context context1, String sinaCallbackUrl, boolean degbug, SHARE_MEDIA... shareMediaList) {
        context = context1;
        // UMShareAPI.get(context1);

//        Config.REDIRECT_URL = sinaCallbackUrl;//http://sns.whalecloud.com/sina2/callback
        shareMedias = shareMediaList;
        Config.isJumptoAppStore = true;

//        Config.DEBUG = degbug;
        umShareAPI = UMShareAPI.get(context1);
        //对应平台没有安装的时候跳转转到应用商店下载,其中qq 微信会跳转到下载界面进行下载，其他应用会跳到应用商店进行下载
        //友盟统计
//        MobclickAgent.setScenarioType(context, MobclickAgent.EScenarioType. E_UM_NORMAL);

//        UMConfigure.init( );
    }

    public static void setKeySecretWeixin(String key, String secret) {
        PlatformConfig.setWeixin(key, secret);
    }

    public static void setKeySecretQQ(String key, String secret) {
        PlatformConfig.setQQZone(key, secret);
    }

    public static void setKeySecretSina(String key, String secret) {
        PlatformConfig.setSinaWeibo(key, secret, "http://sns.whalecloud.com");
    }


    public static void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        UMShareAPI.get(activity).onActivityResult(requestCode, resultCode, data);
    }

    public static void onDestroy(Activity activity) {
        UMShareAPI.get(activity).release();
    }

    public static void shareTxt(Activity activity, final String uid,
                                String title, String desc, String thumbUrl,
                                String targetUrl, final ShareCallback callback) {
        share(0, activity, uid, title, desc, "", thumbUrl, targetUrl, callback);
    }

    /**
     * 自带分享的统计,其中uid是app本身的账户系统的id
     *
     * @param type
     * @param activity
     * @param uid
     * @param title
     * @param desc
     * @param thumbUrl
     * @param mediaUrl
     * @param targetUrl
     * @param callback
     */
    private static void share(int type, Activity activity, final String uid,
                              String title, String desc, String thumbUrl, String mediaUrl,
                              String targetUrl, final ShareCallback callback) {

        ShareAction action = new ShareAction(activity);

        if (type == 0) {
            action.withMedia(new UMImage(activity, mediaUrl));
        } else if (type == 1) {
            UMusic music = new UMusic(mediaUrl);
            music.setTitle(title);//音乐的标题
            music.setThumb(new UMImage(activity, thumbUrl));//音乐的缩略图
            music.setDescription(desc);//音乐的描述
            action.withMedia(music);
        } else if (type == 2) {
            UMVideo music = new UMVideo(mediaUrl);
            music.setTitle(title);//音乐的标题
            music.setThumb(new UMImage(activity, thumbUrl));//音乐的缩略图
            music.setDescription(desc);//音乐的描述
            action.withMedia(music);
        }

    }

    public static void shareUrl(Activity mCotext, String webUrl, String title, String info, String imgUrl) {
        context = mCotext;
        ShareBoardConfig config = new ShareBoardConfig();
        config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_BOTTOM);//分享面板在中间出现
        config.setMenuItemBackgroundColor(Color.WHITE);
        config.setIndicatorVisibility(false);
        config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR);// 圆角背景
        config.setShareboardBackgroundColor(Color.WHITE);//分享面板背景颜色
        config.setTitleText("分享给朋友");
        config.setTitleTextColor(Color.BLACK);
        config.setCancelButtonText("取消");//取消按钮
        config.setCancelButtonTextColor(mCotext.getResources().getColor(R.color.color_2069cf));
        config.setIndicatorVisibility(false);
        new ShareAction(mCotext).setDisplayList(/*SHARE_MEDIA.SINA,*/ SHARE_MEDIA.QQ,/* SHARE_MEDIA.QZONE,*/ SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE/*, SHARE_MEDIA.WEIXIN_FAVORITE, SHARE_MEDIA.MORE*/)
                .withMedia(new UMWeb(webUrl, title, info, new UMImage(mCotext, imgUrl)))
                .setCallback(umShareListener)
                .open(config);
    }

    private static UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            com.umeng.socialize.utils.SLog.E("plat:platform" + platform);
            if (context != null)
                if (platform.name().equals("WEIXIN_FAVORITE")) {
                    Toast.makeText(context, platform + " 收藏成功啦", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
                }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            if (context != null)
                Toast.makeText(context, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                com.umeng.socialize.utils.SLog.E("throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            if (context != null)
                Toast.makeText(context, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };


    public static void shareMusic(Activity activity, final String uid,
                                  String title, String desc, String thumbUrl, String musicUrl,
                                  String targetUrl, final ShareCallback callback) {
        share(1, activity, uid, title, desc, thumbUrl, musicUrl, targetUrl, callback);
    }

    public static void shareVideo(Activity activity, final String uid,
                                  String title, String desc, String thumbUrl, String musicUrl,
                                  String targetUrl, final ShareCallback callback) {
        share(2, activity, uid, title, desc, thumbUrl, musicUrl, targetUrl, callback);
    }

    public static void loginBySina(Activity activity, AuthCallback callback) {
        login(activity, SHARE_MEDIA.SINA, callback);
    }

    public static void loginByWeixin(Activity activity, AuthCallback callback) {
        login(activity, SHARE_MEDIA.WEIXIN, callback);
    }

    public static void loginByQQ(Activity activity, AuthCallback callback) {
        login(activity, SHARE_MEDIA.QQ, callback);
    }

    public static void login(Activity activity, final SHARE_MEDIA platform, final AuthCallback callback) {
//        umShareAPI.doOauthVerify(activity,platform,umAuthListener);
//        UMShareConfig config = new UMShareConfig();
//        config.isNeedAuthOnGetUserInfo(true);
//        UMShareAPI.get(activity).setShareConfig(config);
        umShareAPI = UMShareAPI.get(activity);
        umShareAPI.getPlatformInfo(activity, platform,new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                String info = "";
                boolean hasUnionid;
                if (platform == SHARE_MEDIA.WEIXIN) {
                    info = map.get("unionid");
                    hasUnionid = true;
                } else if (platform == SHARE_MEDIA.QQ) {
                    info = map.get("access_token");
                    hasUnionid = false;
                } else {
                    info = map.get("accessToken");
                    hasUnionid = true;
                }
                callback.onComplete(share_media, info, hasUnionid);
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                callback.onError(i, throwable);

            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                callback.onCancel(i);

            }
        });
        boolean isAuthorize = umShareAPI.isAuthorize(activity, platform);
//        umShareAPI.doOauthVerify(activity, platform, new UMAuthListener() {
//            @Override
//            public void onStart(SHARE_MEDIA share_media) {
//
//            }
//
//            @Override
//            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
//                String info = "";
//                boolean hasUnionid;
//                if (platform == SHARE_MEDIA.WEIXIN) {
//                    info = map.get("unionid");
//                    hasUnionid = true;
//                } else if (platform == SHARE_MEDIA.QQ) {
//                    info = map.get("access_token");
//                    hasUnionid = false;
//                } else {
//                    info = map.get("accessToken");
//                    hasUnionid = true;
//                }
//                callback.onComplete(share_media, info, hasUnionid);
//            }
//
//            @Override
//            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
//                callback.onError(i, throwable);
//
//            }
//
//            @Override
//            public void onCancel(SHARE_MEDIA share_media, int i) {
//                callback.onCancel(i);
//
//            }
//        });
    }


    /*private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            SocializeUtils.safeShowDialog(dialog);
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Log.d("user info", "user info:" + data.toString());
            SocializeUtils.safeCloseDialog(dialog);
//            Log.d("user info", "user info:" + data.toString());
            Message msg = new Message();

            if(platform == SHARE_MEDIA.WEIXIN)


            msg.what = platform.equals(SHARE_MEDIA.QQ) ? ActivityHandler.AUTH_QQ
                    : platform.equals(SHARE_MEDIA.WEIXIN) ? ActivityHandler.AUTH_WEIXIN
                    : platform.equals(SHARE_MEDIA.SINA) ? ActivityHandler.AUTH_SINA : 0;
            if (msg.what == ActivityHandler.AUTH_WEIXIN) {
                Log.i("info", data.toString());
                msg.obj = data.get("unionid");
                handler.sendMessage(msg);
            } else {
                msg.obj = data.get("access_token");
                getUnionid(msg);
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(context, "授权失败！" + t.getMessage(), Toast.LENGTH_SHORT).show();
            Log.i("info", "msg = " + t.getMessage());
            SocializeUtils.safeCloseDialog(dialog);
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(context, "退出授权！", Toast.LENGTH_SHORT).show();
            SocializeUtils.safeCloseDialog(dialog);
        }
    };*/


  /*  //以下的是统计的api

    public static void analysisOnResume(Activity activity){
        MobclickAgent.onResume(activity);
    }
    public static void analysisOnPause(Activity activity){
        MobclickAgent.onPause(activity);
    }
    public static void onKillProcess(){
        MobclickAgent.onKillProcess(context);
    }

    public static void analysisOnPageStart(String pageName){
        MobclickAgent.onPageStart(pageName);
    }
    public static void analysisOnPageEnd(String pageName){
        MobclickAgent.onPageEnd(pageName);
    }

    public static void onProfileSignIn(String ID) {
        MobclickAgent.onProfileSignIn(ID);
    }
    public static void onProfileSignIn(String provider, String ID) {
        MobclickAgent.onProfileSignIn(provider,ID);
    }
    public static void onProfileSignOff(){
        MobclickAgent.onProfileSignOff();
    }
*/


}
