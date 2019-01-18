package com.seition.cloud.pro.newcloud.home.mvp.ui.course.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.common.Section;

/**
 * Created by addis on 2018/4/9.
 */

public class LiveSeitionAdapter extends BaseQuickAdapter<Section, BaseViewHolder> {

    public LiveSeitionAdapter() {
        super(R.layout.item_live_seition);
    }

    private boolean isBuy = false;
    private boolean isFree = false;

    public void setBuyOrFree(boolean buy, boolean free) {
        isBuy = buy;
        isFree = free;
    }

    @Override
    protected void convert(BaseViewHolder helper, Section item) {
        helper.setText(R.id.title, item.getTitle())
                .setText(R.id.num, helper.getAdapterPosition() + 1 + "")
                .setText(R.id.zhibo_state, item.getNote())
                .setVisible(R.id.price, false)
                .setVisible(R.id.is_buy, false)
                .getView(R.id.title).setSelected(true);
        if (!isFree && !isBuy) //直播价格不为0并且未购买状态才显示价格与购买状态
            if (item.getIs_buy() == 1)
                helper.setVisible(R.id.is_buy, true);
            else {
                if (item.getCourse_hour_price() > 0) {
                    helper.setVisible(R.id.price, true)
                            .setText(R.id.price, item.getCourse_hour_price() + "");
                }
            }
    }
}