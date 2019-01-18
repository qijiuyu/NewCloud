package com.seition.cloud.pro.newcloud.app.popupwindow;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.questionask.QaCategory;
import com.seition.cloud.pro.newcloud.app.utils.Utils;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.QaCategoryDialogListRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xzw on 2018/04/03.
 */

public class QaCategoryListPopWindow extends BasePopWindow implements BaseQuickAdapter.OnItemClickListener{
    private List<QaCategory> datas;
    private Activity activity;

//    SpringView springView;

    RecyclerView recyclerView;

    private QaCategoryDialogListRecyclerAdapter adapter;
    private PopupWindow popupWindow;
    private int animationStyle;

    public QaCategoryListPopWindow(Activity activity, List<QaCategory> datas, int animationStyle,View parent){
        this.activity = activity;
        this.datas = datas;
        this.animationStyle = animationStyle;
//        initPop(parent);
    }
    public void addItemDatas(QaCategory item){
        if (datas  == null)
            datas = new ArrayList<>();
        datas.add(item);
    }

    private void initPop(View parent){
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_qa_cate_list_window,null);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        Rect visibleFrame = new Rect();
        parent.getGlobalVisibleRect(visibleFrame);
        int h5 = visibleFrame.left;

        DisplayMetrics dm = activity.getResources().getDisplayMetrics();
        int heigth = dm.heightPixels;
        int width = dm.widthPixels;

        popupWindow = new PopupWindow(view, width - visibleFrame.left*2, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        // 设置popwindow如果点击外面区域，便关闭。
        popupWindow.setOutsideTouchable(true);
        if (animationStyle != 0) {
            popupWindow.setAnimationStyle(animationStyle);
        }
//        ColorDrawable dw = new ColorDrawable(0xb0000000);
//        popupWindow.setBackgroundDrawable(dw);

//        outView.setOnClickListener(this);
    }
    public void hide(){
        if(popupWindow!=null)
            popupWindow.dismiss();
    }

    private void initDialogData(){
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));// 布局管理器
        adapter = new QaCategoryDialogListRecyclerAdapter();
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.setNewData(datas);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(BaseQuickAdapter badapter, View view, int position) {

        onDialogItemClickListener.onWindowItemClick(adapter.getItem(position));
    }


    public interface OnDialogItemClickListener<T> {
        void onWindowItemClick( T p);
    }
    OnDialogItemClickListener onDialogItemClickListener;
    public void setOnDialogItemClickListener(OnDialogItemClickListener onDialogItemClickListener){
        this.onDialogItemClickListener = onDialogItemClickListener;
    }

    public void setAnimationStyle(int animationStyle){
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

    /**
     * 显示popWindow :相对于控件
     */
    @TargetApi(19)
    public void showPopAsDropDown(View parent, int offX, int offY, int gravity) {
        initPop(parent);
        initDialogData();
        // 设置popwindow显示位置
        Rect visibleFrame = new Rect();
        parent.getGlobalVisibleRect(visibleFrame);
        int hj = Utils.dip2px(activity,visibleFrame.left);
        int hh = parent.getHeight();
//        int height = parent.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
        if (Build.VERSION.SDK_INT >= 24) {

            int height = parent.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
//            popupWindow.setHeight(height);
            popupWindow.showAsDropDown(parent, 0, 0);
        } else {
//            popupWindow.setHeight(height);
//            popupWindow.showAsDropDown(parent, visibleFrame.left/4, 0);
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
