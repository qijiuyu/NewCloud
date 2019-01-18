package com.seition.cloud.pro.newcloud.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.utils.Utils;


public class TopBarTab extends LinearLayout {

    private Context mContext;
    private TextView mTextView;
    private RelativeLayout parent;

    private int mTabPosition = -1;
    private int icon;
    private static boolean ifshow = false;

    public TopBarTab(Context context, @DrawableRes int icon, String title) {
        this(context, null, icon, title);
    }


    public TopBarTab(Context context, AttributeSet attrs, int icon, String title) {
        this(context, attrs, 0, icon, title);
    }

    public TopBarTab(Context context, AttributeSet attrs, int defStyleAttr, int icon, String title) {
        super(context, attrs, defStyleAttr);
        init(context, icon, title);
    }

    private void init(Context context, int icon, String title) {
        mContext = context;
        this.icon = icon;

        setOrientation(LinearLayout.VERTICAL);

        Drawable drawable = context.getResources().getDrawable(icon);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());

        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
//        textViewParams.weight= 1;
        params.rightMargin = Utils.dip2px(context, 3f);
        parent = new RelativeLayout(context);
        parent.setLayoutParams(params);

        RelativeLayout.LayoutParams reparams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        reparams.rightMargin = Utils.dip2px(context, 3f);
        reparams.addRule(RelativeLayout.CENTER_IN_PARENT, -1);

        mTextView = new TextView(context);
        mTextView.setText(title);
        mTextView.setTextSize(Utils.dip2px(context, 5f));
//        mTextView.setLayoutParams(reparams);
        mTextView.setCompoundDrawables(null, null, drawable, null);
        mTextView.setTextColor(ContextCompat.getColor(mContext, R.color.item_title_color));
        parent.addView(mTextView, reparams);

        addView(parent);


    }

    public void setText(String title) {
        if (mTextView != null)
            mTextView.setText(title);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
//        if (selected) {
//            mIcon.setColorFilter(ContextCompat.getColor(mContext, R.color.colorPrimary));
//            mTextView.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
//
//        } else {
//            mIcon.setColorFilter(ContextCompat.getColor(mContext, R.color.item_title_color));
//            mTextView.setTextColor(ContextCompat.getColor(mContext, R.color.item_title_color));
//        }
    }


    public void setTabPosition(int position) {
        mTabPosition = position;
        if (position == 0) {
            setSelected(true);
        }
    }

    public int getTabPosition() {
        return mTabPosition;
    }
}
