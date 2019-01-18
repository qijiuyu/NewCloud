package com.seition.cloud.pro.newcloud.home.mvp.contract;

import com.jess.arms.base.bean.DataBean;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.seition.cloud.pro.newcloud.app.PayResponse;
import com.seition.cloud.pro.newcloud.app.bean.money.BalanceDetails;
import com.seition.cloud.pro.newcloud.app.bean.money.CreditDetails;
import com.seition.cloud.pro.newcloud.app.bean.money.Data_BalanceDetails;
import com.seition.cloud.pro.newcloud.app.bean.money.Data_CreditDetails;
import com.seition.cloud.pro.newcloud.app.bean.money.Data_SpiltDetails;
import com.seition.cloud.pro.newcloud.app.bean.money.MoneyDetailResponse;
import com.seition.cloud.pro.newcloud.app.bean.money.SpiltDetails;
import com.seition.cloud.pro.newcloud.home.mvp.view.MultiView;

import io.reactivex.Observable;


public interface MoneyContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void showBalance(BalanceDetails balance);

        void showSpilt(SpiltDetails spilt);

        void showCredit(CreditDetails credit);

        void toAliPay(String orderInfo);
    }

    interface ListView extends MultiView {


    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<Data_BalanceDetails> getBanlanceConfig(boolean iscache);

        Observable<Data_SpiltDetails> getIncomeConfig(boolean iscache);

        Observable<Data_CreditDetails> getCountConfig(boolean iscache);

        Observable<PayResponse> rechargeBanlance(String pay_for, String money);//余额

        Observable<DataBean> recharge(String type, String exchange_score);//积分

        Observable<DataBean> incomeToWithdraw(String exchange_balance, String type, int card_id);//收入

        Observable<MoneyDetailResponse> getBalanceList(int limit, int page, boolean iscache);

        Observable<MoneyDetailResponse> getSpiltList(int limit, int page, boolean iscache);

        Observable<MoneyDetailResponse> getCreditList(int limit, int page, boolean iscache);
    }
}
