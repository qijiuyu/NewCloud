package com.seition.cloud.pro.newcloud.app.config;

import android.content.Context;

/**
 * Created by addis on 2016/12/7.
 */

public class Share {
    //    资讯详情页 /index.php?app=classroom&mod=Topic&act=view&id=xx
    private static String zixun = "app=classroom&mod=Topic&act=view&id=";
    //    话题详情页/index.php?app=group&mod=Topic&act=topic&gid=xx&tid=xx
//    private static String huati = "app=group&mod=Topic&act=topic&gid=";
    private static String huati = "app=group&mod=Index&act=detail";
    //    机构首页/index.php?app=school&mod=School&act=index&id=机构id&doadmin=机构独立域名
    private static String school = "app=school&mod=School&act=index&id=";
    //    课程详情页index.php?app=classroom&mod=Video&act=view&id=xx
    private static String course = "app=classroom&mod=Video&act=view&id=";
    //    讲师详情页index.php?app=classroom&mod=Teacher&act=view&id=xx
    private static String teacher = "app=classroom&mod=Teacher&act=view&id=";
    //    直播详情页/index.php?app=live&mod=Index&act=view&id=xx
    private static String zhibo = "app=live&mod=Index&act=view&id=";
    //    线下课详情页/index.php?app=live&mod=Index&act=view&id=xx
    private static String offline = "app=classroom&mod=LineClass&act=view&id=";

    public static String getZiXunDetails(String id) {
        if (id == null) id = "";
//        return MyConfig.DOMAIN_NAME_DAFENGCHE + zixun + id;
        return "";
    }

    public static String getHuaTiDetails(String gid, String tid) {
        if (gid == null) gid = "";
        if (tid == null) tid = "";
//        return MyConfig.DOMAIN_NAME_DAFENGCHE + huati + gid + "&tid=" + tid;
        return "";
    }
    public static String getTopicDetails(Context context,String gid, String tid) {
//        if (gid == null) gid = "";
        if (tid == null) tid = "";
//        return MyConfig.DOMAIN_NAME_DAFENGCHE + huati + "&token="+ PreferenceUtil.getInstance(context).getString("oauth_token", "")
//                +"&secret="+PreferenceUtil.getInstance(context).getString("oauth_token_secret", "")
//                + "&gid="+gid + "&id=" + tid;
        return "";
    }

    public static String getSchoolHome(String id, String doadmin) {
        if (id == null) id = "";
        if (doadmin == null) doadmin = "";
        return Service.DOMAIN_NAME_DAFENGCHE_SHARE + school + id + "&doadmin=" + doadmin;

    }

    public static String getOfflineHome(String id) {
        if (id == null) id = "";
        return Service.DOMAIN_NAME_DAFENGCHE_SHARE + offline + id ;
    }

    public static String getCourseDetails(String id) {
        if (id == null) id = "";
//        return Service.DOMAIN_NAME_DAFENGCHE_SHARE + course + id;
        return "";
    }

    public static String getTeacherDetails(String id) {
        if (id == null) id = "";
//        return Service.DOMAIN_NAME_DAFENGCHE_SHARE + teacher + id;
        return "";
    }

    public static String getZhiBoDetails(String id) {
        if (id == null) id = "";
//        return Service.DOMAIN_NAME_DAFENGCHE_SHARE + zhibo + id;
        return "";
    }

}
