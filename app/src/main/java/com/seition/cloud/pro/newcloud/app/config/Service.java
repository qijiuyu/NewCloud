package com.seition.cloud.pro.newcloud.app.config;

/**
 * Created by addis on 2018/3/23.
 */

public class Service {
    private static final String Http = "http://";
    private static final String Https = "https://";
//    public static final String DOMAIN = Https + "t.v4.51eduline.com";//多机构测试
    public static final String DOMAIN = Http + "video.longmishu.com/";//多机构正式
//    public static final String DOMAIN = Https + "single.51eduline.com";//单机构测试
//    public static final String DOMAIN = Https + "demo.51eduline.com";//单机构正式
    public static final String DOMAIN_NAME = DOMAIN + "/service/";
    public static final String DOMAIN_NAME_DAFENGCHE = "index.php?app=api&oauth_token=6124dfa9bc269ac8ed7183f7a8ed40b7&oauth_token_secret=591839ca35839ca00e449a512968b703";

    public static final String DOMAIN_NAME_DAFENGCHE_SHARE = DOMAIN_NAME + "/index.php?";

}
