package com.seition.cloud.pro.newcloud.home.mvp.contract;

import com.jess.arms.base.bean.DataBean;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.seition.cloud.pro.newcloud.app.bean.comment.CommentBean;
import com.seition.cloud.pro.newcloud.app.bean.comment.Comments;
import com.seition.cloud.pro.newcloud.app.bean.config.CommentConfig;

import java.util.ArrayList;

import io.reactivex.Observable;


public interface CommentContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void showComment(boolean pull, ArrayList<CommentBean> data);

        void hideDialog();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<Comments> getComment(int page, int count, String courseId, int type, boolean isCache);

        Observable<Comments> getTeacherComment(int page, int count, String teacherId, boolean isCache);

        Observable<DataBean> commentTeacher(String teacherId, int kztype, String content, int score);

        Observable<DataBean> commentCourse(String kzid, int kztype, String content, int score);

        Observable<CommentConfig> getInitReviewConfig();
    }
}
