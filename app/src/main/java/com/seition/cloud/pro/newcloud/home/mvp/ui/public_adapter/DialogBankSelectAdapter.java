package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import android.graphics.drawable.Drawable;
import android.util.SparseBooleanArray;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.bind.BankBean;

public class DialogBankSelectAdapter extends BaseQuickAdapter<BankBean, BaseViewHolder> {
    int old = -1;
    //用来装载某个item是否被选中
    SparseBooleanArray selected;


    public void setSelectedItem(int selected) {
        if (old != -1) {
            this.selected.put(old, false);
        }
        this.selected.put(selected, true);
        old = selected;
    }
    public DialogBankSelectAdapter() {
        super(R.layout.item_dialog_bank_select);
        selected = new SparseBooleanArray();
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, BankBean bean) {
        viewHolder.setText(R.id.select, bean.getCard_info());

        Drawable rightDrawable = null;
        if (selected.get(viewHolder.getAdapterPosition())) {
            rightDrawable =  mContext.getResources().getDrawable(R.mipmap.choose);//青铜

        } else {
            rightDrawable =  mContext.getResources().getDrawable(R.mipmap.unchoose);//青铜
        }
        rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
        ((TextView)viewHolder.getView(R.id.select)).setCompoundDrawables(null, null, rightDrawable, null);
    }
}
