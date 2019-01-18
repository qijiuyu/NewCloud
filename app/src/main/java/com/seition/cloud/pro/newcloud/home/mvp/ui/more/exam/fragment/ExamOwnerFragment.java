package com.seition.cloud.pro.newcloud.home.mvp.ui.more.exam.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jess.arms.base.BaseBackFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.examination.CollectExam;
import com.seition.cloud.pro.newcloud.app.bean.examination.Exam;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerExamComponent;
import com.seition.cloud.pro.newcloud.home.di.module.ExamModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.ExamContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.ExamOwnerPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.exam.activity.ExamResultActivity;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.ExamCollectRecyclerAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.ExamRecyclerAdapter;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class ExamOwnerFragment extends BaseBackFragment<ExamOwnerPresenter> implements ExamContract.ExamOwnerView {
    @BindView(R.id.springview)
    SpringView springView;

    @BindView(R.id.recycle_view)
    SwipeMenuRecyclerView recyclerView;
    @BindView(R.id.empty)
    LinearLayout empty;

    @Inject
    ExamRecyclerAdapter adapter;
    @Inject
    ExamCollectRecyclerAdapter collectAdapter;

    private int type = 0;

    public static ExamOwnerFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        ExamOwnerFragment fragment = new ExamOwnerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerExamComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .examModule(new ExamModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exam_owner, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        type = getArguments().getInt("type");
        initSwipRecylerView();
//        mPresenter.initAdapter(type);
    }

    private void initSwipRecylerView() {

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));// 布局管理器。
//        mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        recyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加也行。
//        recyclerView.addItemDecoration(new DefaultItemDecoration(ContextCompat.getColor(getActivity(), R.color.e5)));
        recyclerView.setSwipeItemClickListener(mItemClickListener);

        // 为SwipeRecyclerView的Item创建菜单就两句话，不错就是这么简单：
        // 设置菜单创建器。
        recyclerView.setSwipeMenuCreator(swipeMenuCreator);
        // 设置菜单Item点击监听。
        recyclerView.setSwipeMenuItemClickListener(menuItemClickListener);

//        mSwipeMenuRecyclerView.useDefaultLoadMore();
        // 自定义的核心就是DefineLoadMoreView类。
//        DefineLoadMoreView loadMoreView = new DefineLoadMoreView(getActivity());
//        mSwipeMenuRecyclerView.addFooterView(loadMoreView); // 添加为Footer。
//        mSwipeMenuRecyclerView.setLoadMoreView(loadMoreView); // 设置LoadMoreView更新监听。
//        mSwipeMenuRecyclerView.setLoadMoreListener(mLoadMoreListener); // 加载更多的监听。
        recyclerView.smoothOpenRightMenu(0);

        if (type != 4)
            recyclerView.setAdapter(adapter);
        else
            recyclerView.setAdapter(collectAdapter);
        adapter.setType(type);
        springView.setType(SpringView.Type.FOLLOW);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                springView.setEnableFooter(false);
                loadData(true);
            }

            @Override
            public void onLoadmore() {
                loadData(false);
            }
        });
        springView.setHeader(new DefaultHeader(getActivity()));   //参数为：logo图片资源，是否显示文字
        springView.setFooter(new DefaultFooter(getActivity()));
        springView.setEnableFooter(false);
        loadData(true);
    }

    private SwipeItemClickListener mItemClickListener = new SwipeItemClickListener() {
        @Override
        public void onItemClick(View itemView, int position) {
            if (type != 4) {
                Exam exam = (Exam) adapter.getItem(position);
                if (exam == null) return;
                if (exam.getProgress() < 100 && type == 1)
                    mPresenter.getExamInfoAndStartExam(exam, 1);
                else if (type != 3 && exam.getStatus() == 1) {
                    boolean isDelect = exam.getIs_del() != 0;
                    launchActivity(new Intent(_mActivity, ExamResultActivity.class)
                            .putExtra("Exam_User_Id", exam.getExams_users_id())
                            .putExtra("Exams_Paper_Id", exam.getExams_paper_id() + "")
                            .putExtra("pid", exam.getPid())
                            .putExtra("isDelect", isDelect)
                            .putExtra("Exams_Type", type));
                }
            }
        }
    };
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int size = 0;
//            if (!isHaveFooter && )
            if (type == 4) {
                size = getResources().getDimensionPixelSize(R.dimen.dp_104);

                SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity())
                        .setBackgroundColor(getActivity().getResources().getColor(R.color.color_F37C7C))
