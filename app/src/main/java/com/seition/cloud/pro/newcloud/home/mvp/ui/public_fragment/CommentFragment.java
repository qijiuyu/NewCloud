package com.seition.cloud.pro.newcloud.home.mvp.ui.public_fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.comment.CommentBean;
import com.seition.cloud.pro.newcloud.app.utils.AdapterViewUtils;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerCommentComponent;
import com.seition.cloud.pro.newcloud.home.di.module.CommentModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.CommentContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.CommentPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.login.activity.LoginActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.CourseCommentAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.seition.cloud.pro.newcloud.home.mvp.ui.public_fragment.CommentFragment.Comment.Teacher;


public class CommentFragment extends BaseFragment<CommentPresenter> implements CommentContract.View {
    public enum Comment {
        Video, Live, Teacher, Offline
    }


    private boolean isBuy = false;
    private Comment type;//1.直播2.点播3.讲师4.线下课
    private String courseId;
    @BindView(R.id.recycle_view)
    RecyclerView recycle_view;
    @BindView(R.id.springview)
    SpringView springView;

    @Inject
    CourseCommentAdapter adapter;

    public static CommentFragment newInstance(String id, Comment type) {
        CommentFragment fragment = new CommentFragment();
        fragment.setCourseId(id);
        fragment.setType(type);
        return fragment;
    }

    public boolean isBuy() {
        return isBuy;
    }

    public void setBuy(boolean buy) {
        isBuy = buy;
    }

    public Comment getType() {
        return type;
    }

