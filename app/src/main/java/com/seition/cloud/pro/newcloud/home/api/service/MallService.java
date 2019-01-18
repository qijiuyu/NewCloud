package com.seition.cloud.pro.newcloud.home.api.service;

import com.jess.arms.base.bean.DataBean;
import com.seition.cloud.pro.newcloud.app.PayResponse;
import com.seition.cloud.pro.newcloud.app.bean.ReceiveGoodsAddresss;
import com.seition.cloud.pro.newcloud.app.bean.mall.MallCategory;
import com.seition.cloud.pro.newcloud.app.bean.mall.MallListData;
import com.seition.cloud.pro.newcloud.app.bean.mall.MallRankData;

import io.reactivex.Observable;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by addis on 2018/5/8.
 */

public interface MallService {
    //商城-获取商城分类
    String MallCategory = "goods.getCategory";
    //	商城-商城首页数据
    String MallHomeData = "goods.getHomeData";
    //	商城-获取商城列表数据
    String MallList = "goods.getList";
    //商城-商城商品兑换
    String MallExchange = "goods.exchange";
    //商城-商城商品兑换,使用微信或支付宝支付
    String UseAliPayOrWxPay = "goods.buyGoods";


    @POST(MallCategory)
    Observable<MallCategory> getMallCategory(@Header("en-params") String enParams, @Header("oauth-token") String oauthToken);

//        @POST( "&mod=Home&act=getAdvert")
//        Observable<DataBean<ArrayList<AdvertBean>>> getMallBanner(@Query("place") String place);

    @POST(MallHomeData)
//"&mod=Goods&act=index"
    Observable<MallRankData> getMallRankDatas(@Header("en-params") String enParams, @Header("oauth-token") String oauthToken);

    @POST(MallList)
//"&mod=Goods&act=index"
    Observable<MallListData> getMallListDatas(@Header("en-params") String enParams, @Header("oauth-token") String oauthToken);

    @POST("&mod=Address&act=getAddressList")
    Observable<ReceiveGoodsAddresss> getReceiveAddress(@Header("oauth-token") String oauthToken);

    @POST(MallExchange)
    Observable<DataBean> exchangeMallGood(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);

    @POST(UseAliPayOrWxPay)
    Observable<PayResponse> useAliPayOrWxPay(@Header("en-params") String en_params, @Header("oauth-token") String oauthToken);
}