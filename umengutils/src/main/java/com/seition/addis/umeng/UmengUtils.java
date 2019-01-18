package com.seition.addis.umeng;

import android.app.Activity;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * Created by addis on 2018/6/1.
 */
public class UmengUtils {

    /**
     * 打开分享面板选择分享的方向
     *
     * @param mActivity
     */
    public void share(Activity mActivity) {
        new ShareAction(mActivity)
                .withText("hello")
                .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN)
                .setCallback(shareListener)
                .open();
    }

    /**
     * 不带面板的直接分享方式
     *
     * @param mActivity
     */
    public void share2(Activity mActivity) {
        new ShareAction(mActivity)
                .setPlatform(SHARE_MEDIA.QQ)//传入平台
                .withText("hello")//分享内容
                .setCallback(shareListener)//回调监听器
                .share();
    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {

        }
    };
}
