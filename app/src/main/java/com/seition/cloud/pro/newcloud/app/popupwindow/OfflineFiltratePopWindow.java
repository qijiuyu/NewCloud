package com.seition.cloud.pro.newcloud.app.popupwindow;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.utils.LogUtils;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.utils.TimeUtils;

import java.util.Calendar;

/**
 * Created by xzw on 2018/04/03.
 */

public class OfflineFiltratePopWindow extends BasePopWindow implements BaseQuickAdapter.OnItemClickListener, View.OnClickListener {
    private Activity activity;
    private PopupWindow popupWindow;
    private int animationStyle;
    private DismissListener dismissDialogListener;
    public OfflineFiltratePopWindow(Activity activity, int animationStyle) {
        this.activity = activity;
        this.animationStyle = animationStyle;
        initPop();
    }

    TextView time_one,
            time_three,
            time_six,
            time_no,
            live_default,
            live_new,
            live_hot,
            live_pirce_down,
            live_pirce_up,
            sure,
            reset;

    private void initPop() {
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_offline_filtrate, null);
        time_one = (TextView) view.findViewById(R.id.time_one);
        time_three = (TextView) view.findViewById(R.id.time_three);
        time_six = (TextView) view.findViewById(R.id.time_six);
        time_no = (TextView) view.findViewById(R.id.time_no);
        live_default = (TextView) view.findViewById(R.id.live_default);
        live_new = (TextView) view.findViewById(R.id.live_new);
        live_hot = (TextView) view.findViewById(R.id.live_hot);
        live_pirce_down = (TextView) view.findViewById(R.id.live_pirce_down);
        live_pirce_up = (TextView) view.findViewById(R.id.live_pirce_up);
        sure = (TextView) view.findViewById(R.id.sure);
        reset = (TextView) view.findViewById(R.id.reset);
        outView = (View) view.findViewById(R.id.outView);
        outView.setOnClickListener(this);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        // 设置popwindow如果点击外面区域，便关闭。
        popupWindow.setOutsideTouchable(false);
        if (animationStyle != 0) {
            popupWindow.setAnimationStyle(animationStyle);
        }
        time_one.setOnClickListener(this);
        time_three.setOnClickListener(this);
        time_six.setOnClickListener(this);
        time_no.setOnClickListener(this);
        live_default.setOnClickListener(this);
        live_new.setOnClickListener(this);
        live_hot.setOnClickListener(this);
        live_pirce_down.setOnClickListener(this);
        live_pirce_up.setOnClickListener(this);
        sure.setOnClickListener(this);
        reset.setOnClickListener(this);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowNormal();
                if (dismissDialogListener != null) {
//                    dismissDialogListener.onDismiss(mPosition);
                }
            }
        });

    }

    private void initDialogData() {

    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }
    private int mCurrYear, mCurrMonth, mCurrDay;
    private Calendar calendar;
    String time = "";
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.time_one:
                calendar = Calendar.getInstance();
                mCurrYear = calendar.get(Calendar.YEAR);
                mCurrMonth = calendar.get(Calendar.MONTH) + 1;

                changeStatusOpress(v.getId());
                if ((mCurrMonth + 1) > 12)
                    time = TimeUtils.dataToStamp((TimeUtils.getCurrentTime(TimeUtils.Format_TIME3)) + "," + TimeUtils.dataToStamp((mCurrYear + 1 + "-" + (mCurrMonth + 1 - 12) + "-" + 1),TimeUtils.Format_TIME1),TimeUtils.Format_TIME1);
                else
                    time = TimeUtils.dataToStamp((TimeUtils.getCurrentTime(TimeUtils.Format_TIME3)) + "," + TimeUtils.dataToStamp((mCurrYear + "-" + (mCurrMonth + 1) + "-" + 1),TimeUtils.Format_TIME1),TimeUtils.Format_TIME1);


                onDialogChildeViewClickListener.onChildeStatusViewClick(time);
                break;
            case R.id.time_three:
                changeStatusOpress(v.getId());
                if ((mCurrMonth + 3) > 12)
                    time = TimeUtils.dataToStamp((TimeUtils.getCurrentTime(TimeUtils.Format_TIME3)) + "," + TimeUtils.dataToStamp((mCurrYear + 1 + "-" + (mCurrMonth + 3 - 12) + "-" + 1),TimeUtils.Format_TIME1),TimeUtils.Format_TIME1);
                else
                    time = TimeUtils.dataToStamp((TimeUtils.getCurrentTime(TimeUtils.Format_TIME3)) + "," + TimeUtils.dataToStamp((mCurrYear + "-" + (mCurrMonth + 3) + "-" + 1),TimeUtils.Format_TIME1),TimeUtils.Format_TIME1);

                onDialogChildeViewClickListener.onChildeStatusViewClick(time);
                LogUtils.debugInfo("live_free");
                break;
            case R.id.time_six:
                if ((mCurrMonth + 6) > 12) {
//                        System.out.println(TimeUtils.dataToStamp((TimeUtils.getCurrentTime(TimeUtils.TIME3)),TimeUtils.TIME1));
//                        System.out.println(TimeUtils.dataToStamp((mCurrYear + 1 + "-" + (mCurrMonth + 6 - 12) + "-" + 1),TimeUtils.TIME1));
                    time = TimeUtils.dataToStamp((TimeUtils.getCurrentTime(TimeUtils.Format_TIME3)) + "," + TimeUtils.dataToStamp((mCurrYear + 1 + "-" + (mCurrMonth + 6 - 12) + "-" + 1),TimeUtils.Format_TIME1),TimeUtils.Format_TIME1);
                } else
                    time = TimeUtils.dataToStamp((TimeUtils.getCurrentTime(TimeUtils.Format_TIME3)) + "," + TimeUtils.dataToStamp((mCurrYear + "-" + (mCurrMonth + 6) + "-" + 1),TimeUtils.Format_TIME1),TimeUtils.Format_TIME1);

                changeStatusOpress(v.getId());
                onDialogChildeViewClickListener.onChildeStatusViewClick(time);
                break;
            case R.id.time_no:
                time = TimeUtils.dataToStamp((TimeUtils.getCurrentTime(TimeUtils.Format_TIME1)),TimeUtils.Format_TIME1) + "," + "";
                changeStatusOpress(v.getId());
                onDialogChildeViewClickListener.onChildeStatusViewClick(time);
                break;
            case R.id.live_default:
                changeOrderOpress(v.getId());
                onDialogChildeViewClickListener.onChildeOrderViewClick("default");
                break;
            case R.id.live_new:
                changeOrderOpress(v.getId());
                onDialogChildeViewClickListener.onChildeOrderViewClick("new");
