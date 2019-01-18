package com.bokecc.dwlivedemo_new.recycle;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bokecc.dwlivedemo_new.R;
import com.bokecc.dwlivedemo_new.R2;

import butterknife.BindView;

/**
 * 作者 ${郭鹏飞}.<br/>
 */

public class StringRecycleAdapter extends SelectAdapter<StringRecycleAdapter.StringViewHolder, String> {

    public StringRecycleAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemView() {
        return R.layout.item_string;
    }

    @Override
    public StringViewHolder getViewHolder(View itemView) {
        return new StringViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StringViewHolder holder, int position) {
        holder.mTip.setText(mDatas.get(position));
        if (position == mSelPosition) {
            holder.mIcon.setImageResource(R.drawable.select_icon_selected);
        } else {
            holder.mIcon.setImageResource(R.drawable.select_icon_normal);
        }
    }

    static class StringViewHolder extends BaseRecycleAdapter.BaseViewHolder {

        @BindView(R2.id.id_string_tip)
        TextView mTip;
        @BindView(R2.id.id_string_icon)
        ImageView mIcon;

        StringViewHolder(View itemView) {
            super(itemView);
        }
    }

}
