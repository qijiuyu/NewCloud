package com.seition.cloud.pro.newcloud.app.popupwindow;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.utils.AdapterViewUtils;

import java.util.ArrayList;

/**
 * Created by xzw on 2018/04/03.
 */

public class LoadMoreListPopWindow<T> extends BasePopWindow implements BaseQuickAdapter.OnItemClickListener, View.OnClickListener {
    //    private List<String> datas;
    private Activity activity;

    SpringView springView;

    RecyclerView recyclerView;

    private BaseQuickAdapter adapter;
    private PopupWindow popupWindow;
    private int animationStyle;
    private DismissListener dismissDialogListener;
    private int mPosition;

    public static final int Count = 10;

    public LoadMoreListPopWindow(Activity activity, int animationStyle, BaseQuickAdapter baseQuickAdapter) {
        this.activity = activity;
        this.animationStyle = animationStyle;
        adapter = baseQuickAdapter;
        initPop();
    }

    public void setNewDatas(ArrayList<T> datas) {
        if (datas.size() == 0) {
            adapter.setEmptyView(notDataView);
        } else
            adapter.setNewData(datas);
        if (datas.size() < Count)
            adapter.loadMoreEnd(true);
        else
            adapter.loadMoreComplete();

    }


    public void addNewDatas(ArrayList<T> datas) {

        if (datas.size() == 0)
            adapter.loadMoreFail();
        else
            adapter.addData(datas);

        if (datas.size() < Count)
            adapter.loadMoreEnd(false);
        else
            adapter.loadMoreComplete();
    }

    public void setDialogData(ArrayList<T> datas, boolean pull) {
        if (pull) {

            if (datas.size() > 0) {
                adapter.setNewData(datas);
                if (datas.size() < Count) {
                    if (adapter.getFooterViewsCount() == 0)
                        adapter.addFooterView(AdapterViewUtils.getNoDataViwe(activity));
                    springView.setEnableFooter(false);//springView不可上拉
                } else {
                    adapter.removeAllFooterView();
                    springView.setEnableFooter(true);//springView可上拉
                }
            } else
                adapter.setEmptyView(AdapterViewUtils.getEmptyViwesmall(activity));
        } else {
            adapter.addData(datas);
            if (datas.size() < Count) {
                if (adapter.getFooterViewsCount() == 0)
                    adapter.addFooterView(AdapterViewUtils.getNoDataViwe(activity));
                springView.setEnableFooter(false);
            } else {
                springView.setEnableFooter(true);
            }
        }
    }


    private View notDataView;

    private void initPop() {

        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_load_more_list_window, null);
        springView = (SpringView) view.findViewById(R.id.springview);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);

//        notDataView = LayoutInflater.from(activity).inflate(R.layout.adapter_empty_view, (ViewGroup) recyclerView.getParent(), false);
        notDataView = LayoutInflater.from(activity).inflate(R.layout.adapter_empty_view, null);

        notDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
                onDialogLoadMoreListener.onDialogRefresh();
            }
        });

        outView = (View) view.findViewById(R.id.outView);
        outView.setOnClickListener(this);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        // 设置popwindow如果点击外面区域，便关闭。
        popupWindow.setOutsideTouchable(true);
        if (animationStyle != 0) {
            popupWindow.setAnimationStyle(animationStyle);
        }
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowNormal();
                if (dismissDialogListener != null) {
                    dismissDialogListener.onDismiss(mPosition);
                }
            }
        });

    }

    private void initDialogData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));// 布局管理器
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        springView.setType(SpringView.Type.FOLLOW);

        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                adapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
                onDialogLoadMoreListener.onDialogRefresh();
            }

            @Override
            public void onLoadmore() {
                onDialogLoadMoreListener.onDialogLoadMore();
            }
        });
        springView.setHeader(new DefaultHeader(activity));   //参数为：logo图片资源，是否显示文字
        springView.setFooter(new DefaultFooter(activity));

   /*     adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                onDialogLoadMoreListener.onDialogLoadMore();
            }
        }, recyclerView);*/

        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        springView.setEnableFooter(false);
    }

    public interface OnDialogLoadMoreListener<T> {
        void onDialogRefresh();

        void onDialogLoadMore();

        void onDialogItemClick(T p);
    }

    OnDialogLoadMoreListener onDialogLoadMoreListener;

    public void setOnDialogLoadMoreListenerListener(OnDialogLoadMoreListener onDialogLoadMoreListener) {
        this.onDialogLoadMoreListener = onDialogLoadMoreListener;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        onDialogLoadMoreListener.onDialogItemClick(adapter.getItem(position));
        popupWindow.dismiss();
    }


    public void loadMoreFail() {
        adapter.loadMoreFail();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.outView:
                popupWindow.dismiss();
                break;

        }
    }

    public interface DismissListener {
        void onDismiss(int i);
    }

    public void setDismissDialogListener(DismissListener dismissDialogListener) {
        this.dismissDialogListener = dismissDialogListener;
    }

    public void setAnimationStyle(int animationStyle) {
        this.animationStyle = animationStyle;
    }

    /**
     * 设置窗口正常:有些界面不能够这样使用窗口直接透明化的方法，来设置popwindow的方法，但毕竟是少数，在子类中重写该方法返回null
     */
    protected void setWindowNormal() {
        setWindowAlpha(1.0f);
    }

    /**
     * 设置屏幕的透明度
     *
     * @param alpha 需要设置透明度
     */
    private void setWindowAlpha(float alpha) {
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.alpha = alpha;
        params.verticalMargin = 100;
        activity.getWindow().setAttributes(params);
    }

    /**
     * 显示popWindow :整个界面中的位置
     */

    public void showPopAtLocation(View parent, int offX, int offY, int gravity) {
        setWindowAlpha();
        // 设置popwindow显示位置
        popupWindow.showAtLocation(parent, gravity, offX, offY);
        if (Build.VERSION.SDK_INT != 24)
            popupWindow.update();
        //startAnim();
    }

    //实例化一个矩形
    private Rect mRect = new Rect();
    //坐标的位置（x、y）
    private final int[] mLocation = new int[2];

    /**
     * 显示popWindow :相对于控件
     */
    @TargetApi(19)
    public void showPopAsDropDown(View parent, int offX, int offY, int gravity) {
//        setWindowAlpha();

        initDialogData();
        // 设置popwindow显示位置
       /* try {
            popupWindow.showAsDropDown(parent, offX, offY, gravity);
        }catch (Throwable t){
            popupWindow.showAsDropDown(parent, offX, offY);
        }*/

        if (Build.VERSION.SDK_INT >= 24) {
            Rect visibleFrame = new Rect();
            parent.getGlobalVisibleRect(visibleFrame);
            int height = parent.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
            popupWindow.setHeight(height);
            popupWindow.showAsDropDown(parent, 0, parent.getHeight());
        } else {
//            popupWindow.showAsDropDown(parent, 0, parent.getHeight());
            popupWindow.showAsDropDown(parent, 0, 0);
        }
    }


    /**
     * 显示popWindow,在屏幕的最下方
     */
    public void showPopAtDown(View parent) {
        setWindowAlpha();
        // 设置popwindow显示位置
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        if (Build.VERSION.SDK_INT != 24)
            popupWindow.update();
    }

    protected void setWindowAlpha() {
        setWindowAlpha(0.8f);
    }
}
