package com.bokecc.dwlivedemo_new.recycle;

import android.content.Context;
import android.view.View;

import com.bokecc.dwlivedemo_new.R;
import com.bokecc.dwlivedemo_new.R2;
import com.bokecc.dwlivedemo_new.view.ItemLayout;
import com.bokecc.sdk.mobile.push.entity.SpeedRtmpNode;

import butterknife.BindView;

/**
 * 作者 ${郭鹏飞}.<br/>
 */

public class ServerRecycleAdapter extends SelectAdapter<ServerRecycleAdapter.ServerViewHolder, SpeedRtmpNode> {

    public ServerRecycleAdapter(Context context) {
        super(context);
    }

    @Override
    public void onBindViewHolder(ServerViewHolder holder, int position) {
        holder.mServer.setTip(mDatas.get(position).getDesc());
        long time = mDatas.get(position).getConnectTime();
        holder.mServer.setValue(time > 500 || time <= 0 ? "超时" : String.valueOf(time) + "ms");
        if (position == mSelPosition) {
            holder.mServer.setLeftImageResource(R.drawable.select_icon_selected);
        } else {
            holder.mServer.setLeftImageResource(R.drawable.select_icon_normal);
        }
    }

    @Override
    public int getItemView() {
        return R.layout.item_server;
    }

    @Override
    public ServerViewHolder getViewHolder(View itemView) {
        return new ServerViewHolder(itemView);
    }

    public static class ServerViewHolder extends BaseRecycleAdapter.BaseViewHolder {

        @BindView(R2.id.id_server_item)
        ItemLayout mServer;

        public ServerViewHolder(View itemView) {
            super(itemView);
        }
    }

}
