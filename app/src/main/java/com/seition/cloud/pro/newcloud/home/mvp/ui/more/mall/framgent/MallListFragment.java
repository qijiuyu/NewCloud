package com.seition.cloud.pro.newcloud.home.mvp.ui.more.mall.framgent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseBackFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.mall.Mall;
import com.seition.cloud.pro.newcloud.app.bean.mall.MallCategory;
import com.seition.cloud.pro.newcloud.app.utils.Utils;
import com.seition.cloud.pro.newcloud.home.di.component.DaggerMallComponent;
import com.seition.cloud.pro.newcloud.home.di.module.MallModule;
import com.seition.cloud.pro.newcloud.home.mvp.contract.MallContract;
import com.seition.cloud.pro.newcloud.home.mvp.presenter.MallFragmentPresenter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.mall.framgent.adapter.MallCateHorizontalListAdapter;
import com.seition.cloud.pro.newcloud.home.mvp.ui.more.mall.framgent.adapter.MallCateHorizontalListAdapter2;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.MallGridRecyclerAdapter;
import com.seition.cloud.pro.newcloud.widget.ScrollViewCustom;
import com.seition.cloud.pro.newcloud.widget.decoration.SpacesItemDecoration;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MallListFragment extends BaseBackFragment<MallFragmentPresenter> implements MallContract.FragmentView,
        BaseQuickAdapter.OnItemClickListener, AdapterView.OnItemClickListener, View.OnTouchListener, ScrollViewCustom.OnScrollStopListner {
    @BindView(R.id.springview)
    SpringView springView;
    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;
    @BindView(R.id.hor_list_custom2)
    ScrollViewCustom hor_list_custom2;
    @BindView(R.id.hor_list_custom3)
    ScrollViewCustom hor_list_custom3;

    private MallCateHorizontalListAdapter2 secondCateAdapter;

    private MallCateHorizontalListAdapter thirdCateAdapter;
    private ArrayList<MallCategory> secondCateDatas, thirdCateDatas;
    private ArrayList<MallCategory> datas;
    private int position;

    private void initCateTab() {
//        listDatas = new ArrayList<MallGoodsListData>();
        secondCateDatas = new ArrayList<>();
        thirdCateDatas = new ArrayList<>();

        secondCateAdapter = new MallCateHorizontalListAdapter2(_mActivity, secondCateDatas);
        thirdCateAdapter = new MallCateHorizontalListAdapter(_mActivity, thirdCateDatas, 3);
        hor_list_custom2.setNestedScrollingEnabled(true);
        hor_list_custom3.setNestedScrollingEnabled(true);
        hor_list_custom2.setOnItemClickListener(this);
        hor_list_custom3.setOnItemClickListener(this);

        hor_list_custom2.setAdapter(secondCateAdapter);
        hor_list_custom3.setAdapter(thirdCateAdapter);

        if (datas.get(position).getChilds() != null) {
            secondCateDatas = datas.get(position).getChilds();
            thirdCateDatas = secondCateDatas.get(0).getChilds();
//            if (thirdCateDatas != null)
//                System.out.println("PPPPPPPPPPPPPPPPPPPPPPPPP" + position + "," + thirdCateDatas.size());
            secondCateAdapter.setDatas(secondCateDatas, datas.size());
            secondCateAdapter.setSelectedItem(0);
            secondCateAdapter.notifyDataSetChanged();

            thirdCateAdapter.setDatas(thirdCateDatas, datas.size());
            thirdCateAdapter.setSelectedItem(0);
            thirdCateAdapter.notifyDataSetChanged();
        }

        if (secondCateDatas.size() == 0) {
            hor_list_custom2.setVisibility(View.GONE);
            hor_list_custom3.setVisibility(View.GONE);
        } else {
            hor_list_custom2.setVisibility(View.VISIBLE);
            hor_list_custom3.setVisibility(View.VISIBLE);
        }
        if (thirdCateDatas == null || thirdCateDatas.size() == 0) {
            hor_list_custom3.setVisibility(View.GONE);
        } else {
            hor_list_custom3.setVisibility(View.VISIBLE);
        }

        hor_list_custom2.setOnTouchListener(this);
        hor_list_custom2.setOnScrollStopListner(this);
    }

    @Inject
    MallGridRecyclerAdapter adapter;

    //    private int page = 1 , count = 10;
    private String goods_category = "", keyword = "";

    public static MallListFragment newInstance(MallCategory categories, int position, ArrayList<MallCategory> datas) {
        Bundle args = new Bundle();
        args.putSerializable("mallCategory", categories);
        args.putInt("position", position);
        args.putParcelableArrayList("datas", datas);
        MallListFragment fragment = new MallListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void notifyDataSetChanged() {
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerMallComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .mallModule(new MallModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mall_list, container, false);

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        MallCategory organization = (MallCategory) getArguments().getSerializable("mallCategory");
//        datas.add(organization);
        goods_category = organization.getGoods_category_id() + "";
        position = getArguments().getInt("position");
        datas = getArguments().getParcelableArrayList("datas");
//        position = getArguments().getInt("position");
        initView();
        initCateTab();
        loadData(true);
    }

    private void initView() {
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));// 布局管理器
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        //添加ItemDecoration，item之间的间隔
        int leftRight = Utils.dip2px(getActivity(), 5);
        int topBottom = Utils.dip2px(getActivity(), 5);

        recyclerView.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom));
        springView.setType(SpringView.Type.FOLLOW);

        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                keyword = "";
                loadData(true);
            }

            @Override
            public void onLoadmore() {
                loadData(false);
            }
        });
        springView.setHeader(new DefaultHeader(getActivity()));   //参数为：logo图片资源，是否显示文字
        springView.setFooter(new DefaultFooter(getActivity()));
    }

    private void loadData(boolean pull) {
        mPresenter.getMallListData("list", goods_category, keyword, pull, true);
    }


    public void searchData(String word) {
        this.keyword = word;
        loadData(true);
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


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ((MallFragment) getParentFragment()).startDetail((Mall) adapter.getItem(position));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
        switch (adapterView.getId()) {
            case R.id.hor_list_custom2:
                secondCateAdapter.setSelectedItem(i);
                secondCateAdapter.notifyDataSetChanged();
                thirdCateDatas = secondCateDatas.get(i).getChilds();
                thirdCateAdapter.setDatas(thirdCateDatas);
                thirdCateAdapter.notifyDataSetChanged();

                if (thirdCateDatas == null || thirdCateDatas.size() == 0) {
                    hor_list_custom3.setVisibility(View.GONE);
                    goods_category = secondCateDatas.get(i).getGoods_category_id();
//                    LoadType = LOAD_NEW;
//                    isOne = true;
//                    loadListMallData(firsCateId, keyword, page, count);
                    loadData(true);
                } else {
                    hor_list_custom3.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.hor_list_custom3:
                thirdCateAdapter.setSelectedItem(i);
                thirdCateAdapter.notifyDataSetChanged();
                if (goods_category != thirdCateDatas.get(i).getGoods_category_id()) {
                    goods_category = thirdCateDatas.get(i).getGoods_category_id();
//                    isOne = true;
//                    LoadType = LOAD_NEW;
//                    loadListMallData(mallid, keyword, page, count);
                    loadData(true);
                }
                break;
        }
    }


    private String direction = "";
    public float lastX = 0;
    public float lastY = 0;
    float dY = 0.0f;
    float dX = 0.0f;

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (view.getId()) {
            case R.id.hor_list_custom2:
                final int action = event.getAction();

                float x = event.getX();
                float y = event.getY();

                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        direction = "";
                        lastY = y;
                        lastX = x;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        dY = Math.abs(y - lastY);
                        dX = x - lastX;
                        boolean down = y > lastY ? true : false;
                        lastY = y;
                        lastX = x;
//                        System.out.println("dX = "+dX);
                        if (dX > 0) {
                            direction = ScrollViewCustom.SCROLL_DIRECTION_LEFT;
                        } else if (dX < 0) {  //滑到最右
                            direction = ScrollViewCustom.SCROLL_DIRECTION_RIGHT;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
//                        System.out.println("dX = "+dX+"direction ="+direction+ "____"+Math.abs(dX));
                        if (Math.abs(dX) > 4)
                            hor_list_custom2.startScrollerTask(direction);
//                        return false;
                        break;
                }

                break;
        }
        return false;
    }

    @Override
    public void onScrollStoped() {

    }

    //    private ViewPager vp;
    @Override
    public void onScrollToLeftEdge() {


        if (position != 0)
            ((MallFragment) getParentFragment()).setVPosition(position - 1);
//            vp.setCurrentItem(position - 1);
    }

    @Override
    public void onScrollToRightEdge() {
        if (position != datas.size())
            ((MallFragment) getParentFragment()).setVPosition(position + 1);
    }

    @Override
    public void onScrollToMiddle() {

    }
}
