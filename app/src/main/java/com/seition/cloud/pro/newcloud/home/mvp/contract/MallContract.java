package com.seition.cloud.pro.newcloud.home.mvp.contract;

import com.jess.arms.base.bean.DataBean;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.seition.cloud.pro.newcloud.app.PayResponse;
import com.seition.cloud.pro.newcloud.app.bean.AdvertBean;
import com.seition.cloud.pro.newcloud.app.bean.ReceiveGoodsAddress;
import com.seition.cloud.pro.newcloud.app.bean.ReceiveGoodsAddresss;
import com.seition.cloud.pro.newcloud.app.bean.config.CredPayConfig;
import com.seition.cloud.pro.newcloud.app.bean.login.RegisterTypeInit;
import com.seition.cloud.pro.newcloud.app.bean.mall.Mall;
import com.seition.cloud.pro.newcloud.app.bean.mall.MallCategory;
import com.seition.cloud.pro.newcloud.app.bean.mall.MallListData;
import com.seition.cloud.pro.newcloud.app.bean.mall.MallRankData;
import com.seition.cloud.pro.newcloud.app.bean.money.BalanceDetails;
import com.seition.cloud.pro.newcloud.app.bean.money.CreditDetails;
import com.seition.cloud.pro.newcloud.app.bean.money.Data_BalanceDetails;
import com.seition.cloud.pro.newcloud.app.bean.money.Data_CreditDetails;

import java.util.ArrayList;

import io.reactivex.Observable;


public interface MallContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void setBanner(ArrayList<AdvertBean> advertBeans);

        void setRankList(ArrayList<Mall> mallRanks);

        void setFragmentList(ArrayList<MallCategory> commonCategories);

        void showListData();
    }

    interface FragmentView extends IView {

    }

    interface DetailstView extends IView {
        void setReceiveAddress(ReceiveGoodsAddress address);

        void showDialog(CreditDetails credit);

        void reLoad(int num);

        void showPayResult(PayResponse data);

        void showPrice(CredPayConfig credPayConfig);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<CredPayConfig> getInitCredpayConfig();

        Observable<Data_CreditDetails> getCountConfig();

        Observable<MallCategory> getMallCategory(int goods_category_id, boolean iscache);

        Observable<AdvertBean> getMallBanner(String place, boolean iscache);


        Observable<MallRankData> getMallRankDatas(String type, String keyword, int page, int count, boolean iscache);

        Observable<MallListData> getMallListDatas(String type, String goods_category, String keyword, int page, int count, boolean iscache);

//        Observable<ReceiveGoodsAddresss> getReceiveAddress(boolean iscache);

        Observable<DataBean> exchangeMallGood(int goods_id, int num, String address_id);

        Observable<PayResponse> useAliPayOrWxPay(int goods_id, int num, String address_id, double price, String payStyle);

        Observable<ReceiveGoodsAddresss> getReceiveAddress(boolean iscache);
    }
}
