package com.seition.cloud.pro.newcloud.home.mvp.ui.public_adapter;

import android.graphics.drawable.Drawable;
import android.util.SparseBooleanArray;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.member.Member;

public class MemberTypeGridRecyclerAdapter extends BaseQuickAdapter<Member, BaseViewHolder> {




    public MemberTypeGridRecyclerAdapter() {
        super(R.layout.item_member_type_select);
        selected = new SparseBooleanArray();
    }

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

    @Override
    protected void convert(BaseViewHolder viewHolder, Member bean) {
        viewHolder.setText(R.id.member_type_select, bean.getTitle());

        Drawable leftDrawable = null;
        if (selected.get(viewHolder.getAdapterPosition())) {
            leftDrawable =  mContext.getResources().getDrawable(R.mipmap.choose);//青铜

        } else {
            leftDrawable =  mContext.getResources().getDrawable(R.mipmap.unchoose);//青铜
        }
        leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());
        ((TextView)viewHolder.getView(R.id.member_type_select)).setCompoundDrawables(leftDrawable, null, null, null);

    }



}
