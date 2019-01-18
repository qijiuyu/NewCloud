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
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.examination.ExamLevel;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.ExamLevelSelectAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by addis on 2018/9/8.
 */
public class ExamLevelPopWindow extends BasePopWindow implements BaseQuickAdapter.OnItemClickListener, View.OnClickListener {

    private ArrayList<ExamLevel> datas = new ArrayList<>();
    private Activity activity;
    RecyclerView recyclerView;
    private ExamLevelSelectAdapter mAdapter;
    private PopupWindow popupWindow;
    private CourseTypeListPopWindow.DismissListener dismissDialogListener;
    private int mPosition;
    private int animationStyle;

    public void addItemDatas(ExamLevel item) {
        if (datas == null)
            datas = new ArrayList<>();
        datas.add(item);
    }

    public ExamLevelPopWindow(Activity activity) {
        this.activity = activity;
        initPop();
    }

    private void initPop() {
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_list_window, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        outView = (View) view.findViewById(R.id.outView);
        outView.setOnClickListener(this);

        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        // 设置popwindow如果点击外面区域，便关闭。
        popupWindow.setOutsideTouchable(true);
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
        mAdapter = new ExamLevelSelectAdapter();
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter.setNewData(datas);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//        mAdapter.setSelectId(datas.get(position).getLevelId());
        onDialogItemClickListener.onDialogItemClick(this, ((ExamLevel) adapter.getItem(position)).getLevelId(), position);
        popupWindow.dismiss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.outView:
                popupWindow.dismiss();
                break;

        }
    }

    public interface OnDialogItemClickListener {
        void onDialogItemClick(BasePopWindow popWindow, int levelId, int position);
    }

    OnDialogItemClickListener onDialogItemClickListener;

    public void setOnDialogItemClickListener(OnDialogItemClickListener onDialogItemClickListener) {
        this.onDialogItemClickListener = onDialogItemClickListener;
    }

    public interface DismissListener {
        void onDismiss(int i);
    }

    public void setDismissDialogListener(CourseTypeListPopWindow.DismissListener dismissDialogListener) {
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
//            popupWindow.showAsDropDown(parent, 0, 0);
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
