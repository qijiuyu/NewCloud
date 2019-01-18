package com.seition.cloud.pro.newcloud.home.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.seition.cloud.pro.newcloud.app.PayResponse;
import com.seition.cloud.pro.newcloud.app.bean.live.CourseOnlines;
import com.seition.cloud.pro.newcloud.app.bean.member.Member;
import com.seition.cloud.pro.newcloud.app.bean.member.PaySwitch;
import com.seition.cloud.pro.newcloud.app.bean.member.VipUser;
import com.seition.cloud.pro.newcloud.home.mvp.view.MultiView;

import java.util.ArrayList;

import io.reactivex.Observable;


public interface MemberContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends MultiView {

    }

    interface MemberView extends IView {
        void setFragment(ArrayList<Member> members);
    }

    interface MemberCourseView extends IView {
        void addFooter();
    }

    interface MemberRechargeView extends IView {
        void showPayView(ArrayList<String> datas);

        void showPayResult(PayResponse data);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<Member> getVipGrades(boolean iscache);

        Observable<VipUser> getNewMembers(int limit, boolean iscache);

        Observable<CourseOnlines> getVipCourses(int vip_id, boolean iscache);

        Observable<PaySwitch> getPaySwitch();

        Observable<PayResponse> rechargeVip(String pay_for, int user_vip, String vip_type_time, int vip_time);
    }
}
