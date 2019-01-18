package com.seition.cloud.pro.newcloud.home.mvp.contract;

import com.jess.arms.base.bean.DataBean;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.seition.cloud.pro.newcloud.app.bean.group.Category;
import com.seition.cloud.pro.newcloud.app.bean.group.Group;
import com.seition.cloud.pro.newcloud.app.bean.group.GroupData;
import com.seition.cloud.pro.newcloud.app.bean.group.GroupMember;
import com.seition.cloud.pro.newcloud.app.bean.group.GroupTheme;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;


public interface GroupContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void setTabsData(ArrayList<Category> categories);
    }

    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface fView extends IView {
        void setGroupCategory(String groupCategory);
        void setSpringViewLoader(boolean hasdata );
    }


    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<Category> getGroupCategory(String cacheName, boolean cache);
    }
    interface fModel extends IModel {
        Observable<GroupData> getGroupListNew(int vPage, int page, int count, String cate_id, boolean cache);
    }



    interface GroupOperationView extends IView {
        void setGroupLogoData(int logoData);

    }

    interface GroupOperationModel extends IModel {
        Observable<DataBean> createGroup(String  name, int group_logo, String cate_id, String type, String intro, String announce);
        Observable<DataBean> editGroup(int group_id,  String  name,  int group_logo, String cate_id,    String intro,  String announce);

        Observable<DataBean> uploadFile(List<MultipartBody.Part> files);
    }


    interface GroupTopicView extends IView {
            void showDetails(Group group);

    }

    interface GroupTopicModel extends IModel {
        Observable<DataBean<Group>> getGroupDetals(String  group_id, boolean cache);
        Observable<DataBean<ArrayList<GroupTheme>>> getTopicList(String  group_id, int page, int count, String dist, String keysord, boolean cache);
        Observable<DataBean> deleteGroup(String  group_id);
        Observable<DataBean> quitGroup(String  group_id);
        Observable<DataBean> joinGroup(String  group_id);
        Observable<DataBean> themeOperate(String tid, int action, String type);
        Observable<DataBean> themeDelete(String tid, String group_id);
        Observable<DataBean> themeReply(String tid, String group_id);

        Observable<DataBean<ArrayList<GroupMember>>> getGroupMemberList( String  group_id,  int page,int count, String type, boolean cache);
        Observable<DataBean<ArrayList<GroupMember>>> getApplyGroupMemberList( String  group_id,  int page,int count, String type, boolean cache);
    }

//    interface GroupTopicModel extends IModel {
//        Observable<DataBean> getGoupTopicList(  String  name,  int group_logo, String cate_id,  String type,  String intro,  String announce);
//
//    }

    interface GroupMemberModel extends IModel {
        Observable<DataBean<Group>> getGroupDetals(String  group_id, boolean cache);
        Observable<DataBean<ArrayList<GroupTheme>>> getTopicList(String  group_id, int page, int count, String dist, String keysord, boolean cache);
        Observable<DataBean> deleteGroup(String  group_id);
        Observable<DataBean> quitGroup(String  group_id);
        Observable<DataBean> joinGroup(String  group_id);
        Observable<DataBean> themeOperate(String tid, int action, String type);
        Observable<DataBean> themeDelete(String tid, String group_id);
        Observable<DataBean> themeReply(String tid, String group_id);

        Observable<DataBean<ArrayList<GroupMember>>> getGroupMemberList( String  group_id,  int page,int count, String type, boolean cache);
    }

    interface GroupMemberView extends IView {


    }
}
