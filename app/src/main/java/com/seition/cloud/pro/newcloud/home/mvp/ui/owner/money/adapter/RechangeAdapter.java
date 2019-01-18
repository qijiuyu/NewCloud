package com.seition.cloud.pro.newcloud.home.mvp.ui.owner.money.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.seition.cloud.pro.newcloud.R;
import com.seition.cloud.pro.newcloud.app.bean.examination.Exam;
import com.seition.cloud.pro.newcloud.app.bean.money.BalanceRechangeBean;

/**
 * Created by addis on 2018/5/30.
 */
public class RechangeAdapter extends BaseQuickAdapter<BalanceRechangeBean, BaseViewHolder> {

    public RechangeAdapter() {
        super(R.layout.item_rechange);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, BalanceRechangeBean bean) {
//        if (viewHolder.getPosition() == getItemCount() /*- 1*/) {
//            viewHolder.getView(R.id.price).setVisibility(View.GONE);
//            viewHolder.getView(R.id.price_more).setVisibility(View.VISIBLE);
//            if (bean.getRechange() != 0)
//                viewHolder.setText(R.id.more_money, bean.getRechange() + "");
//            viewHolder.setTag(R.id.more_money, viewHolder.getAdapterPosition());
////            viewHolder.addOnClickListener(R.id.more_money);
//            viewHolder.getView(R.id.more_money).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    for (int i = 0; i < getData().size(); i++) {
//                        if (getItemCount() - 1 == i)
//                            getItem(i).setSelect(true);
//                        else
//                            getItem(i).setSelect(false);
//                    }
//                    notifyDataSetChanged();
//                }
//            });
//            ((EditText) viewHolder.getView(R.id.more_money)).addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                    String s1 = s.toString();
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    String s2 = s.toString();
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                    String s3 = s.toString();
//                    try {
//                        bean.setRechange(Integer.getInteger(s3));
//                        if (s != null) {
//                            listener.SaveEdit(Integer.parseInt(viewHolder.getView(R.id.more_money).getTag().toString()), s.toString());
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            });
//        } else {
        viewHolder.getView(R.id.price).setVisibility(View.VISIBLE);
//        viewHolder.getView(R.id.price_more).setVisibility(View.GONE);
        viewHolder.setText(R.id.money, bean.getRechange() + "");
        if (bean.getGive() == 0)
            viewHolder.getView(R.id.info).setVisibility(View.GONE);
        else {
            viewHolder.getView(R.id.info).setVisibility(View.VISIBLE);
            viewHolder.setText(R.id.info, "送" + bean.getGive() + "");
        }
//        }

        if (bean.isSelect())
            viewHolder.getView(R.id.price_bg).setBackgroundResource(R.drawable.shape_money_select);
        else
            viewHolder.getView(R.id.price_bg).setBackgroundResource(R.drawable.shape_money_unselect);
    }

    SaveEditListener listener;

    public void setEditListener(SaveEditListener listener) {
        this.listener = listener;
    }

    public interface SaveEditListener {
        void SaveEdit(int position, String string);
    }
}


