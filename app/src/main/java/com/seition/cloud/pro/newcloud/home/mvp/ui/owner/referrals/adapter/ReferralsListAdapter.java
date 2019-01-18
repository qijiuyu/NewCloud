package com.seition.cloud.pro.newcloud.home.mvp.ui.owner.referrals.adapter;

import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.download.InitDownloadBean;
import com.seition.cloud.pro.newcloud.app.bean.library.LibraryItemBean;
import com.seition.cloud.pro.newcloud.app.bean.referrals.Arr_Referrals;
import com.seition.cloud.pro.newcloud.app.bean.referrals.ReferralsBean;
import com.seition.cloud.pro.newcloud.app.utils.TimeUtils;
import com.seition.cloud.pro.newcloud.app.utils.download.DBUtils;
import com.seition.cloud.pro.newcloud.home.mvp.ui.download.view_holder.DownloadViewHolder;

/**
 * Created by addis on 2018/11/26.
 */
public class ReferralsListAdapter extends BaseQuickAdapter<ReferralsBean, BaseViewHolder> {

    public ReferralsListAdapter() {
        super(R.layout.item_referrals);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReferralsBean item) {
        int textColor = mContext.getResources().getColor(R.color.color_81);
        int textSize = R.dimen.sp_9;
        String name;
        String time;
        String level;
        String commission;
        String number;
        if (helper.getPosition() == 0) {
            textColor = mContext.getResources().getColor(R.color.color_48);
            textSize = R.dimen.sp_11;
            name = "会员昵称";
            time = "注册时间";
            level = "推荐层级";
            commission = "附带收入";
            number = "推荐人数";
        } else {
            name = item.getUname();
            time = TimeUtils.MyFormatTime4(item.getCtime()*1000);
            level = item.getLevel() == 1 ? "一级推荐" : "二级推荐";
            commission = item.getNum() + "元";
            number = item.getUserCount() + "";
        }

        helper.setText(R.id.name, name)
                .setText(R.id.level, level)
                .setText(R.id.time, time)
                .setText(R.id.commission, commission)
                .setText(R.id.number, number)
                .setTextColor(R.id.name, textColor)
                .setTextColor(R.id.level, textColor)
                .setTextColor(R.id.time, textColor)
                .setTextColor(R.id.commission, textColor)
                .setTextColor(R.id.number, textColor)
                .setVisible(R.id.top_line, helper.getPosition() == 0)
        ;
        setTextSize(helper.getView(R.id.name), textSize);
        setTextSize(helper.getView(R.id.level), textSize);
        setTextSize(helper.getView(R.id.time), textSize);
        setTextSize(helper.getView(R.id.commission), textSize);
        setTextSize(helper.getView(R.id.number), textSize);
    }

    private void setTextSize(TextView textView, int textSize) {
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.getResources().getDimensionPixelSize(textSize));
    }
}