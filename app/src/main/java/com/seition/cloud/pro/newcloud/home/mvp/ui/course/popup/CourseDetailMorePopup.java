package com.seition.cloud.pro.newcloud.home.mvp.ui.course.popup;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.utils.PreferenceUtil;
import com.seition.cloud.pro.newcloud.home.mvp.ui.login.activity.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by addis on 2018/4/9.
 */

public class CourseDetailMorePopup {
    private OnClickListener listener;
    private PopupWindow more;
    private Unbinder mUnbinder;
    @BindView(R.id.collect)
    TextView collect;
    //    @BindView(R.id.download)
    TextView download;

    @OnClick({R.id.collect, R.id.shape, R.id.download})
    void onClick(View view) {
            switch (view.getId()) {
                case R.id.collect:
                    if (listener != null) listener.collect();
                    dismiss();
                    break;
                case R.id.shape:
                    if (listener != null) listener.shape();
                    dismiss();
                    break;
                case R.id.download:
                    if (listener != null) listener.download();
                    dismiss();
                    break;
            }
    }

    Context mContext;

    public CourseDetailMorePopup(Context mContext, OnClickListener listner, boolean isLive) {
        this.mContext = mContext;
        this.listener = listner;
        View view = LayoutInflater.from(mContext).inflate(R.layout.popup_course_detail_more, null);
        more = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        more.setFocusable(true);
        download = (TextView) view.findViewById(R.id.download);
//        download.setVisibility(isLive ? View.GONE : View.VISIBLE);//暂时取消课时下载功能

//        ColorDrawable dw = new ColorDrawable(0xb0000000);
//        more.setBackgroundDrawable(dw);
        mUnbinder = ButterKnife.bind(this, view);
    }

    public void show(View view) {
        if (more != null)
            more.showAsDropDown(view);
    }

    public void dismiss() {
        if (more != null)
            more.dismiss();
    }

    public void unBind() {
        if (mUnbinder != null)
            mUnbinder.unbind();
    }

    public void setCollect(boolean isCollect) {
        int id = isCollect ? R.drawable.ic_offline_collect : R.drawable.ic_offline_collect_grey;
        Drawable d = mContext.getResources().getDrawable(id);
        d.setBounds(0, 0, d.getMinimumWidth(), d.getMinimumHeight());
        collect.setCompoundDrawables(d, null, null, null);
//        collect.setText(isCollect?"取消收藏":"收藏");
    }

    public interface OnClickListener {
        void collect();

        void shape();

        void download();
    }
}