//                        .setBackgroundDrawable(R.color.exam_delde_ba_color)
//                        .setImage(R.mipmap.ic_action_delete)
                        .setText("取消收藏") // 文字，还可以设置文字颜色，大小等。。
                        .setTextColor(Color.WHITE)
                        .setWidth(size)
                        .setHeight(size);
                swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。
            } else {
                size = getResources().getDimensionPixelSize(R.dimen.dp_75);

                // 添加右侧的，如果不添加，则右侧不会出现菜单。
                SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity())
                        .setBackgroundColor(getActivity().getResources().getColor(R.color.color_F37C7C))
//                        .setBackgroundDrawable(R.color.exam_delde_ba_color)
//                        .setImage(R.mipmap.ic_action_delete)
                        .setText("删除") // 文字，还可以设置文字颜色，大小等。。
                        .setTextColor(Color.WHITE)
                        .setWidth(size)
                        .setHeight(size);
                swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。
            }

            if (type == 3) {
                SwipeMenuItem reWrong = new SwipeMenuItem(getActivity())
                        .setBackgroundColor(getActivity().getResources().getColor(R.color.color_65c1f0))
//                        .setBackgroundDrawable(R.color.exam_delde_ba_color)
//                        .setImage(R.mipmap.ic_action_delete)
                        .setText("错题重练") // 文字，还可以设置文字颜色，大小等。。
                        .setTextColor(Color.WHITE)
                        .setWidth(size)
                        .setHeight(size);
                swipeRightMenu.addMenuItem(reWrong);// 添加一个错题重练按钮按钮到右侧侧菜单。
            }
        }
    };

    private SwipeMenuItemClickListener menuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();
            int position = menuBridge.getPosition(); // 侧边栏按钮下标
            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

            String id = "";
            int paper_id = 0;
            if (type == 4)
                id = ((CollectExam) collectAdapter.getItem(adapterPosition)).getSource_id();
            else {
                id = ((Exam) adapter.getItem(adapterPosition)).getExams_users_id();
                paper_id = ((Exam) adapter.getItem(adapterPosition)).getPaper_info().getExams_paper_id();
            }
            swipMenuOnclick(position, id, paper_id, adapterPosition);
        }
    };

    private void swipMenuOnclick(int position, String id, int paper_id, int adapterPosition) {
        String content = "";
        if (type == 4)
            content = "你确定要取消收藏该题目吗？";
        else if (type == 3 && position == 1)
            content = "你确定要重练此考试错题吗？";
        else
            content = "你确定要删除该记录吗？";

        new MaterialDialog.Builder(_mActivity)
                .content(content)
                .positiveText("确定")
                .negativeText("取消")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        if (type == 4)
                            mPresenter.collectExam(id, 0, position);
                        else if (type == 3 && position == 1)
                            mPresenter.examinationWrongExam(id, paper_id);
                        else
                            mPresenter.deleteExamRecord(id, adapterPosition);
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }


    private void loadData(boolean pull) {
        if (type == 4)
            mPresenter.getCollectExam(pull, false);
        else
            mPresenter.getExamOwner(type, pull, false);
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

    MaterialDialog materialDialog;

    @Override
    public void showLoading() {
        materialDialog = new MaterialDialog.Builder(_mActivity)
                .title("正在请求数据")
                .content("......")
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .canceledOnTouchOutside(false)
                .show();
    }

    @Override
    public void hideLoading() {
        if (materialDialog != null)
            materialDialog.dismiss();
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

   /* @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }*/

    @Override
    public void showStateViewState(int state) {
        showMultiViewState(state);
    }

    @Override
    public void showSpingViewFooterEnable(boolean enabled) {
        springView.setEnableFooter(enabled);
    }

    @Override
    public void delete(int type, int position) {
        if (type == 4)
            collectAdapter.remove(position);
        else
            adapter.remove(position);
    }

    @Override
    public void emptyData(boolean isEmpty) {
        recyclerView.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
        empty.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
    }


    /*重写 OnItemSwipeListener */
/*    @Override
    public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {

    }

    @Override
    public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {

    }

    @Override
    public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {

    }

    @Override
    public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {

    }*/


}