//                order = "new";
                break;
            case R.id.live_hot:
                changeOrderOpress(v.getId());
                onDialogChildeViewClickListener.onChildeOrderViewClick("hot");
                break;
            case R.id.live_pirce_down:
                changeOrderOpress(v.getId());
                onDialogChildeViewClickListener.onChildeOrderViewClick("t_price_down");
                break;
            case R.id.live_pirce_up:
                changeOrderOpress(v.getId());
                onDialogChildeViewClickListener.onChildeOrderViewClick("t_price");
                break;
            case R.id.live_near:
                changeOrderOpress(v.getId());
                onDialogChildeViewClickListener.onChildeOrderViewClick("");
                break;
            case R.id.reset:
                time_one.setBackgroundResource(R.drawable.shape_filtrate_dialog_unpress);
                time_three.setBackgroundResource(R.drawable.shape_filtrate_dialog_unpress);
                time_six.setBackgroundResource(R.drawable.shape_filtrate_dialog_unpress);
                time_no.setBackgroundResource(R.drawable.shape_filtrate_dialog_unpress);

                live_default.setBackgroundResource(R.drawable.shape_filtrate_dialog_press);
                live_new.setBackgroundResource(R.drawable.shape_filtrate_dialog_unpress);
                live_hot.setBackgroundResource(R.drawable.shape_filtrate_dialog_unpress);
                live_pirce_down.setBackgroundResource(R.drawable.shape_filtrate_dialog_unpress);
                live_pirce_up.setBackgroundResource(R.drawable.shape_filtrate_dialog_unpress);
                onDialogChildeViewClickListener.onChildeSureViewClick(v);
                break;
            case R.id.sure:
                popupWindow.dismiss();
                onDialogChildeViewClickListener.onChildeSureViewClick(v);
                break;
            case R.id.outView:
                popupWindow.dismiss();
                break;
        }
    }


    private void changeStatusOpress(int id) {
        switch (id) {
            case R.id.time_one:
                time_one.setBackgroundResource(R.drawable.shape_filtrate_dialog_press);
                time_three.setBackgroundResource(R.drawable.shape_filtrate_dialog_unpress);
                time_six.setBackgroundResource(R.drawable.shape_filtrate_dialog_unpress);
                time_no.setBackgroundResource(R.drawable.shape_filtrate_dialog_unpress);
                break;
            case R.id.time_three:
                time_one.setBackgroundResource(R.drawable.shape_filtrate_dialog_unpress);
                time_three.setBackgroundResource(R.drawable.shape_filtrate_dialog_press);
                time_six.setBackgroundResource(R.drawable.shape_filtrate_dialog_unpress);
                time_no.setBackgroundResource(R.drawable.shape_filtrate_dialog_unpress);
                break;
            case R.id.time_six:
                time_one.setBackgroundResource(R.drawable.shape_filtrate_dialog_unpress);
                time_three.setBackgroundResource(R.drawable.shape_filtrate_dialog_unpress);
                time_six.setBackgroundResource(R.drawable.shape_filtrate_dialog_press);
                time_no.setBackgroundResource(R.drawable.shape_filtrate_dialog_unpress);
                break;
            case R.id.time_no:
                time_one.setBackgroundResource(R.drawable.shape_filtrate_dialog_unpress);
                time_three.setBackgroundResource(R.drawable.shape_filtrate_dialog_unpress);
                time_six.setBackgroundResource(R.drawable.shape_filtrate_dialog_unpress);
                time_no.setBackgroundResource(R.drawable.shape_filtrate_dialog_press);
                break;
        }
    }
    private void changeOrderOpress(int id) {
        switch (id) {
            case R.id.live_default:
                live_default.setBackgroundResource(R.drawable.shape_filtrate_dialog_press);
                live_new.setBackgroundResource(R.drawable.shape_filtrate_dialog_unpress);
                live_hot.setBackgroundResource(R.drawable.shape_filtrate_dialog_unpress);
                live_pirce_down.setBackgroundResource(R.drawable.shape_filtrate_dialog_unpress);
                live_pirce_up.setBackgroundResource(R.drawable.shape_filtrate_dialog_unpress);
                break;
            case R.id.live_new:
                live_default.setBackgroundResource(R.drawable.shape_filtrate_dialog_unpress);
                live_new.setBackgroundResource(R.drawable.shape_filtrate_dialog_press);
                live_hot.setBackgroundResource(R.drawable.shape_filtrate_dialog_unpress);
                live_pirce_down.setBackgroundResource(R.drawable.shape_filtrate_dialog_unpress);
                live_pirce_up.setBackgroundResource(R.drawable.shape_filtrate_dialog_unpress);
                break;
            case R.id.live_hot:
                live_default.setBackgroundResource(R.drawable.shape_filtrate_dialog_unpress);
                live_new.setBackgroundResource(R.drawable.shape_filtrate_dialog_unpress);
                live_hot.setBackgroundResource(R.drawable.shape_filtrate_dialog_press);
                live_pirce_down.setBackgroundResource(R.drawable.shape_filtrate_dialog_unpress);
                live_pirce_up.setBackgroundResource(R.drawable.shape_filtrate_dialog_unpress);
                break;
            case R.id.live_pirce_down:
                live_default.setBackgroundResource(R.drawable.shape_filtrate_dialog_unpress);
                live_new.setBackgroundResource(R.drawable.shape_filtrate_dialog_unpress);
                live_hot.setBackgroundResource(R.drawable.shape_filtrate_dialog_unpress);
                live_pirce_down.setBackgroundResource(R.drawable.shape_filtrate_dialog_press);
                live_pirce_up.setBackgroundResource(R.drawable.shape_filtrate_dialog_unpress);
                break;
            case R.id.live_pirce_up:
                live_default.setBackgroundResource(R.drawable.shape_filtrate_dialog_unpress);
                live_new.setBackgroundResource(R.drawable.shape_filtrate_dialog_unpress);
                live_hot.setBackgroundResource(R.drawable.shape_filtrate_dialog_unpress);
                live_pirce_down.setBackgroundResource(R.drawable.shape_filtrate_dialog_unpress);
                live_pirce_up.setBackgroundResource(R.drawable.shape_filtrate_dialog_press);
                break;
            case R.id.live_near:
                live_default.setBackgroundResource(R.drawable.shape_filtrate_dialog_unpress);
                live_new.setBackgroundResource(R.drawable.shape_filtrate_dialog_unpress);
                live_hot.setBackgroundResource(R.drawable.shape_filtrate_dialog_unpress);
                live_pirce_down.setBackgroundResource(R.drawable.shape_filtrate_dialog_unpress);
                live_pirce_up.setBackgroundResource(R.drawable.shape_filtrate_dialog_unpress);
                break;
        }
    }




    public interface OnDialogChildeViewClickListener<T> {
        void onChildeStatusViewClick(String time);
        void onChildeOrderViewClick(String order);
        void onChildeSureViewClick(View v);
    }
    OnDialogChildeViewClickListener onDialogChildeViewClickListener;
    public void setOnDialogChildeViewClickListener(OnDialogChildeViewClickListener onDialogChildeViewClickListener){
        this.onDialogChildeViewClickListener = onDialogChildeViewClickListener;
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
