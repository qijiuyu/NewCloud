package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.money.MoneyDetail;
import com.seition.cloud.pro.newcloud.app.utils.TimeUtils;

public class MoneyDetailsListRecyclerAdapter extends BaseQuickAdapter<MoneyDetail, BaseViewHolder> {

    public MoneyDetailsListRecyclerAdapter() {
        super(R.layout.item_money_details_list);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, MoneyDetail bean) {
        viewHolder.setText(R.id.name, bean.getNote());
        viewHolder.setText(R.id.time, TimeUtils.stampToDate(bean.getCtime(),TimeUtils.Format_TIME8));

        if(bean.getType().equals("消费")||bean.getType().equals("冻结")||bean.getType().equals("扣除积分")){
            viewHolder.setTextColor(R.id.expenses,viewHolder.itemView.getContext().getResources().getColor(R.color.black));
            viewHolder.setText(R.id.expenses,"-" + bean.getNum());
        }
        else if(bean.getType().equals("充值")||bean.getType().equals("冻结扣除")||bean.getType().equals("增加积分")){

            viewHolder.setTextColor(R.id.expenses,viewHolder.itemView.getContext().getResources().getColor(R.color.color_47b37d));
            viewHolder.setText(R.id.expenses,"+" + bean.getNum());
        }else
            viewHolder.setText(R.id.expenses,"");

    }


}
