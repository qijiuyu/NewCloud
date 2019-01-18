package com.seition.cloud.pro.newcloud.home.mvp.contract;

import com.jess.arms.base.bean.DataBean;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.seition.cloud.pro.newcloud.app.bean.AreaInfo;
import com.seition.cloud.pro.newcloud.app.bean.ReceiveGoodsAddress;
import com.seition.cloud.pro.newcloud.app.bean.ReceiveGoodsAddresss;
import com.seition.cloud.pro.newcloud.app.bean.VersionInfo;
import com.seition.cloud.pro.newcloud.app.bean.note.NoteComment;
import com.seition.cloud.pro.newcloud.app.bean.note.NoteComments;
import com.seition.cloud.pro.newcloud.app.bean.note.Notes;
import com.seition.cloud.pro.newcloud.app.bean.order.Orders;
import com.seition.cloud.pro.newcloud.app.bean.study.StudyRecord;
import com.seition.cloud.pro.newcloud.app.bean.study.StudyRecordSection;
import com.seition.cloud.pro.newcloud.home.mvp.view.MultiView;

import java.util.ArrayList;

import io.reactivex.Observable;


public interface UserContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends MultiView {

//        void showStyduRecord(ArrayList<StudyRecord> studyRecords);
    }

    interface FindPasswordView extends IView {
            void toChangePwd(String  phone,String code);
//        void showStyduRecord(ArrayList<StudyRecord> studyRecords);
    }

    interface SettingView extends IView {
        void showVersionInfo(VersionInfo versionInfo);
    }

    interface StudyView extends MultiView {
        void setData(ArrayList<StudyRecordSection> studyRecordSections);
        void deleteSrudyRecord(boolean successful);

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<StudyRecord> getStudyRecord(boolean iscache);
        Observable<DataBean> deleteStudyRecord(String sid);

        Observable<Orders> getExchangeRecord(String  type,boolean isCache);

        Observable<ReceiveGoodsAddresss> getReceiveAddress(boolean iscache);
        Observable<ReceiveGoodsAddress> addReceiveAddress(String province,String city,String area,String location,String address,String name,String phone,String is_default);
        Observable<ReceiveGoodsAddress> changeReceiveAddress(String province,String city,String area,String location,String address,String name,String phone,String address_id,String is_default);

        Observable<ReceiveGoodsAddress> deleteReceiveAddress(String address_id);
        Observable<ReceiveGoodsAddress> setDefaultReceiveAddress(String address_id);

        Observable<AreaInfo> getArea(int  area_id, boolean cache);
        Observable<Notes> getMyNotes( int page,int count);
        Observable<NoteComments> getMyNoteComments(int pid ,int  ntype,boolean cache);
        Observable<NoteComment> addNoteComments(int pid , String content, int  kzid);

        Observable<VersionInfo> getVersionInfo();

        Observable<DataBean> getPasswordBackVerifyCode(String  phone_number);

        Observable<DataBean> checkVerifyCode(String  phone,String code);

        Observable<DataBean> resetPassword(String  phone,String code,String pwd,String repwd);
        Observable<DataBean> getPasswordByEmail(String  email);

    }
}
