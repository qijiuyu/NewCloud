package com.seition.cloud.pro.newcloud.home.mvp.ui.more.exam.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.liaoinstan.springview.widget.SpringView;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.examination.CollectExam;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerExamComponent;
import com.seition.cloud.pro.newcloud.home.di.module.ExamModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.ExamContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.ExamCollectPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.ExamCollectListRecyclerAdapter;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class ExamCollectFragment extends BaseFragment<ExamCollectPresenter> implements ExamContract.ExamCollectView {

    @BindView(R.id.springview)
    SpringView springView;

    @BindView(R.id.recycle_view)
    SwipeMenuRecyclerView recyclerView;

    ExamCollectListRecyclerAdapter adapter;


    public static ExamCollectFragment newInstance() {
        ExamCollectFragment fragment = new ExamCollectFragment();
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
        return inflater.inflate(R.layout.fragment_exam_collect, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }
  /*  private void initSwipRecylerView(){

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));// 布局管理器。
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));// 布局管理器。
        recyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        recyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加也行。
//        recyclerView.addItemDecoration(new DefaultItemDecoration(ContextCompat.getColor(getActivity(), R.color.e5)));
//        mSwipeMenuRecyclerView.setSwipeItemClickListener(mItemClickListener);
        // 为SwipeRecyclerView的Item创建菜单就两句话，不错就是这么简单：
        // 设置菜单创建器。
        recyclerView.setSwipeMenuCreator(swipeMenuCreator);
        // 设置菜单Item点击监听。
        recyclerView.setSwipeMenuItemClickListener(menuItemClickListener);
        // 自定义的核心就是DefineLoadMoreView类。
//        DefineLoadMoreView loadMoreView = new DefineLoadMoreView(getActivity());
//        recyclerView.addFooterView(loadMoreView); // 添加为Footer。
//        recyclerView.setLoadMoreView(loadMoreView); // 设置LoadMoreView更新监听。
//        recyclerView.setLoadMoreListener(mLoadMoreListener);
        recyclerView.smoothOpenRightMenu(0);

//        adapter = new CollectExamRecyclerAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        springView.setType(SpringView.Type.FOLLOW);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                loadData(page,true);
            }

            @Override
            public void onLoadmore() {
                page++;
                loadData(page,false);
            }
        });
        springView.setHeader(new DefaultHeader(getActivity()));   //参数为：logo图片资源，是否显示文字
        springView.setFooter(new DefaultFooter(getActivity()));
        loadData(page,true);
    }*/


    /**
     * 菜单创建器。在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int size = getResources().getDimensionPixelSize(R.dimen.dp_104);
            // 添加右侧的，如果不添加，则右侧不会出现菜单。
            SwipeMenuItem deleteItem = new SwipeMenuItem(_mActivity)
                    .setBackgroundColor(getActivity().getResources().getColor(R.color.color_F37C7C))
//                        .setBackgroundDrawable(R.color.exam_delde_ba_color)
//                        .setImage(R.mipmap.ic_action_delete)
                    .setText("取消收藏") // 文字，还可以设置文字颜色，大小等。。
                    .setTextColor(Color.WHITE)
                    .setWidth(size)
                    .setHeight(size);
            swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。
        }
    };

    /**
     * 菜单点击监听。
     */
    private SwipeMenuItemClickListener menuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();
            int position = menuBridge.getPosition(); // 侧边栏按钮下标
            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
            CollectExam exam = (CollectExam) adapter.getItem(adapterPosition);
            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
//                showYesOrNoDialog(exam.getSource_id(), "你确定要取消收藏该题目吗？", null, null);
//                removePosition = adapterPosition;
            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
            }
        }

    };


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


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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

}
