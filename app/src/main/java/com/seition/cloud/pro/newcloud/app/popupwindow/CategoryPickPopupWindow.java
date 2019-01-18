package com.seition.cloud.pro.newcloud.app.popupwindow;

import android.app.Activity;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.CommonCategory;
import com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter.CategoryListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xzw on 2018/3/30.
 */

public class CategoryPickPopupWindow extends BasePopWindow implements View.OnClickListener {
    private Activity activity;
    private PopupWindow popupWindow;
    private int animationStyle;
    private DismissListener dismissDialogListener;

    ListView firstCategoryLV;
    ListView secondCategoryLV;
    ListView thirdCategoryLV;


    private int firtListClickPosition;
    private int seconListClickPosition;
    private int thirdListClickPosition;

    private CommonCategory mSelectedProvince;
    private CommonCategory mSelectedCity;
    private CommonCategory mSelectedArea;

    private ArrayList<CommonCategory> firstDatas = new ArrayList<CommonCategory>();
    private ArrayList<CommonCategory> secondDatas = new ArrayList<CommonCategory>();
    private ArrayList<CommonCategory> thirdDatas = new ArrayList<CommonCategory>();

    private Map<String, CommonCategory> provinceMap = new HashMap<String, CommonCategory>();
    private Map<String, ArrayList<CommonCategory>> cityMap = new HashMap<String, ArrayList<CommonCategory>>();
    private Map<String, ArrayList<CommonCategory>> areaMap = new HashMap<String, ArrayList<CommonCategory>>();

    public CategoryPickPopupWindow(Activity activity, ArrayList<CommonCategory> firstData/*, Map<String, ArrayList<CommonCategory>> secondDatas, Map<String, ArrayList<CommonCategory>> areaMap*//*, String selected*/) {
        super();
        this.activity = activity;
        this.firstDatas = firstData;
        if (firstDatas!=null&&firstDatas.size()>0&&firstDatas.get(0) != null&&firstDatas.get(0).getChild() != null)
        this.secondDatas = firstDatas.get(0).getChild();
//        if (secondDatas!=null&&secondDatas.get(0) != null&&secondDatas.get(0).getChild() != null)
        if (secondDatas != null && secondDatas.size() > 0 && secondDatas.get(0).getChild() != null)
            this.thirdDatas = secondDatas.get(0).getChild();
        initPop();
    }
    LinearLayout empty;
    private void initPop() {
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_category_window, null);
        empty = (LinearLayout) view.findViewById(R.id.empty);
        firstCategoryLV = (ListView) view.findViewById(R.id.category_f);
        secondCategoryLV = (ListView) view.findViewById(R.id.category_s);
        thirdCategoryLV = (ListView) view.findViewById(R.id.category_t);
        outView = (View) view.findViewById(R.id.outView);
        outView.setOnClickListener(this);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setFocusable(true);
//        popupWindow.setTouchable(true);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        popupWindow.setBackgroundDrawable(dw);
        // 设置popwindow如果点击外面区域，便关闭。
        popupWindow.setOutsideTouchable(true);
        if (animationStyle != 0) {
            popupWindow.setAnimationStyle(animationStyle);
        }

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowNormal();
                if (dismissDialogListener != null) {

                }
            }
        });

    }

    public void setDialogEmptyViewVisibility(boolean visibility){
        empty.setVisibility(visibility?View.VISIBLE:View.GONE);
    }

    CategoryListAdapter firstListAdapter;
    CategoryListAdapter seconListAdapter;
    CategoryListAdapter thirdListAdapter;

    private void initWheelView() {
        firstListAdapter = new CategoryListAdapter(activity, firstDatas, false, R.color.white);
        seconListAdapter = new CategoryListAdapter(activity, secondDatas, false, R.color.white);
        thirdListAdapter = new CategoryListAdapter(activity, thirdDatas, false, R.color.white);

        firstCategoryLV.setAdapter(firstListAdapter);
        secondCategoryLV.setAdapter(seconListAdapter);
        thirdCategoryLV.setAdapter(thirdListAdapter);


        firstCategoryLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("", "wheell" + firtListClickPosition + " selectCity:  ");
                firtListClickPosition = position;
                setSeconListDataChange(firtListClickPosition);
                setThirdListDataChange(0);
                if (firstDatas.get(firtListClickPosition).getChild() == null) {
                    mSelectedProvince = firstListAdapter.getItem(position);
                    onDialogItemClickListener.onWindowItemClick(mSelectedProvince);
                    popupWindow.dismiss();
                }


            }
        });

        secondCategoryLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                seconListClickPosition = position;
                setThirdListDataChange(seconListClickPosition);
                mSelectedProvince = seconListAdapter.getItem(position);
                if (secondDatas.get(seconListClickPosition).getChild() == null) {
                    onDialogItemClickListener.onWindowItemClick(mSelectedProvince);
                    popupWindow.dismiss();
                }

            }
        });
        thirdCategoryLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                thirdListClickPosition = position;