    public void setType(Comment type) {
        this.type = type;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    //1.直播2.点播3.讲师4.线下课
    private void comment(String content, int score) {
        switch (type) {
            case Video:
            case Live:
                mPresenter.commentCourse(courseId, 1, content, score);
                break;
            case Offline:
                mPresenter.commentCourse(courseId, 3, content, score);
                break;
            case Teacher:
                mPresenter.commentTeacher(courseId, 4, content, score);
                break;
        }
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerCommentComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .commentModule(new CommentModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comment, container, false);
    }

    ImageView comment_star;
    LinearLayout comment_ll;
    TextView commit_this_course;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initData(Bundle savedInstanceState) {
        recycle_view.setLayoutManager(new LinearLayoutManager(getContext()));// 布局管理器
        View headerView = _mActivity.getLayoutInflater().inflate(R.layout.fragment_comment_header, recycle_view, false);
        mPresenter.getInitReviewConfig(headerView);
        commit_this_course = (TextView) headerView.findViewById(R.id.commit_this_course);
        comment_star = (ImageView) headerView.findViewById(R.id.commentStar);
        comment_ll = (LinearLayout) headerView.findViewById(R.id.comment_ll);
        if (type == Teacher) {
            setBuy(true);
            commit_this_course.setText("点评该讲师");
        } else {
            setBuy(false);
            commit_this_course.setText("点评该课程");
        }
        comment_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment();
            }
        });

        recycle_view.setAdapter(adapter);
        recycle_view.setItemAnimator(new DefaultItemAnimator());


        springView.setType(SpringView.Type.FOLLOW);

        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                springView.setEnableFooter(false);
                mPresenter.getCommentData(true, type, courseId);
            }

            @Override
            public void onLoadmore() {
                mPresenter.getCommentData(false, type, courseId);
            }
        });
        springView.setHeader(new DefaultHeader(_mActivity));   //参数为：logo图片资源，是否显示文字
        springView.setFooter(new DefaultFooter(_mActivity));
        springView.setEnableFooter(false);


        showCommitStart(commentStar);
        adapter.addHeaderView(headerView);
        mPresenter.getCommentData(true, type, courseId);
    }

    void comment() {
        if (TextUtils.isEmpty(PreferenceUtil.getInstance(_mActivity).getString("oauth_token", null))) {
            launchActivity(new Intent(_mActivity, LoginActivity.class));
        } else {
            if (isBuy)
                showCustomView();
            else showMessage("请购买后点评本课程！");
        }
    }


    EditText contentInput;
    TextView commentCommit, dialog_title;
    private RatingBar ratiing;
    MaterialDialog dialog;

    public void showCustomView() {

        dialog = new MaterialDialog.Builder(_mActivity)
                .customView(R.layout.dialog_comment, true)
                .positiveText("")
                .negativeText("")
                .build();
        contentInput = (EditText) dialog.getCustomView().findViewById(R.id.content);
        commentCommit = (TextView) dialog.getCustomView().findViewById(R.id.commentClick);
        dialog_title = (TextView) dialog.getCustomView().findViewById(R.id.dialog_title);
        ratiing = (RatingBar) dialog.getCustomView().findViewById(R.id.grade);
        if (type == Teacher)
            dialog_title.setText("点评该讲师");
        else
            dialog_title.setText("点评该课程");
        commentCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int score = (int) ratiing.getRating();
                String comments = contentInput.getText().toString();
                if (score == 0) {
                    if (type == Teacher)
                        Toast.makeText(_mActivity, "请给讲师评分", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(_mActivity, "请给课程评分", Toast.LENGTH_SHORT).show();
//                    showMessage("请给课程评分");
                    return;
                }
                if (TextUtils.isEmpty(comments)) {
                    Toast.makeText(_mActivity, "请输入评论内容", Toast.LENGTH_SHORT).show();
//                    showMessage("请输入评论内容");
                    return;
                }
                comment(comments, score);
            }
        });
        dialog.show();
    }

    /**
     * 此方法是让外部调用使fragment做一些操作的,比如说外部的activity想让fragment对象执行一些方法,
     * 建议在有多个需要让外界调用的方法时,统一传Message,通过what字段,来区分不同的方法,在setData
     * 方法中就可以switch做不同的操作,这样就可以用统一的入口方法做不同的事
     * <p>
     * 使用此方法时请注意调用时fragment的生命周期,如果调用此setData方法时onCreate还没执行
     * setData里却调用了presenter的方法时,是会报空的,因为dagger注入是在onCreated方法中执行的,然后才创建的presenter
     * 如果要做一些初始化操作,可以不必让外部调setData,在initData中初始化就可以了
     *
     * @param data
     */

    @Override
    public void setData(Object data) {

    }

    int commentStar;

    public void setComment_star(int commentStar) {
        this.commentStar = commentStar;
    }

    private void showCommitStart(int commentStar) {
        switch (commentStar) {
            case 0:
                comment_star.setImageResource(R.drawable.ic_star0);
                break;
            case 1:
                comment_star.setImageResource(R.drawable.ic_star1);
                break;
            case 2:
                comment_star.setImageResource(R.drawable.ic_star2);
                break;
            case 3:
                comment_star.setImageResource(R.drawable.ic_star3);
                break;
            case 4:
                comment_star.setImageResource(R.drawable.ic_star4);
                break;
            case 5:
                comment_star.setImageResource(R.drawable.ic_star5);
                break;
        }
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        springView.onFinishFreshAndLoad();
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }

    boolean needAddEmpty = true;

    @Override
    public void showComment(boolean pull, ArrayList<CommentBean> datas) {

//        if (datas.size() > 0) {
//            adapter.setNewData(datas);
//                if (adapter.getFooterViewsCount() == 0)
//                    adapter.addFooterView(AdapterViewUtils.getNoDataViwe(_mActivity));
//        }
//        else
//            adapter.setEmptyView(LayoutInflater.from(_mActivity).inflate(R.layout.adapter_empty_view,  recycle_view,false));//getEmptyViwe
//            adapter.setEmptyView(AdapterViewUtils.getNoDataViwe(_mActivity));//getEmptyViwe

        if (pull) {
            adapter.setNewData(datas);
            if (datas.size() > 0) {
                if (datas.size() < mPresenter.count) {
                    adapter.loadMoreEnd(true);
                    springView.setEnableFooter(false);
                } else {
                    adapter.loadMoreComplete();
                    springView.setEnableFooter(true);
                }
            } else if (needAddEmpty) {
                adapter.addFooterView(AdapterViewUtils.getEmptyViwe(_mActivity));
                needAddEmpty = false;
            }
        } else {
            adapter.addData(datas);
            if (datas.size() < mPresenter.count) {
                adapter.loadMoreEnd(false);
                springView.setEnableFooter(false);
            } else {
                adapter.loadMoreComplete();
                springView.setEnableFooter(true);
            }
        }

    }

    @Override
    public void hideDialog() {
        if (dialog != null || dialog.isShowing())
            dialog.dismiss();
    }
}
