package com.seition.addis.umeng.login;

import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * Created by Administrator on 2017/2/15 0015.
 */

public interface AuthCallback<T extends BaseInfo> {
    void onComplete(SHARE_MEDIA share_media, String info,boolean hasUnionid);
//    void onComplete(int var2, T info);

    void onError(int var2, Throwable var3);

    void onCancel(int var2);
}