//                setThirdListDataChange(0);
                mSelectedProvince = thirdListAdapter.getItem(position);
                onDialogItemClickListener.onWindowItemClick(mSelectedProvince);
                popupWindow.dismiss();

            }
        });

    }


    private void setSeconListDataChange(int firtListClickPosition) {
        if (firstDatas.get(firtListClickPosition).getChild() != null)
            secondDatas = firstDatas.get(firtListClickPosition).getChild();
        else {
            secondDatas = new ArrayList<>();
            mSelectedProvince = firstListAdapter.getItem(firtListClickPosition);
//            onDialogItemClickListener.onWindowItemClick( mSelectedProvince);
//            popupWindow.dismiss();
        }
        seconListAdapter.setListDatas(secondDatas);

    }

    private void setThirdListDataChange(int seconListClickPosition) {
        if (secondDatas.size() > 0 && secondDatas.get(seconListClickPosition).getChild() != null)
            thirdDatas = secondDatas.get(seconListClickPosition).getChild();
        else {
            thirdDatas = new ArrayList<>();
            mSelectedProvince = firstListAdapter.getItem(seconListClickPosition);
//            onDialogItemClickListener.onWindowItemClick( mSelectedProvince);
//            popupWindow.dismiss();
        }
        thirdListAdapter.setListDatas(thirdDatas);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
       /*     case R.id.btn_ok:
                if (null != onDialogItemClickListener) {
                    onDialogItemClickListener.onWindowItemClick(mSelectedProvince);
                }
                break;*/
            case R.id.outView:
                popupWindow.dismiss();
                break;

        }
    }

    @Override
    public void outViewClick() {

    }

    public interface OnDialogItemClickListener<T> {
        void onWindowItemClick(T p);
    }

    OnDialogItemClickListener onDialogItemClickListener;

    public void setOnDialogItemClickListener(OnDialogItemClickListener onDialogItemClickListener) {
        this.onDialogItemClickListener = onDialogItemClickListener;
    }


    public interface DismissListener {
        void onDismiss(int select);
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

    protected void setWindowAlpha() {
        setWindowAlpha(0.8f);
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
//    @TargetApi(19)
    public void showPopAsDropDown(View parent, int offX, int offY, int gravity) {
//        setWindowAlpha();
        initWheelView();
        // 设置popwindow显示位置

        /*if (Build.VERSION.SDK_INT < 24) {
            popupWindow.showAsDropDown(parent);
        } else {

          *//*  parent.getLocationOnScreen(mLocation);
            //设置矩形的大小
            mRect.set(mLocation[0], mLocation[1], mLocation[0] + parent.getWidth(),mLocation[1] + parent.getHeight());
            popupWindow.showAtLocation(parent, Gravity.NO_GRAVITY, mLocation[0], mRect.bottom);
//            popupWindow.showAtLocation(parent, Gravity.BOTTOM, location[0], location[1] + 2);
            popupWindow.update();*//*

            int[] location = new int[2];
            parent.getLocationOnScreen(location);
            popupWindow.showAtLocation(parent, Gravity.NO_GRAVITY, 0, location[1] + parent.getHeight());
            popupWindow.update();
        }*/


        if (Build.VERSION.SDK_INT >= 24) {
            Rect visibleFrame = new Rect();
            parent.getGlobalVisibleRect(visibleFrame);
            int height = parent.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
            popupWindow.setHeight(height);
            popupWindow.showAsDropDown(parent, 0, 0);//parent.getHeight()
        } else {
            popupWindow.showAsDropDown(parent, 0,0 );//parent.getHeight()
        }

        //startAnim();
    }


    /**
     * 显示popWindow,在屏幕的最下方
     */
    public void showPopAtDown(View parent) {
        setWindowAlpha();
        // 设置popwindow显示位置
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        initWheelView();
        if (Build.VERSION.SDK_INT != 24)
            popupWindow.update();
    }


}
